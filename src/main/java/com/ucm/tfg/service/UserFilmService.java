package com.ucm.tfg.service;

import android.app.Activity;


import com.ucm.tfg.entities.UserFilm;


public class UserFilmService {
    private static String url = "http://tfg-spring.herokuapp.com/user-films/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/user-films/";

    public UserFilmService(){}


    public static <T> void postUserFilm(Activity activity, UserFilm userFilm, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .body(userFilm)
                .url(develop_url)
                .execute(callback, c);
    }

    public static <T> void getRating(Activity activity, String userUuid, String filmUuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{userUuid}/{filmUuid}/rating")
                .addPathVariable("userUuid", userUuid)
                .addPathVariable("filmUuid", filmUuid)
                .execute(callback, c);
    }

    public static <T> void rate(Activity activity, UserFilm userFilm, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .url(develop_url + "rate")
                .body(userFilm)
                .execute(callback, c);
    }

    public static <T> void delete(Activity activity, String userUuid, String filmUuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .delete()
                .url(develop_url + "{userUuid}/{filmUuid}")
                .addPathVariable("userUuid", userUuid)
                .addPathVariable("filmUuid", filmUuid)
                .execute(callback, c);
    }
}
