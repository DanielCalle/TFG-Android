package com.ucm.tfg.client;

public class FriendshipService {

    private static String url = "";
    private static String develop_url = "http://filmar-develop.herokuapp.com/friendship/";

    public FriendshipService() {}

    public static <T> void getFriendsById(String uuid, ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .addParam("id", uuid)
                .GET(FriendshipService.develop_url + "{id}", callback, c);
    }

}
