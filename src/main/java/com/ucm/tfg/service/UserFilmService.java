package com.ucm.tfg.service;

import android.app.Activity;


import com.ucm.tfg.entities.UserFilm;

import org.springframework.http.MediaType;

public class UserFilmService {
    private static String url = "http://tfg-spring.herokuapp.com/user-films/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/user-films/";

    public UserFilmService(){}

    public static <T> void postUserFilm(Activity activity, UserFilm userFilm, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity).setContentType(MediaType.APPLICATION_JSON)
                .addBody("userUuid", userFilm.getUserUuid()).addBody("filmUuid", userFilm.getFilmUuid())
                .post()
                .url(develop_url)
                .execute(callback, c);
    }
}
