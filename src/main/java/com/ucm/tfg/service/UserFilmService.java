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

    public static <T> void get(Activity activity, long userId, long filmId, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{userId}/{filmId}")
                .addPathVariable("userId", "" + userId)
                .addPathVariable("filmId", "" + filmId)
                .execute(callback, c);
    }

    public static <T> void rate(Activity activity, UserFilm userFilm, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .put()
                .url(develop_url + "rate")
                .url(develop_url + "{userId}/{filmId}/rate/{rating}")
                .addPathVariable("userId", "" + userFilm.getUserId())
                .addPathVariable("filmId", "" + userFilm.getFilmId())
                .addPathVariable("rating", userFilm.getRating() + "")
                .body(userFilm)
                .execute(callback, c);
    }

    public static <T> void delete(Activity activity, long userId, long filmId, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .delete()
                .url(develop_url + "{userId}/{filmId}")
                .addPathVariable("userId", "" + userId)
                .addPathVariable("filmId", "" + filmId)
                .execute(callback, c);
    }
}
