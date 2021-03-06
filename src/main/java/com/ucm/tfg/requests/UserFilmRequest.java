package com.ucm.tfg.requests;

import android.app.Activity;


import com.ucm.tfg.entities.UserFilm;

/**
 * All http requests against user-film relation route
 */
public class UserFilmRequest {
    private static String url = "http://tfg-spring.herokuapp.com/user-films/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/user-films/";

    public UserFilmRequest(){}

    // Creates a new user-film relation
    public static <T> void postUserFilm(Activity activity, UserFilm userFilm, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .post()
                .body(userFilm)
                .url(develop_url)
                .execute(callback, c);
    }

    // Gets back a user-film relation given its user id and its film id
    public static <T> void get(Activity activity, long userId, long filmId, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{userId}/{filmId}")
                .addPathVariable("userId", "" + userId)
                .addPathVariable("filmId", "" + filmId)
                .execute(callback, c);
    }

    // A user rates a film
    public static <T> void rate(Activity activity, UserFilm userFilm, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
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
    public static <T> void delete(Activity activity, long userId, long filmId, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .delete()
                .url(develop_url + "{userId}/{filmId}")
                .addPathVariable("userId", "" + userId)
                .addPathVariable("filmId", "" + filmId)
                .execute(callback, c);
    }
}
