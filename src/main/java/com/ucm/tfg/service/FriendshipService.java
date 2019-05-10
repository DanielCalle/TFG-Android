package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.entities.Friendship;

public class FriendshipService {

    private static String url = "";
    private static String develop_url = "http://filmar-develop.herokuapp.com/friendships/";

    public FriendshipService() {}


    public static <T> void areFriends(Activity activity, long id1, long id2, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("requesterUuid", "" +id1)
                .addPathVariable("friendUuid", "" + id2)
                .url(develop_url + "{requesterUuid}/{friendUuid}")
                .execute(callback, c);
    }

    public static <T> void request(Activity activity, long requestId, long friendId, Service.ClientResponse<T> callback, Class<T> c){
        Service.getInstance()
                .setContext(activity)
                .post()
                .url(develop_url + "/{requestId}/{friendId}/request")
                .addPathVariable("requestId", requestId + "")
                .addPathVariable("friendId", friendId + "")
                .execute(callback, c);
    }

    public static <T> void accept(Activity activity, long requestId, long friendId, Service.ClientResponse<T> callback, Class<T> c){
        Service.getInstance()
                .setContext(activity)
                .put()
                .url(develop_url + "/{requestId}/{friendId}/accept")
                .addPathVariable("requestId", requestId + "")
                .addPathVariable("friendId", friendId + "")
                .execute(callback, c);
    }

    public static <T> void decline(Activity activity, long requestId, long friendId, Service.ClientResponse<T> callback, Class<T> c){
        Service.getInstance()
                .setContext(activity)
                .delete()
                .url(develop_url + "/{requestId}/{friendId}/decline")
                .addPathVariable("requestId", requestId + "")
                .addPathVariable("friendId", friendId + "")
                .execute(callback, c);
    }

    public static <T> void delete(Activity activity, long requestId, long friendId, Service.ClientResponse<T> callback, Class<T> c){
        Service.getInstance()
                .setContext(activity)
                .delete()
                .url(develop_url + "/{requestId}/{friendId}")
                .addPathVariable("requestId", requestId + "")
                .addPathVariable("friendId", friendId + "")
                .execute(callback, c);
    }
}
