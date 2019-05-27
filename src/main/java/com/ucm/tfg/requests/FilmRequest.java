package com.ucm.tfg.requests;

import android.app.Activity;

import com.ucm.tfg.entities.Film;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;

/**
 * All http requests against films route
 */
public class FilmRequest {

    private static String url = "http://tfg-spring.herokuapp.com/films/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/films/";

    public FilmRequest() {
    }

    // Given a film id, returns through callback the film result
    public static <T> void getFilmById(Activity activity, long id, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}")
                .addPathVariable("id", "" + id)
                .execute(callback, c);
    }

    // Given a film uuid (vuforia), returns through callback the film result
    public static <T> void getFilmByUUID(Activity activity, String uuid, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "uuid/" + "{uuid}")
                .addPathVariable("uuid", "" + uuid)
                .execute(callback, c);
    }

    // Given a film name, returns through callback all films containing the string
    public static <T> void searchFilmsByName(Activity activity, String name, Request.ClientResponse<ArrayList<Film>> callback) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("name", name)
                .url(develop_url + "search/{name}")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }

    // Returns through callback all films
    public static <T> void getFilms(Activity activity, Request.ClientResponse<ArrayList<Film>> callback) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url)
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }
}
