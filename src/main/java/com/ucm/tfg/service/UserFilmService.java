package com.ucm.tfg.service;

import android.app.Activity;


import com.ucm.tfg.entities.UserFilm;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;


public class UserFilmService {
    private static String url = "http://tfg-spring.herokuapp.com/user-films/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/user-films/";

    public UserFilmService(){}

    public static <T> void getUserFilms(Activity activity, Service.ClientResponse<ArrayList<UserFilm>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url)
                .execute(callback, new ParameterizedTypeReference<ArrayList<UserFilm>>(){});
    }
    public static <T> void postUserFilm(Activity activity, UserFilm userFilm, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .body(userFilm)
                .url(develop_url)
                .execute(callback, c);
    }
}
