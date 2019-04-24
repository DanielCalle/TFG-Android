package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.UserFilm;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserFilmService;
import com.ucm.tfg.views.ExpandableTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InfoActivity extends AppCompatActivity {

    private final static String LOGTAG = "FilmActivity";
    private final static int FORM_REQUEST = 1;

    private ActionBar actionBar;
    private ImageView filmPoster;

    private ProgressBar progressBar;
    private TextView progressText;
    private SeekBar progressController;

    private Film film;
    private UserFilm userFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        film = (Film) getIntent().getExtras().getSerializable("film");

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

        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        progressController = findViewById(R.id.progress_controller);

        // enable/disable rating
        progressBar.setOnClickListener((View v) -> {
            progressController.setVisibility(
                    progressController.isShown() ? View.GONE : View.VISIBLE
            );
        });

        // change rating
        progressController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                progressText.setText("" + progress / 10.0 + "/10");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (userFilm != null) {
                    userFilm.setRating(((float) (seekBar.getProgress() / 10.0)));
                    UserFilmService.rate(InfoActivity.this, userFilm, new Service.ClientResponse<UserFilm>() {
                        @Override
                        public void onSuccess(UserFilm result) {
                            Toast.makeText(InfoActivity.this, getString(R.string.rated_film), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {

                        }
                    }, UserFilm.class);
                }
            }
        });

        Button addToPlan = findViewById(R.id.add_to_plan);
        addToPlan.setOnClickListener((View v) -> {
            Intent intent = new Intent(InfoActivity.this, FormActivity.class);
            intent.putExtra(getString(R.string.plan_date), "date");
            intent.putExtra(getString(R.string.plan_location), "text");
            intent.putExtra(getString(R.string.plan_description), "text");
            startActivityForResult(intent, FORM_REQUEST);
        });

        ImageButton expandable = findViewById(R.id.expandable_button);
        ExpandableTextView description = findViewById(R.id.description);
        description.setText(film.getSynopsis());
        description.setExpandListener(expandable);

        TextView genre = findViewById(R.id.genre);
        genre.setText(film.getGenre());
        TextView director = findViewById(R.id.director);
        director.setText(film.getdirector());

        FloatingActionButton trailer = findViewById(R.id.trailer);
        trailer.setOnClickListener((View v) -> {
            Intent youtube = new Intent(Intent.ACTION_VIEW);
            youtube.setData(Uri.parse(film.getTrailerURL()));
            startActivity(youtube);
        });

        UserFilmService.get(
                InfoActivity.this,
                getSharedPreferences(Session.SESSION_FILE, 0).getString(Session.USER, null),
                film.getUuid(),
                new Service.ClientResponse<UserFilm>() {
            @Override
            public void onSuccess(UserFilm result) {
                userFilm = result;
                // setting film rating data
                progressBar.setProgress((int) result.getRating() * 10);
                progressController.setProgress((int) result.getRating() * 10);
                progressText.setText("" + result.getRating() + "/10");
            }

            @Override
            public void onError(String error) {

            }
        }, UserFilm.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FORM_REQUEST:
                if (data != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Plan plan = new Plan();
                    plan.setCreator("a");
                    plan.setFilmUuid(film.getUuid());
                    try {
                        plan.setDate(dateFormat.parse(data.getExtras().getString(getString(R.string.plan_date))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    plan.setLocation(data.getExtras().getString(getString(R.string.plan_location)));
                    plan.setDescription(data.getExtras().getString(getString(R.string.plan_description)));
                    PlanService.createPlan(InfoActivity.this, plan, new Service.ClientResponse<Plan>() {

                        @Override
                        public void onSuccess(Plan result) {
                            Toast.makeText(InfoActivity.this, getText(R.string.plan_created), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(InfoActivity.this, error, Toast.LENGTH_SHORT).show();
                        }

                    }, Plan.class);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_film, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: finish(); break;
            case R.id.delete:
                UserFilmService.delete(
                        InfoActivity.this,
                        getSharedPreferences(Session.SESSION_FILE, 0).getString(Session.USER, null),
                        film.getUuid(),
                        new Service.ClientResponse<UserFilm>() {
                            @Override
                            public void onSuccess(UserFilm result) {
                                Toast.makeText(InfoActivity.this, getString(R.string.film_deleted), Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, UserFilm.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
