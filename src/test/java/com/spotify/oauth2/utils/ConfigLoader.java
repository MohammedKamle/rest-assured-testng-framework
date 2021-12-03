package com.spotify.oauth2.utils;

import java.util.Properties;

// Singleton design pattern in this class
public class ConfigLoader {
    private final Properties properties;
    public static ConfigLoader configLoader;

    private ConfigLoader(){
        properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
    }

    public static ConfigLoader getInstance(){
        if (configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getClientId(){
        String prop = properties.getProperty("client_id");
        if (prop != null){
            return prop;
        }else {
            throw new RuntimeException("client_id not specifies in config.properties file");
        }
    }

    public String getClientSecret(){
        String prop = properties.getProperty("client_secret");
        if (prop != null){
            return prop;
        }else {
            throw new RuntimeException("client_secret not specifies in config.properties file");
        }
    }

    public String getGrantType(){
        String prop = properties.getProperty("grant_type");
        if (prop != null){
            return prop;
        }else {
            throw new RuntimeException("grant_type not specifies in config.properties file");
        }
    }

    public String getRefreshToken(){
        String prop = properties.getProperty("refresh_token");
        if (prop != null){
            return prop;
        }else {
            throw new RuntimeException("refresh_token not specifies in config.properties file");
        }
    }

    public String getUser(){
        String prop = properties.getProperty("user");
        if (prop != null){
            return prop;
        }else {
            throw new RuntimeException("user not specifies in config.properties file");
        }
    }
}
