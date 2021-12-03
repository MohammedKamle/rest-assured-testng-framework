package com.spotify.oauth2.utils;

import java.util.Properties;

// Singleton design pattern in this class
public class DataLoader {
    private final Properties properties;
    public static DataLoader dataLoader;

    private DataLoader(){
        properties = PropertyUtils.propertyLoader("src/test/resources/data.properties");
    }

    public static DataLoader getInstance(){
        if (dataLoader == null){
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    public String getGetPlaylistId(){
        String prop = properties.getProperty("get_playlist_id");
        if (prop != null){
            return prop;
        }else {
            throw new RuntimeException("get_playlist_id not specifies in data.properties file");
        }
    }

    public String getUpdatePlaylistId(){
        String prop = properties.getProperty("update_playlist_id");
        if (prop != null){
            return prop;
        }else {
            throw new RuntimeException("update_playlist_id not specifies in data.properties file");
        }
    }

}
