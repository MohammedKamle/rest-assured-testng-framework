package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TokenManager {
    // access_token VALUE WILL BE RETAINED(while program is running) AS IT IS DECLARED STATIC unless we overwrite it
    private static String access_token;
    private static Instant expiry_time;

    public static String getToken(){
        try {
            if (access_token == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing token...");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                // we are giving a buffer of 5min(300 seconds) just to be on the safer side
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            }else {
                System.out.println("Token is good to use");
            }
        }catch (Exception e){
            throw new RuntimeException("Abort! Failed to get the token");
        }
        System.out.println("NEW TOKEN :: "+access_token);
        return access_token;
    }

    private static Response renewToken(){
        Map<String, String> formParams = new HashMap<>();
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        Response response = RestResource.postAccount(formParams);

        if (response.getStatusCode() != 200){
            throw new RuntimeException("Abort! Renew Token Failed");
        }
        return response;
    }
}
