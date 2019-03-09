package com.ucm.tfg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.ucm.tfg.R;

import org.json.JSONException;
import org.json.JSONObject;
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
    }

}
