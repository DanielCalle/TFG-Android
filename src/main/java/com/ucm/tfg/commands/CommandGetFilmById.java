package com.ucm.tfg.commands;

import android.app.Activity;
import android.util.Log;

import com.ucm.tfg.entities.Film;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.Service;
import com.unity3d.player.UnityPlayer;

public class CommandGetFilmById implements Command {

    private final static String action = "getFilmById";

    @Override
    public Film execute(Object... objects) {
        FilmService.getFilmById((Activity) objects[0], (long) objects[1], (Service.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
