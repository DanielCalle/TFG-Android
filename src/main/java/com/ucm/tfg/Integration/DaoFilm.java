package com.ucm.tfg.Integration;

import android.util.Log;
import android.widget.Toast;

import com.ucm.tfg.entities.Film;

import org.springframework.web.client.RestTemplate;

public class DaoFilm {

    public Film getFilmById(String uuid){
        RestTemplate restTemplate = new RestTemplate();
        Film film = restTemplate.getForObject("http://tfg-spring.herokuapp.com/film/a49581c363b94409badf6bafb4bd15d0", Film.class);
        Log.d("Spring", film.toString());
        /*Toast toast = Toast.makeText(this, film.getName().toString(), Toast.LENGTH_SHORT);
        toast.show();*/
        return film;
    }
}
