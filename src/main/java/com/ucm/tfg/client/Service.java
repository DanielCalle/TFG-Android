package com.ucm.tfg.client;

import android.os.AsyncTask;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Service {

    private RestTemplate restTemplate;
    private Map<String, String> params;

    private static Service instance;

    private Service() {
        restTemplate = new RestTemplate();
        params = new HashMap<>();
    }

    // Thread safe
    public static synchronized Service getInstance() {
        // Lazy initialization
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public Service addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public <T> void GET(String url, ClientResponse<T> callback, Class<T> c) {
        new AsyncTask<String, Void, T>() {

            @Override
            protected T doInBackground(String... strings) {
                T result = restTemplate.getForObject(strings[0], c, params);
                return result;
            }

            @Override
            protected void onPostExecute(T film) {
                callback.onSuccess(film);
            }
        }.execute(url);
    }

}
