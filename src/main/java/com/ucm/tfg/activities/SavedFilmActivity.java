package com.ucm.tfg.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.entities.UserFilm;
import com.ucm.tfg.service.FilmRequest;
import com.ucm.tfg.service.Request;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.service.UserFilmRequest;


import org.json.JSONObject;

import java.util.Date;

/**
 * When in unity proyect has clicked save film
 */
public class SavedFilmActivity extends AppCompatActivity {

    private static String LOGTAG = "SavedFilmActivity";

    private TextView info;
    private Button button;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_film);
        Intent intent = this.getIntent();
        String uuid = intent.getStringExtra("uuid");
        Log.i(LOGTAG, uuid);
        getData(uuid);
    }

    public void getData(String uuid){
        JSONObject json = null;
        try {
            json = new JSONObject(uuid);
            FilmRequest.getFilmById(SavedFilmActivity.this, json.getLong("id"), new Request.ClientResponse<Film>() {

                @Override
                public void onSuccess(Film film) {
                    String text = "Saved film : " + film.getName() + " correctly!!";

                    TextView info = (TextView) findViewById(R.id.textView);
                    info.setText(text);

                    ImageView image = (ImageView) findViewById(R.id.image);
                    /* To display an image represented by byte[], it converts it to a valid ImageView*/
                    Picasso.get().load(film.getImageURL()).into(image);

                    UserFilm userFilm = new UserFilm();
                    userFilm.setUserId(getSharedPreferences(Session.SESSION_FILE, 0).getLong(Session.USER, 0));
                    userFilm.setFilmId(film.getId());
                    userFilm.setDate(new Date());
                    UserFilmRequest.postUserFilm(SavedFilmActivity.this, userFilm, new Request.ClientResponse<UserFilm>(){
                        @Override
                        public void onSuccess(UserFilm userFilm) {
                            Button button = (Button) findViewById(R.id.button);
                            /*button.setOnClickListener(view -> {
                                Intent intentInfo = new Intent(Intent.ACTION_VIEW);
                                intentInfo.setData(Uri.parse(film.getInfoURL()));
                                startActivity(intentInfo);
                            });*/
                        }
                        @Override
                        public void onError(String error) {
                            TextView info = (TextView) findViewById(R.id.textView);
                            info.setText(error);
                        }
                    }, UserFilm.class);

                }

                @Override
                public void onError(String error) {
                    TextView info = (TextView) findViewById(R.id.textView);
                    info.setText(error);
                }
            }, Film.class);

        } catch (Exception e) {
            Log.e("Error", "Exception: " + e.getMessage());
        }

    }


}
