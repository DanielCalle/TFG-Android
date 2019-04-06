package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;

public class UserService {
    private static String url = "http://tfg-spring.herokuapp.com/users/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/users/";

    public UserService() {}

    public static <T> void getUserById(Activity activity, String uuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("id", uuid)
                .url(develop_url + "{id}")
                .execute(callback, c);
    }

    public static <T> void getUserFilmsById(Activity activity,String uuid, Service.ClientResponse<ArrayList<Film>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get().addPathVariable("id", uuid)
                .url(develop_url + "{id}" + "/films")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>(){});
    }

    public static <T> void getUserPlansById(Activity activity, String uuid, Service.ClientResponse<ArrayList<Plan>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get().addPathVariable("id", uuid)
                .url(develop_url + "{id}" + "/plans")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Plan>>(){});
    }

}
