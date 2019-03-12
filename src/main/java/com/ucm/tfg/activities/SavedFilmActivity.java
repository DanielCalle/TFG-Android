package com.ucm.tfg.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import com.ucm.tfg.Integration.DaoFilm;
import com.ucm.tfg.R;
import com.ucm.tfg.entities.Film;


import org.json.JSONObject;

public class SavedFilmActivity extends AppCompatActivity {

    private static String LOGTAG = "SavedFilmActivity";

    private TextView info;
    private Button button;
    private DaoFilm daoFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_film);
        Intent intent = this.getIntent();
        String uuid = intent.getStringExtra("uuid");
        getData(uuid);
    }

    public void getData(String uuid){

        class HiloEnSegundoPlano extends AsyncTask <String, Void, Film> {

            public HiloEnSegundoPlano() {
                // Es el constructor
            }

            @Override
            protected Film doInBackground(String... uuid) {
                // Aquí iria el código que quieres ejecutar en segundo plano.
                JSONObject json = null;
                Film filmSaved = new Film();
                try {
                    json = new JSONObject(uuid[0]);
                    DaoFilm daoFilm = new DaoFilm();
                    filmSaved = daoFilm.getFilmById(json.getString("uuid"));

                } catch (Exception e) {
                    Log.e("Error", "Exception: " + e.getMessage());
                }
                return filmSaved;
            }


            @Override
            protected void onPostExecute(Film film) {
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

        }

        HiloEnSegundoPlano hilo = new HiloEnSegundoPlano();
        hilo.execute(uuid);

    }


}
