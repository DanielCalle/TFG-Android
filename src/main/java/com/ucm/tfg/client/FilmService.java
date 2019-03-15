package com.ucm.tfg.client;

public class FilmService {

    private static String url = "http://tfg-spring.herokuapp.com/film/";

    public FilmService() {}

    public static <T> void getFilmById(String uuid, ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .addParam("id", uuid)
                .GET(FilmService.url + "{id}", callback, c);
    }

}
