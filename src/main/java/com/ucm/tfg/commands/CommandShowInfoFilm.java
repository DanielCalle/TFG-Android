package com.ucm.tfg.commands;

import android.app.Activity;
import android.content.Intent;

import com.ucm.tfg.activities.FilmActivity;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.requests.FilmRequest;
import com.ucm.tfg.requests.Request;

public class CommandShowInfoFilm implements Command {

    private final static String action = "infoFilm";


    @Override
    public Object execute(Object... objects) {
        FilmRequest.getFilmById((Activity)objects[0], Long.parseLong((String) objects[1]), new Request.ClientResponse<Film>() {
            @Override
            public void onSuccess(Film result) {
                Intent i = new Intent((Activity)objects[0], FilmActivity.class);
                i.putExtra("film", result);
                ((Activity)objects[0]).startActivity(i);
            }

            @Override
            public void onError(String error) {

            }
        }, Film.class);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
