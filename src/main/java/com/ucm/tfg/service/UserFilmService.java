package com.ucm.tfg.service;

import android.app.Activity;


import com.ucm.tfg.entities.UserFilm;

/**
 * All http requests against user-film relation route
 */
public class UserFilmService {
    private static String url = "http://tfg-spring.herokuapp.com/user-films/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/user-films/";

    public UserFilmService(){}

    // Creates a new user-film relation
    public static <T> void postUserFilm(Activity activity, UserFilm userFilm, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .body(userFilm)
                .url(develop_url)
                .execute(callback, c);
    }

    // Gets back a user-film relation given its user id and its film id
    public static <T> void get(Activity activity, long userId, long filmId, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{userId}/{filmId}")
                .addPathVariable("userId", "" + userId)
                .addPathVariable("filmId", "" + filmId)
                .execute(callback, c);
    }

    // A user rates a film
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

    // Deletes the user-film relation given its user id and its film id
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
