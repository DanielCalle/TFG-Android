package com.ucm.tfg.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.ucm.tfg.Integration.DaoFilm;
import com.ucm.tfg.R;
import com.ucm.tfg.client.ClientResponse;
import com.ucm.tfg.client.FilmService;
import com.ucm.tfg.entities.Film;


import org.json.JSONObject;

public class SavedFilmActivity extends AppCompatActivity {

    private static String LOGTAG = "SavedFilmActivity";

    private TextView info;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_film);
        Intent intent = this.getIntent();
        String uuid = intent.getStringExtra("uuid");
        getData(uuid);
    }

    public void getData(String uuid){
        JSONObject json = null;
        try {
            json = new JSONObject(uuid);
            FilmService.getFilmById(json.getString("uuid"), new ClientResponse<Film>() {

                @Override
                public void onSuccess(Film film) {
                    String text = "Saved film : " + film.getName() + " correctly!!";
                    TextView info = (TextView) findViewById(R.id.textView);
                    Button button = (Button) findViewById(R.id.button);
                    info.setText(text);
                    button.setOnClickListener(view -> {
                        Intent intent2 = new Intent(Intent.ACTION_VIEW);
                        intent2.setData(Uri.parse(film.getDescription()));
                        startActivity(intent2);
                    });
                }

                @Override
                public void onError(String error) {
                    TextView info = (TextView) findViewById(R.id.textView);
                    info.setText(error);
                }
            }, Film.class); //5 seconds

        } catch (Exception e) {
            Log.e("Error", "Exception: " + e.getMessage());
        }

    }


}
