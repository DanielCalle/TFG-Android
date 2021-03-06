package com.ucm.tfg.requests;

import android.app.Activity;

import com.ucm.tfg.entities.Film;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;

/**
 * All http requests against films route
 */
public class RecommendationRequest {

    private static String url = "http://tfg-spring.herokuapp.com/recommendations/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/recommendations/";

    public RecommendationRequest() {
    }

    // Returns film recommendations given for a specific user id
    public static <T> void getRecommendedFilms(Activity activity, long id, Request.ClientResponse<ArrayList<Film>> callback) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}/films")
                .addPathVariable("id", "" + id)
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }

    // Returns recent films on premiere
    public static <T> void getPremiereFilms(Activity activity, Request.ClientResponse<ArrayList<Film>> callback) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "premiere")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }

    // Returns the most popular films
    public static <T> void getTrendingFilms(Activity activity, Request.ClientResponse<ArrayList<Film>> callback) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "trending")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }

    // Returns recommendations for plans given two user ids (for unity case)
    public static <T> void getThreePlans(Activity activity, long id, String friendId, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}/plans/{friendId}")
                .addPathVariable("id", "" + id)
                .addPathVariable("friendId", "" + friendId)
                .execute(callback, c);
    }

    // Get a random film
    public static <T> void getRandomFilm(Activity activity, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "random")
                .execute(callback, c);
    }
}
