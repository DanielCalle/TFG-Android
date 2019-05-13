package com.ucm.tfg.service;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucm.tfg.R;

import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A generic class designed to do all http requests
 * Its implemented in a builder pattern for an easier use (concat methods)
 * Also its async so the results are returned as callback
 */
public class Service {

    private RestTemplate restTemplate;

    private String url;
    private HttpMethod method;
    private Map<String, String> pathVariables;
    private HttpHeaders headers;
    private Object body;

    private WeakReference<Activity> context;

    private static Service instance;

    private Service() {
        restTemplate = new RestTemplate();
        pathVariables = new HashMap<>();
        headers = new HttpHeaders();
    }

    // Thread safe
    public static synchronized Service getInstance() {
        instance = new Service();
        return instance;
    }

    // Setting the activity calling this class, because as this class is async then it could
    // be when the result has reached the activity was already destroyed
    public Service setContext(Activity activity) {
        context = new WeakReference<>(activity);
        return this;
    }

    // Setting url
    public Service url(String url) {
        this.url = url;
        return this;
    }

    public Service get() {
        method = HttpMethod.GET;
        return this;
    }

    public Service post() {
        method = HttpMethod.POST;
        return this;
    }

    public Service put() {
        method = HttpMethod.PUT;
        return this;
    }

    public Service delete() {
        method = HttpMethod.DELETE;
        return this;
    }

    // Adding values to the path, for example: /route/{id}
    public Service addPathVariable(String key, String value) {
        pathVariables.put(key, value);
        return this;
    }

    // Setting content type
    public Service setContentType(MediaType mediaType) {
        headers.setContentType(mediaType);
        return this;
    }

    // Setting accepting type
    public Service setAccept(MediaType mediaType) {
        headers.setAccept(Arrays.asList(mediaType));
        return this;
    }

    // Adding pair to header
    public Service addHeader(String key, String value) {
        headers.add(key, value);
        return this;
    }

    // Adding request body
    public <T> Service body(T body) {
        this.body = body;
        return this;
    }

    // Execution
    public <T> void execute(ClientResponse<T> callback, Class<T> responseType) {
        Activity activity = context.get();
        if (activity != null && !activity.isFinishing()) {
            // Checking connectivity
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (wifi.isConnected()) {
                URI uri = new UriTemplate(url).expand(pathVariables);
                HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
                new AsyncTask<Void, Void, ResponseEntity<T>>() {

                    @Override
                    protected ResponseEntity<T> doInBackground(Void... voids) {
                        try {
                            return restTemplate.exchange(uri, method, httpEntity, responseType);
                        } catch (Exception e) {
                            callback.onError(e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(ResponseEntity<T> entity) {
                        Activity activity = context.get();
                        // Checking if the activity has been destroyed before calling the callback
                        if (activity != null && !activity.isFinishing()) {
                            // Http status with 2xx is accepted only
                            if (entity != null && entity.getStatusCode().toString().startsWith("2")) {
                                callback.onSuccess(entity.getBody());
                            } else {
                                callback.onError(entity == null ? "" : entity.getStatusCode().toString());
                            }
                        }
                    }
                }.execute();
            } else {
                callback.onError(context.get().getString(R.string.no_wifi));
            }
        }
    }

    // Execution when the expecting data is an array or list of entities
    public <T> void execute(ClientResponse<T> callback, ParameterizedTypeReference<T> responseType) {
        Activity activity = context.get();
        if (activity != null && !activity.isFinishing()) {
            // Checking connectivity
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (wifi.isConnected()) {
                URI uri = new UriTemplate(url).expand(pathVariables);
                HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
                new AsyncTask<Void, Void, ResponseEntity<T>>() {

                    @Override
                    protected ResponseEntity<T> doInBackground(Void... voids) {
                        try {
                            return restTemplate.exchange(uri, method, httpEntity, responseType);
                        } catch (Exception e) {
                            callback.onError(e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(ResponseEntity<T> entity) {
                        Activity activity = context.get();
                        // Checking if the activity has been destroyed before calling the callback
                        if (activity != null && !activity.isFinishing()) {
                            // Http status with 2xx is accepted only
                            if (entity != null && entity.getStatusCode().toString().startsWith("2")) {
                                callback.onSuccess(entity.getBody());
                            } else {
                                callback.onError(entity == null ? "" : entity.getStatusCode().toString());
                            }
                        }
                    }
                }.execute();
            } else {
                callback.onError(context.get().getString(R.string.no_wifi));
            }
        }
    }

    // The callback
    public interface ClientResponse<T> {

        // When everything is successful
        void onSuccess(T result);

        // When has ocurred any problem
        void onError(String error);
    }

}
