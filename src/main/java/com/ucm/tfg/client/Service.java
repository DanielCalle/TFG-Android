package com.ucm.tfg.client;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class Service {

    private RestTemplate restTemplate;
    private Map<String, String> params;
    private WeakReference<Activity> context;

    private static Service instance;

    private Service() {
        restTemplate = new RestTemplate();
        params = new HashMap<>();
    }

    // Thread safe
    public static synchronized Service getInstance() {
        instance = new Service();
        return instance;
    }

    public Service setContext(Activity activity) {
        context = new WeakReference<>(activity);
        return this;
    }

    public Service addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public <T> void GET(String url, ClientResponse<T> callback, Class<T> c) {
        new AsyncTask<String, Void, ResponseEntity<T>>() {

            @Override
            protected ResponseEntity<T> doInBackground(String... strings) {
                return restTemplate.getForEntity(strings[0], c, params);
            }

            @Override
            protected void onPostExecute(ResponseEntity<T> entity) {
                Activity activity = context.get();
                if (activity != null && !activity.isFinishing()) {
                    if (entity.getStatusCode() == HttpStatus.OK) {
                        callback.onSuccess(entity.getBody());
                    } else {
                        callback.onError(entity.getStatusCode().toString());
                    }
                }
            }
        }.execute(url);
    }

    public <T> void POST(String url, Object object, ClientResponse<T> callback, Class<T> c) {
        new AsyncTask<String, Void, ResponseEntity<T>>() {

            @Override
            protected ResponseEntity<T> doInBackground(String... strings) {
                return restTemplate.postForEntity(strings[0], object, c, params);
            }

            @Override
            protected void onPostExecute(ResponseEntity<T> entity) {
                Activity activity = context.get();
                if (activity != null && !activity.isFinishing()) {
                    if (entity.getStatusCode() == HttpStatus.OK) {
                        callback.onSuccess(entity.getBody());
                    } else {
                        callback.onError(entity.getStatusCode().toString());
                    }
                }
            }
        }.execute(url);
    }

    public void PUT(String url, Object object) {
        new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... strings) {
                restTemplate.put(strings[0], object, params);
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {}
        }.execute(url);
    }

    public void DELETE(String url) {
        new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... strings) {
                restTemplate.delete(strings[0], params);
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {}
        }.execute(url);
    }

    public interface ClientResponse<T> {

        void onSuccess(T result);

        void onError(String error);
    }

}
