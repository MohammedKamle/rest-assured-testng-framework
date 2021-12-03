package com.spotify.oauth2.api;

import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response post(String path, String token, Object payload){
        return
        given(SpecBuilder.getRequestSpec())
                .body(payload)
                .header("Authorization", "Bearer "+token)
        .when()
                .post(path)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract()
                .response();
    }

    public static Response postAccount(Map<String, String> formParams){
        return
                given(SpecBuilder.getAccountRequestSpec())
                        .formParams(formParams)
                .when()
                        .post(Route.API + Route.TOKEN)
                .then()
                        .spec(SpecBuilder.getResponseSpec())
                        .extract()
                        .response();
    }

    public static Response get(String path, String token){
        return
         given(SpecBuilder.getRequestSpec())
                 .header("Authorization", "Bearer "+token)
        .when()
                 .get(path)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract()
                .response();
    }

    public static Response update(String path, String token, Object payload){
        return
        given(SpecBuilder.getRequestSpec())
                .body(payload)
                .header("Authorization", "Bearer "+token)
        .when()
                .put(path)
        .then()
                .spec(SpecBuilder.getResponseSpec())
                .extract()
                .response();
    }
}
