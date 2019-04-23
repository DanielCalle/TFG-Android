package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.RatingFilm;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;

public class FilmService {

    private static String url = "http://tfg-spring.herokuapp.com/films/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/films/";

    public FilmService() {
    }

    public static <T> void getFilmById(Activity activity, String uuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("id", uuid)
                .url(develop_url + "{id}")
                .execute(callback, c);
    }

    public static <T> void getFilms(Activity activity, Service.ClientResponse<ArrayList<Film>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url)
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }

    public static <T> void getRating(Activity activity, String userUuid, String filmUuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{uuid}/user/{userUuid}/rating")
                .addPathVariable("uuid", filmUuid)
                .addPathVariable("userUuid", userUuid)
                .execute(callback, c);
    }

    public static <T> void rate(Activity activity, RatingFilm ratingFilm, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .url(develop_url + "rate")
                .body(ratingFilm)
                .execute(callback, c);
    }
}
