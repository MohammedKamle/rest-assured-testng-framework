package com.spotify.oauth2.test;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.enums.StatusCodes;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import com.spotify.oauth2.utils.FakerUtils;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    @Test
    public void shouldBeAbleToCreatePlaylist(){
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCodes.CODE_201.getCode());
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToGetPlaylist(){
        Playlist requestPlaylist = playlistBuilder("Evening Vibes", "New playlist description", false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.getStatusCode(), StatusCodes.CODE_200.getCode());
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToUpdateInPlaylist(){
        Playlist updatedPlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(), false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), updatedPlaylist);;
        assertThat(response.getStatusCode(), equalTo(StatusCodes.CODE_200.getCode()));

    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName(){
        Playlist playlist = playlistBuilder("", FakerUtils.generateDescription(), false);
        Response response = PlaylistApi.post(playlist);
        assertStatusCode(response.getStatusCode(), StatusCodes.CODE_400.getCode());
        assertError(response.as(Error.class),StatusCodes.CODE_400.getCode(), StatusCodes.CODE_400.getMsg());
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithInvalidToken(){
        String invalidToken =  "Bearer some76764random88/.invalid_token";
        Playlist playlist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(), false);
        Response response = PlaylistApi.post(invalidToken,playlist);
        assertStatusCode(response.getStatusCode(), StatusCodes.CODE_401.getCode());
        assertError(response.as(Error.class), StatusCodes.CODE_401.getCode(), StatusCodes.CODE_401.getMsg());
    }

    public Playlist playlistBuilder(String name, String description, boolean _public){
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;
    }

    public void assertPlaylistEqual(Playlist responsePLaylist, Playlist requestPlaylist){
        assertThat(responsePLaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePLaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePLaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    public void assertError(Error error, int statusCode, String errorMessage){
        assertThat(error.getInnerError().getStatus(), equalTo(statusCode));
        assertThat(error.getInnerError().getMessage(), equalTo(errorMessage));
    }



}
