package com.ucm.tfg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

public class PruebaInfoPeli extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_info_peli);
        Intent intent = this.getIntent();
        String film = intent.getStringExtra("film");
        //JSONObject film_json = new JSONObject(film);

    }
}
