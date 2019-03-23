package com.ucm.tfg.commands;

import android.app.Activity;

import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;

public class CommandGetUserById implements Command {

    private final static String action = "getUserById";

    @Override
    public User execute(Object... objects) {
        UserService.getUserById((Activity) objects[0], (String) objects[1], (Service.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
