package com.ucm.tfg.client;

import android.os.AsyncTask;

import org.springframework.web.client.RestTemplate;

public class Service {

    private RestTemplate restTemplate;

    private static Service instance;

    private Service() {
        restTemplate = new RestTemplate();
    }

    // Thread safe
    public static synchronized Service getInstance() {
        // Lazy initialization
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public <T> void execute(String url, ClientResponse<T> callback, Class<T> c) {
        new AsyncTask<String, Void, T>() {

            @Override
            protected T doInBackground(String... strings) {
                T result = restTemplate.getForObject(strings[0], c);
                return result;
            }

            @Override
            protected void onPostExecute(T film) {
                callback.onSuccess(film);
            }
        }.execute(url);
    }

}
