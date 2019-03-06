package com.ucm.tfg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucm.tfg.R;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.restClient.RestClient;

import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class SavedFilmActivity extends AppCompatActivity {

    private static String LOGTAG = "SavedFilmActivity";

    private TextView saved_film_info;
    private String info;
    private String uuid;
    private String saved_film_uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_film);

        Intent intent = this.getIntent();
        saved_film_info = findViewById(R.id.saved_film_info);
        this.info = intent.getStringExtra("uuid");
        loadJSON();
    }
    private void loadJSON() {
        try {
            JSONObject json = new JSONObject(this.info);
            this.uuid = !json.isNull("uuid") ? json.getString("uuid") : "";
        } catch (JSONException e) {
            Log.e(LOGTAG, "Error at parsing");
        }
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tfg-spring.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RestClient restClient = retrofit.create(RestClient.class);
        Call<Film> call = restClient.getData(this.uuid);

        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                switch (response.code()) {
                    case 200:

                        Film data = response.body();
                        Log.i(LOGTAG, response.body().toString());
                        saved_film_info.setText(data.getName());
                        break;
                    case 401:

                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

}
