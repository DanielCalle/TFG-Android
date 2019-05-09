package com.ucm.tfg.commands;

import android.app.Activity;
import android.util.Log;

import com.ucm.tfg.service.FriendshipService;
import com.ucm.tfg.service.Service;

public class CommandAreFriends implements Command {

    private final static String action = "areFriends";

    @Override
    public Object execute(Object... objects) {
        Log.wtf("command friendship objects[1]", (String) objects[1]);
        FriendshipService.areFriends((Activity) objects[0], 1, (long) objects[1], (Service.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
