package com.ucm.tfg.service;

import android.app.Activity;

public class FilmService {

    private static String url = "http://tfg-spring.herokuapp.com/films/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/films/";

    public FilmService() {}

    public static <T> void getFilmById(Activity activity, String uuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .addParam("id", uuid)
                .GET(FilmService.develop_url + "{id}", callback, c);
    }

}
