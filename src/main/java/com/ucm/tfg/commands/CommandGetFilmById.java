package com.ucm.tfg.commands;

import android.app.Activity;

import com.ucm.tfg.entities.Film;
import com.ucm.tfg.requests.FilmRequest;
import com.ucm.tfg.requests.Request;

public class CommandGetFilmById implements Command {

    private final static String action = "getFilmById";

    @Override
    public Film execute(Object... objects) {
        FilmRequest.getFilmByUuid((Activity) objects[0], (String) objects[1], (Request.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
