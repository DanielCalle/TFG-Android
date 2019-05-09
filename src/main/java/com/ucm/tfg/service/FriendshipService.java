package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.entities.Friendship;

public class FriendshipService {

    private static String url = "";
    private static String develop_url = "http://filmar-develop.herokuapp.com/friendships/";

    public FriendshipService() {}

    public static <T> void areFriends(Activity activity, long friendId, long otherFriendId, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("friendId", "" + friendId)
                .addPathVariable("otherFriendId", "" + otherFriendId)
                .url(develop_url + "/{friendId}/{otherFriendId}")
                .execute(callback, c);
    }
    public static <T> void addFriend(Activity activity, Friendship friendship, Service.ClientResponse<T> callback, Class<T> c){
        Service.getInstance()
                .setContext(activity)
                .post()
                .body(friendship)
                .url(develop_url)
                .execute(callback, c);

    }
    

}
