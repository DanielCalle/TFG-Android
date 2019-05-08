package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.entities.Film;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;

public class RecommendationService {

    private static String url = "http://tfg-spring.herokuapp.com/recommendations/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/recommendations/";

    public RecommendationService() {
    }

    public static <T> void getRecommendedFilms(Activity activity, long id, Service.ClientResponse<ArrayList<Film>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}/films")
                .addPathVariable("id", "" + id)
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }

    public static <T> void getPremiereFilms(Activity activity, Service.ClientResponse<ArrayList<Film>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "premiere")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }

    public static <T> void getTrendingFilms(Activity activity, Service.ClientResponse<ArrayList<Film>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "trending")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }
}
