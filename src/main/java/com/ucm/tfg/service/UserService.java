package com.ucm.tfg.service;

import android.app.Activity;

public class UserService {
    private static String url = "http://tfg-spring.herokuapp.com/user/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/user/";

    public UserService() {}

    public static <T> void getUserById(Activity activity, String uuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .addParam("id", uuid)
                .GET(UserService.develop_url + "{id}", callback, c);
    }
}
