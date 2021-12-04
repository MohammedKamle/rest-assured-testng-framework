package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.api.Route;
import com.spotify.oauth2.api.SpecBuilder;
import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PlaylistApi {
    //static String accessToken = "BQC-s08oh62_VjImRtJWIaNKb5FSCiNNM8ywE4HHeTEy9KB1_0Qv0KECNZ-fJ_wWtSQB93n-g96-ukRmFTcwfADuZ-N1ecWYl55nDJvDDapiL8NYaCSx8TyPfeEXP5sz_gUOaqmN36ytSrrIsv3O6ZrlPCndroIuWyf4pYOOdbvkb9w91iBXa4oyJdTNYiq8pp5i9dFgxxExba5IpQUfsnvpkeZikAZm98OYHBcreCgN";

    @Step
    public static Response post(Playlist requestPlaylist){
        return RestResource.post(Route.USERS + "/"+ ConfigLoader.getInstance().getUser() +
                Route.PLAYLISTS, TokenManager.getToken(), requestPlaylist);
    }
    // Overloaded post method
    public static Response post(String token, Playlist requestPlaylist){
        return RestResource.post(Route.USERS + "/"+ConfigLoader.getInstance().getUser()+
                Route.PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(Route.PLAYLISTS + "/"+playlistId, TokenManager.getToken());
    }

    public static Response update(String playlistId, Playlist requestPlaylist){
        return RestResource.update(Route.PLAYLISTS + "/"+playlistId, TokenManager.getToken(), requestPlaylist);
    }
}
