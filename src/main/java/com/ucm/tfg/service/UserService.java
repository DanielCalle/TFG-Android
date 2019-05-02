package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Friendship;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;

public class UserService {
    private static String url = "http://tfg-spring.herokuapp.com/users/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/users/";

    public UserService() {}

    public static <T> void register(Activity activity, User user, Service.ClientResponse callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .body(user)
                .url(develop_url)
                .execute(callback, c);
    }

    public static <T> void login(Activity activity, User user, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .body(user)
                .url(develop_url + "login")
                .execute(callback, c);
    }

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

    public static <T> void getFriends(Activity activity, String uuid, Service.ClientResponse<ArrayList<User>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get().addPathVariable("id", uuid)
                .url(develop_url + "{id}" + "/friends")
                .execute(callback, new ParameterizedTypeReference<ArrayList<User>>(){});
    }

    public static <T> void searchUsersByName(Activity activity, String name, Service.ClientResponse<ArrayList<User>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("name", name)
                .url(develop_url + "search/{name}")
                .execute(callback, new ParameterizedTypeReference<ArrayList<User>>() {
                });
    }

    public static <T> void getFriendsPlans(Activity activity, String uuid, Service.ClientResponse<ArrayList<Plan>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get().addPathVariable("id", uuid)
                .url(develop_url + "{id}" + "/friendships" + "/plans")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Plan>>(){});
    }

}
