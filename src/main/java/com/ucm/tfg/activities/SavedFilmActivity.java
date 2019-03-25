package com.ucm.tfg.activities;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Utils;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.entities.UserFilm;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.service.UserFilmService;
import com.ucm.tfg.service.UserService;


import org.json.JSONObject;

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
            FilmService.getFilmById(SavedFilmActivity.this, json.getString("uuid"), new Service.ClientResponse<Film>() {

                @Override
                public void onSuccess(Film film) {
                    String text = "Saved film : " + film.getName() + " correctly!!";

                    TextView info = (TextView) findViewById(R.id.textView);
                    info.setText(text);

                    ImageView image = (ImageView) findViewById(R.id.image);
                    /* To display an image represented by byte[], it converts it to a valid ImageView*/
                    Picasso.get().load(film.getImageURL()).into(image);

                    UserFilm userFilm = new UserFilm();
                    userFilm.setUserUuid("1");
                    userFilm.setFilmUuid(film.getUuid());
                    UserFilmService.postUserFilm(SavedFilmActivity.this, userFilm, new Service.ClientResponse<UserFilm>(){
                        @Override
                        public void onSuccess(UserFilm userFilm) {
                            Button button = (Button) findViewById(R.id.button);
                            button.setOnClickListener(view -> {
                                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                                intent2.setData(Uri.parse(film.getInfoURL()));
                                startActivity(intent2);
                            });
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
