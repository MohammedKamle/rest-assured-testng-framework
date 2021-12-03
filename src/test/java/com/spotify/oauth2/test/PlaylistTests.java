package com.spotify.oauth2.test;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistTests {

    @Test
    public void shouldBeAbleToCreatePlaylist(){
        Playlist requestPlaylist = playlistBuilder("Namaste London", "New playlist description", false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 201);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToGetPlaylist(){
        Playlist requestPlaylist = playlistBuilder("Evening Vibes", "New playlist description", false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.getStatusCode(), 200);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void shouldBeAbleToUpdateInPlaylist(){
        Playlist updatedPlaylist = playlistBuilder("Evening Vibes", "New playlist description", false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), updatedPlaylist);;
        assertThat(response.getStatusCode(), equalTo(200));

    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName(){
        Playlist playlist = playlistBuilder("", "New playlist description", false);
        Response response = PlaylistApi.post(playlist);
        assertStatusCode(response.getStatusCode(), 400);
        assertError(response.as(Error.class),400,"Missing required field: name");
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithInvalidToken(){
        String invalidToken =  "Bearer some76764random88/.invalid_token";
        Playlist playlist = playlistBuilder("New Playlist101", "New playlist description", false);
        Response response = PlaylistApi.post(invalidToken,playlist);
        assertStatusCode(response.getStatusCode(), 401);
        assertError(response.as(Error.class), 401,"Invalid access token");
    }

    public Playlist playlistBuilder(String name, String description, boolean _public){
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setPublic(_public);
        return playlist;
    }

    public void assertPlaylistEqual(Playlist responsePLaylist, Playlist requestPlaylist){
        assertThat(responsePLaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePLaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePLaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    public void assertError(Error error, int statusCode, String errorMessage){
        assertThat(error.getInnerError().getStatus(), equalTo(statusCode));
        assertThat(error.getInnerError().getMessage(), equalTo(errorMessage));
    }



}
