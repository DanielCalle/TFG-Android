package com.ucm.tfg.service;

import android.app.Activity;

public class UserService {
    private static String url = "http://tfg-spring.herokuapp.com/users/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/users/";

    public UserService() {}

    public static <T> void getUserById(Activity activity, String uuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .addParam("id", uuid)
                .GET(UserService.develop_url + "{id}", callback, c);
    }
}
