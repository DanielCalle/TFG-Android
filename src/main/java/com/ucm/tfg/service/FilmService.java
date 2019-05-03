package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.Session;
import com.ucm.tfg.entities.Film;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;

public class FilmService {

    private static String url = "http://tfg-spring.herokuapp.com/films/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/films/";

    public FilmService() {
    }

    public static <T> void getFilmById(Activity activity, long id, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}")
                .addPathVariable("id", "" + id)
                .execute(callback, c);
    }

    public static <T> void searchFilmsByName(Activity activity, String name, Service.ClientResponse<ArrayList<Film>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("name", name)
                .url(develop_url + "search/{name}")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }

    public static <T> void getFilms(Activity activity, Service.ClientResponse<ArrayList<Film>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url)
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>() {
                });
    }
}
