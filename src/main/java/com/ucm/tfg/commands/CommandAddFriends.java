package com.ucm.tfg.commands;

import android.app.Activity;
import android.util.Log;

import com.ucm.tfg.service.FriendshipService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;

public class CommandAddFriends implements Command {

    private final static String action = "addFriend";


    @Override
    public Object execute(Object... objects) {
        Log.wtf("command addfriends", objects.toString());
        //FriendshipService.addFriend((Activity) objects[0],(String) objects[1], "1", (Service.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
