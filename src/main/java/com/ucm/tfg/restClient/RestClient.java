package com.ucm.tfg.restClient;

import com.ucm.tfg.entities.Film;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestClient {


    @GET("/film/")
    Call<Film> getData(@Query("uuid") String uuid);

}
