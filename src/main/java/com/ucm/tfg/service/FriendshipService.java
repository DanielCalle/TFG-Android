package com.ucm.tfg.service;

import android.app.Activity;

public class FriendshipService {

    private static String url = "";
    private static String develop_url = "http://filmar-develop.herokuapp.com/friendships/";

    public FriendshipService() {}

    public static <T> void getFriendsById(Activity activity, String uuid1, String uuid2, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("requesterUuid", uuid1)
                .addPathVariable("friendUuid", uuid2)
                .url(develop_url + "/{requesterUuid}/{friendUuid}")
                .execute(callback, c);
    }
    

}
