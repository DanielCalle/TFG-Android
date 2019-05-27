package com.ucm.tfg.service;

import android.app.Activity;

/**
 * All http requests against friendships route
 */
public class FriendshipRequest {

    private static String url = "";
    private static String develop_url = "http://filmar-develop.herokuapp.com/friendships/";

    public FriendshipRequest() {}

    // Given a two users id, returns through callback the friendship relation telling if they are friends
    public static <T> void areFriends(Activity activity, long id1, long id2, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("requesterUuid", "" +id1)
                .addPathVariable("friendUuid", "" + id2)
                .url(develop_url + "{requesterUuid}/{friendUuid}")
                .execute(callback, c);
    }

    // Given a requester user id A and the receiver user id B, A sends a frienship request to B
    public static <T> void request(Activity activity, long requestId, long friendId, Request.ClientResponse<T> callback, Class<T> c){
        Request.getInstance()
                .setContext(activity)
                .post()
                .url(develop_url + "/{requestId}/{friendId}/request")
                .addPathVariable("requestId", requestId + "")
                .addPathVariable("friendId", friendId + "")
                .execute(callback, c);
    }

    // Given a requester user id A and the receiver user id B, B accepts A as friend
    public static <T> void accept(Activity activity, long requestId, long friendId, Request.ClientResponse<T> callback, Class<T> c){
        Request.getInstance()
                .setContext(activity)
                .put()
                .url(develop_url + "/{requestId}/{friendId}/accept")
                .addPathVariable("requestId", requestId + "")
                .addPathVariable("friendId", friendId + "")
                .execute(callback, c);
    }

    // Given a requester user id A and the receiver user id B, B declines A as friend
    public static <T> void decline(Activity activity, long requestId, long friendId, Request.ClientResponse<T> callback, Class<T> c){
        Request.getInstance()
                .setContext(activity)
                .delete()
                .url(develop_url + "/{requestId}/{friendId}/decline")
                .addPathVariable("requestId", requestId + "")
                .addPathVariable("friendId", friendId + "")
                .execute(callback, c);
    }

    // Given a requester user id A and the receiver user id B, they are not friends anymore
    public static <T> void delete(Activity activity, long requestId, long friendId, Request.ClientResponse<T> callback, Class<T> c){
        Request.getInstance()
                .setContext(activity)
                .delete()
                .url(develop_url + "/{requestId}/{friendId}")
                .addPathVariable("requestId", requestId + "")
                .addPathVariable("friendId", friendId + "")
                .execute(callback, c);
    }
}
