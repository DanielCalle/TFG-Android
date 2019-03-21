package com.ucm.tfg.service;

import android.app.Activity;

public class FriendshipService {

    private static String url = "";
    private static String develop_url = "http://filmar-develop.herokuapp.com/user/friendships/";

    public FriendshipService() {}

    public static <T> void getFriendsById(Activity activity, String uuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("id", uuid)
                .url(develop_url + "{id}")
                .execute(callback, c);
    }
    

}
