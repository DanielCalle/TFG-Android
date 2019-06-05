package com.ucm.tfg.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.entities.UserFilm;
import com.ucm.tfg.requests.FilmRequest;
import com.ucm.tfg.requests.Request;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.requests.UserFilmRequest;


import org.json.JSONObject;

import java.util.Date;

/**
 * When in unity proyect has clicked save film
 */
public class SavedFilmActivity extends AppCompatActivity {

    private static String LOGTAG = "SavedFilmActivity";

    private String uuid;
    private Button infoButton;
    private ImageView filmPoster;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_film);
        uuid = getIntent().getStringExtra("uuid");
        filmPoster = findViewById(R.id.film_poster);
        infoButton = findViewById(R.id.info_button);
        progressBar = findViewById(R.id.loading);

        infoButton.setVisibility(View.INVISIBLE);
        infoButton.setEnabled(false);

        FilmRequest.getFilmByUuid(SavedFilmActivity.this, uuid, new Request.ClientResponse<Film>() {

            @Override
            public void onSuccess(Film film) {
                infoButton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

                Picasso.get()
                        .load(film.getImageURL())
                        .fit()
                        .into(filmPoster);

                UserFilm userFilm = new UserFilm();
                userFilm.setUserId(Session.user.getId());
                userFilm.setFilmId(film.getId());
                UserFilmRequest.postUserFilm(SavedFilmActivity.this, userFilm, new Request.ClientResponse<UserFilm>() {
                    @Override
                    public void onSuccess(UserFilm userFilm) {
                        infoButton.setEnabled(true);
                        Toast.makeText(SavedFilmActivity.this, getString(R.string.film_saved), Toast.LENGTH_SHORT).show();
                        infoButton.setOnClickListener(view -> {
                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                                    .makeSceneTransitionAnimation(
                                            SavedFilmActivity.this,
                                            Pair.create(filmPoster, "film_poster")
                                    );
                            Intent i = new Intent(SavedFilmActivity.this, FilmActivity.class);
                            i.putExtra("film", film);
                            startActivity(i, optionsCompat.toBundle());
                        });
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(SavedFilmActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }, UserFilm.class);

            }

            @Override
            public void onError(String error) {
                Toast.makeText(SavedFilmActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        }, Film.class);
    }
}
