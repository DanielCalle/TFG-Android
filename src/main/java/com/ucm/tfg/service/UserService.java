package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Friendship;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;

/**
 * All http requests against users route
 */
public class UserService {
    private static String url = "http://tfg-spring.herokuapp.com/users/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/users/";

    public UserService() {}

    // Creates a new user
    public static <T> void register(Activity activity, User user, Service.ClientResponse callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .body(user)
                .url(develop_url)
                .execute(callback, c);
    }

    // Login action
    public static <T> void login(Activity activity, User user, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .body(user)
                .url(develop_url + "login")
                .execute(callback, c);
    }

    // returns a user given its id
    public static <T> void getUserById(Activity activity, long id, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}")
                .addPathVariable("id", "" + id)
                .execute(callback, c);
    }

    // Returns all films saved for a given user
    public static <T> void getUserFilmsById(Activity activity, long id, Service.ClientResponse<ArrayList<Film>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}" + "/films")
                .addPathVariable("id", "" + id)
                .execute(callback, new ParameterizedTypeReference<ArrayList<Film>>(){});
    }

    // Returns all plans in which the user is involved
    public static <T> void getUserPlansById(Activity activity, long id, Service.ClientResponse<ArrayList<Plan>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}" + "/plans")
                .addPathVariable("id", "" + id)
                .execute(callback, new ParameterizedTypeReference<ArrayList<Plan>>(){});
    }

    // Returns all friends for a user
    public static <T> void getFriends(Activity activity, long id, Service.ClientResponse<ArrayList<User>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}" + "/friends")
                .addPathVariable("id", "" + id)
                .execute(callback, new ParameterizedTypeReference<ArrayList<User>>(){});
    }

    // Returns all users which name contains the string
    public static <T> void searchUsersByName(Activity activity, String name, Service.ClientResponse<ArrayList<User>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("name", name)
                .url(develop_url + "search/{name}")
                .execute(callback, new ParameterizedTypeReference<ArrayList<User>>() {
                });
    }

    // Retuns back a user given its uuid (vuforia)
    public static <T> void getUserByUUID(Activity activity, String uuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "uuid/"+ "{uuid}")
                .addPathVariable("uuid", "" + uuid)
                .execute(callback, c);
    }

}
