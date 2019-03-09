package com.ucm.tfg.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import com.ucm.tfg.Integration.DaoFilm;
import com.ucm.tfg.R;
import com.ucm.tfg.entities.Film;


public class SavedFilmActivity extends AppCompatActivity {

    private static String LOGTAG = "SavedFilmActivity";

    private TextView info;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_film);
        DaoFilm daoFilm = new DaoFilm();
        Intent intent = this.getIntent();
        String uuid = intent.getStringExtra("uuid");
        Log.i(LOGTAG, uuid);
        Film film = daoFilm.getFilmById(uuid);
        this.info = (TextView)findViewById(R.id.textView);
        String text = "Saved film : " + film.getName() + " correctly!!";
        info.setText(text);
        this.button = (Button)findViewById(R.id.button);
        this.button.setOnClickListener(view -> {
            Intent intent2 = new Intent(Intent.ACTION_VIEW);
            intent2.setData(Uri.parse(film.getDescription()));
            startActivity(intent2);
        });
    }


}
