package com.ucm.tfg.client;

import android.app.Activity;
import android.content.Context;

public class FilmService {

    private static String url = "http://tfg-spring.herokuapp.com/film/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/film/";

    public FilmService() {}

    public static <T> void getFilmById(Activity activity, String uuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .addParam("id", uuid)
                .GET(FilmService.develop_url + "{id}", callback, c);
    }

}
