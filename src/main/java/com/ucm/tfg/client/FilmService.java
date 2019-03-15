package com.ucm.tfg.client;

public class FilmService {

    private static String url = "http://tfg-spring.herokuapp.com/film/";

    public FilmService() {}

    public static void getFilmById(String uuid, ClientResponse<String> callback) {
        Service.getInstance().execute(FilmService.url + uuid, callback, String.class);
    }

}
