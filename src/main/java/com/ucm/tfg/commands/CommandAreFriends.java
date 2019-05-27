package com.ucm.tfg.commands;

import android.app.Activity;
import android.util.Log;

import com.ucm.tfg.requests.FriendshipRequest;
import com.ucm.tfg.requests.Request;

public class CommandAreFriends implements Command {

    private final static String action = "areFriends";

    @Override
    public Object execute(Object... objects) {
        Log.wtf("command friendship objects[1]", (String) objects[1]);
        //Log.wtf("Session.user.getId()", "" + Session.user.getId());

        FriendshipRequest.areFriends((Activity) objects[0], (Long) objects[4], Long.parseLong((String) objects[1]), (Request.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
