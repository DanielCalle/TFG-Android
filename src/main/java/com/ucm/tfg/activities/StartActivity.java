package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.Utils;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    private final static int MAX_TIME = 5000;
    private final static int INTERVAL = 1; // 1 sec
    private int time;
    private ProgressBar timerProgress;
    private TextView timerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        timerProgress = findViewById(R.id.timer);
        timerLabel = findViewById(R.id.timer_label);

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
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, INTERVAL, INTERVAL);
    }
}
