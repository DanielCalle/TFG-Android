package com.ucm.tfg.client;

public interface ClientResponse<T> {

    void onSuccess(T result);

    void onError(String error);

}
