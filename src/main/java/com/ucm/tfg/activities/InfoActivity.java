package com.ucm.tfg.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.views.ExpandableTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InfoActivity extends AppCompatActivity {

    private final static String LOGTAG = "FilmActivity";
    private final static int FORM_REQUEST = 1;

    private ActionBar actionBar;
    private ImageView filmPoster;

    private Film film;

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

        ProgressBar progressBar = findViewById(R.id.progress_bar);
        TextView progressText = findViewById(R.id.progress_text);
        SeekBar progressController = findViewById(R.id.progress_controller);

        // setting film rating data
        progressBar.setProgress((int) film.getRating() * 10);
        progressText.setText("" + film.getRating() + "/10");
        progressController.setProgress((int) film.getRating() * 10);

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

            }
        });

        Button addToPlan = findViewById(R.id.add_to_plan);
        addToPlan.setOnClickListener((View v) -> {
            Intent intent = new Intent(InfoActivity.this, FormActivity.class);
            intent.putExtra("date", "date");
            intent.putExtra("location", "text");
            intent.putExtra("description", "text");
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FORM_REQUEST:
                if (data != null) {
                    for (String key : data.getExtras().keySet()) {
                        Log.i(key, data.getExtras().getString(key));
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Plan plan = new Plan();
                    plan.setCreator("a");
                    plan.setFilmUuid(film.getUuid());
                    try {
                        plan.setDate(dateFormat.parse(data.getExtras().getString("date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    plan.setLocation(data.getExtras().getString("location"));
                    plan.setDescription(data.getExtras().getString("description"));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
