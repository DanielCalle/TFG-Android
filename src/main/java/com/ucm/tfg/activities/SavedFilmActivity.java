package com.ucm.tfg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ucm.tfg.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SavedFilmActivity extends AppCompatActivity {

    private static String LOGTAG = "SavedFilmActivity";

    private TextView saved_film_info;

    private String saved_film_uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_film);

        Intent intent = this.getIntent();
        String film_uuid = intent.getStringExtra("uuid");

        try {
            JSONObject json = new JSONObject(film_uuid);
            fillData(json);
        } catch (JSONException e) {
            Log.e(LOGTAG, "Error at parsing");
        }
        Toast.makeText(this, this.saved_film_uuid,
                Toast.LENGTH_LONG).show();
        saved_film_info = (TextView)findViewById(R.id.saved_film_info);
        String url = "https://tfg-spring.herokuapp.com/film/" + this.saved_film_uuid;
        Log.i(LOGTAG,  url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        saved_film_info.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e(LOGTAG, error.toString());
                    }
                });
    }
    private void fillData(JSONObject json) throws JSONException {
        this.saved_film_uuid = !json.isNull("uuid") ? "" + json.getString("uuid") : "";
    }

}
