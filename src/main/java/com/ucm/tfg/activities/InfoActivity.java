package com.ucm.tfg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.entities.Film;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {

    private static String LOGTAG = "InfoActivity";

    private ActionBar actionBar;
    private ImageView filmPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Film film = (Film) getIntent().getExtras().getSerializable("film");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(film.getName());
        }

        filmPoster = findViewById(R.id.film_poster);

        Picasso.get()
                .load(film.getImageURL())
                .into(filmPoster);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
