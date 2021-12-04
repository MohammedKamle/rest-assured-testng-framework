package com.spotify.oauth2.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    public static String generateName(){
        return "Playlist "+new Faker()
                .regexify("[A-Za-z0-9 ,_-]{10}");
    }

    public static String generateDescription(){
        return "Description "+new Faker()
                .regexify("[ A-Za-z0-9_@./#&+-]{50}");
    }

    public static void main(String[] args) {
        System.out.println(generateName());
        System.out.println(generateDescription());
    }

}
