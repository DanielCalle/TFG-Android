package com.ucm.tfg.service;

import android.app.Activity;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public Service setContext(Activity activity) {
        context = new WeakReference<>(activity);
        return this;
    }

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

    public Service addPathVariable(String key, String value) {
        pathVariables.put(key, value);
        return this;
    }

    public Service setContentType(MediaType mediaType) {
        headers.setContentType(mediaType);
        return this;
    }

    public Service setAccept(MediaType mediaType) {
        headers.setAccept(Arrays.asList(mediaType));
        return this;
    }

    public Service addHeader(String key, String value) {
        headers.add(key, value);
        return this;
    }

    public <T> Service body(T body) {
        this.body = body;
        return this;
    }

    public <T> void execute(ClientResponse<T> callback, Class<T> responseType) {
        URI uri = new UriTemplate(url).expand(pathVariables);
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        new AsyncTask<Void, Void, ResponseEntity<T>>() {

            @Override
            protected ResponseEntity<T> doInBackground(Void... voids) {
                try {
                    return restTemplate.exchange(uri, method, httpEntity, responseType);
                } catch (Exception e) {
                    callback.onError("error");
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ResponseEntity<T> entity) {
                Activity activity = context.get();
                if (activity != null && !activity.isFinishing()) {
                    callback.onSuccess(entity.getBody());
                }
            }
        }.execute();
    }

    public <T> void execute(ClientResponse<T> callback, ParameterizedTypeReference<T> responseType) {
        URI uri = new UriTemplate(url).expand(pathVariables);
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        new AsyncTask<Void, Void, ResponseEntity<T>>() {

            @Override
            protected ResponseEntity<T> doInBackground(Void... voids) {
                return restTemplate.exchange(uri, method, httpEntity, responseType);
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
        }.execute();
    }

    public interface ClientResponse<T> {

        void onSuccess(T result);

        void onError(String error);
    }

}
