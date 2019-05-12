package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.Utils;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.RecommendationService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The first activity when the app opens, if shows a 5 sec advertisement then redirects to main activity
 */
public class StartActivity extends AppCompatActivity {

    private final static int MAX_TIME = 5000;
    private final static int INTERVAL = 1; // 1 sec
    private int time;
    private ProgressBar timerProgress;
    private TextView timerLabel;
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // This writing logged data is for a problem caused between main activity and its fragments
        SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
        boolean isLogged = sharedPreferences.getBoolean(Session.IS_LOGGED, false);
        if (isLogged) {
            long userId = sharedPreferences.getLong(Session.USER, 0);
            UserService.getUserById(StartActivity.this, userId, new Service.ClientResponse<User>() {
                @Override
                public void onSuccess(User result) {
                    Session.user = result;
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(StartActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }, User.class);
        }

        poster = findViewById(R.id.poster);
        RelativeLayout relativeLayout = findViewById(R.id.timer);
        relativeLayout.bringToFront();
        timerProgress = findViewById(R.id.timer_progress);
        timerLabel = findViewById(R.id.timer_label);

        RecommendationService.getRandomFilm(StartActivity.this, new Service.ClientResponse<Film>() {
            @Override
            public void onSuccess(Film result) {
                Picasso.get()
                        .load(result.getImageURL())
                        .fit()
                        .into(poster);

                time = 0;
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        time += INTERVAL;
                        if (time < MAX_TIME){
                            runOnUiThread(() -> {
                                timerProgress.setProgress(time * 100 / MAX_TIME);
                                timerLabel.setText(String.format(Locale.getDefault(), "%d", ((MAX_TIME / 1000) - (time * (MAX_TIME / 1000) / MAX_TIME))));
                            });
                        } else {
                            timer.cancel();
                            startActivity(new Intent(StartActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                }, INTERVAL, INTERVAL);
            }

            @Override
            public void onError(String error) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                Toast.makeText(StartActivity.this, error, Toast.LENGTH_SHORT).show();
                finish();
            }
        }, Film.class);
    }
}
