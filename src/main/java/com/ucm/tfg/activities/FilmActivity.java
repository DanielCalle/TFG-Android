package com.ucm.tfg.activities;

import android.content.Intent;
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
import com.ucm.tfg.requests.PlanRequest;
import com.ucm.tfg.requests.Request;
import com.ucm.tfg.requests.UserFilmRequest;
import com.ucm.tfg.views.ExpandableTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Shows all data for a given film
 */
public class FilmActivity extends AppCompatActivity {

    private final static String LOGTAG = "FilmActivity";
    private final static int FORM_REQUEST = 1;

    private ActionBar actionBar;
    private ImageView filmPoster;

    private ProgressBar progressBar;
    private TextView progressText;
    private SeekBar progressController;

    private Film film;
    private UserFilm userFilm;

    private MenuItem favoriteResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

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

        // A circle progress bar representin the user valoration for this film
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        // The view used to valorate the film
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
                    UserFilmRequest.rate(FilmActivity.this, userFilm, new Request.ClientResponse<UserFilm>() {
                        @Override
                        public void onSuccess(UserFilm result) {
                            Toast.makeText(FilmActivity.this, getString(R.string.rated_film), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(FilmActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }, UserFilm.class);
                }
            }
        });

        Button addToPlan = findViewById(R.id.add_to_plan);
        // Creating a new plan
        addToPlan.setOnClickListener((View v) -> {
            Intent intent = new Intent(FilmActivity.this, FormActivity.class);
            intent.putExtra(getString(R.string.plan_title), "text");
            intent.putExtra(getString(R.string.plan_date), "date");
            intent.putExtra(getString(R.string.plan_location), "text");
            intent.putExtra(getString(R.string.plan_description), "text");
            startActivityForResult(intent, FORM_REQUEST);
        });

        // Film synopsis
        ImageButton expandable = findViewById(R.id.expandable_button);
        ExpandableTextView description = findViewById(R.id.description);
        description.setText(film.getSynopsis());
        description.setExpandListener(expandable);

        // Film genre
        TextView genre = findViewById(R.id.genre);
        genre.setText(film.getGenre());
        TextView director = findViewById(R.id.director);
        director.setText(film.getDirector());

        // Redirect to youtube to see the trailer
        FloatingActionButton trailer = findViewById(R.id.trailer);
        trailer.setOnClickListener((View v) -> {
            Intent youtube = new Intent(Intent.ACTION_VIEW);
            youtube.setData(Uri.parse(film.getTrailerURL()));
            startActivity(youtube);
        });

        UserFilmRequest.get(
                FilmActivity.this,
                Session.user.getId(),
                film.getId(),
                new Request.ClientResponse<UserFilm>() {
                    @Override
                    public void onSuccess(UserFilm result) {
                        userFilm = result;
                        // setting film rating data
                        enableEdit(true);
                        progressBar.setProgress((int) result.getRating() * 10);
                        progressController.setProgress((int) result.getRating() * 10);
                        progressText.setText("" + result.getRating() + "/10");
                    }

                    @Override
                    public void onError(String error) {
                        runOnUiThread(() -> {
                            enableEdit(false);
                        });
                    }
                }, UserFilm.class);
    }

    // When its a saved film for the logged user, the he/she can rate the film
    private void enableEdit(boolean enabled) {
        progressBar.setEnabled(enabled);
        progressController.setEnabled(enabled);
        progressText.setEnabled(enabled);
        progressBar.setVisibility(enabled ? View.VISIBLE : View.GONE);
        progressController.setVisibility(enabled ? View.VISIBLE : View.GONE);
        progressText.setVisibility(enabled ? View.VISIBLE : View.GONE);
        favoriteResource.setIcon(enabled ? R.drawable.ic_favorite_red_24dp : R.drawable.ic_favorite_white_24dp);
        favoriteResource.setTitle(enabled ? R.string.favorite : R.string.unfavorite);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // The response from the form activity
            case FORM_REQUEST:
                if (data != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Plan plan = new Plan();
                    plan.setCreatorId(
                            getSharedPreferences(Session.SESSION_FILE, 0)
                                    .getLong(Session.USER, 0)
                    );
                    plan.setFilmId(film.getId());
                    plan.setTitle(data.getExtras().getString(getString(R.string.plan_title)));
                    try {
                        plan.setDate(dateFormat.parse(data.getExtras().getString(getString(R.string.plan_date))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    plan.setLocation(data.getExtras().getString(getString(R.string.plan_location)));
                    plan.setDescription(data.getExtras().getString(getString(R.string.plan_description)));
                    // Creates the new plan
                    PlanRequest.createPlan(FilmActivity.this, plan, new Request.ClientResponse<Plan>() {

                        @Override
                        public void onSuccess(Plan result) {
                            Toast.makeText(FilmActivity.this, getText(R.string.plan_created), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(FilmActivity.this, error, Toast.LENGTH_SHORT).show();
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
        favoriteResource = menu.findItem(R.id.favorite);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.favorite:
                if (userFilm != null) {
                    UserFilmRequest.delete(FilmActivity.this, Session.user.getId(), film.getId(), new Request.ClientResponse<UserFilm>() {
                        @Override
                        public void onSuccess(UserFilm result) {
                            userFilm = null;
                            enableEdit(false);
                            Toast.makeText(FilmActivity.this, getString(R.string.user_film_unfavorite), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(FilmActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }, UserFilm.class);
                } else {
                    userFilm = new UserFilm();
                    userFilm.setUserId(Session.user.getId());
                    userFilm.setFilmId(film.getId());
                    UserFilmRequest.postUserFilm(FilmActivity.this, userFilm, new Request.ClientResponse<UserFilm>() {
                        @Override
                        public void onSuccess(UserFilm result) {
                            userFilm = result;
                            enableEdit(true);
                            Toast.makeText(FilmActivity.this, getString(R.string.user_film_favourite), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(FilmActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }, UserFilm.class);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
