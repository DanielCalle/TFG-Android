package com.ucm.tfg.commands;

import android.app.Activity;
import android.util.Log;

import com.ucm.tfg.entities.Friendship;
import com.ucm.tfg.service.FriendshipService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandAddFriends implements Command {

    private final static String action = "addFriend";


    @Override
    public Object execute(Object... objects) {
        Log.wtf("command addfriends", objects.toString());
        Friendship tfriendship = new Friendship();
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Date date = new Date();
        Log.wtf("command addfriends",sdf.format(date) );
        tfriendship.setDate(sdf.format(date));
        tfriendship.setRequesterUuid("1");
        //tfriendship.setFriendUuid((String) objects[1]);
        tfriendship.setFriendUuid("2");
        FriendshipService.addFriend((Activity) objects[0], tfriendship, (Service.ClientResponse) objects[2], (Class) objects[3]);
        //FriendshipService.addFriend((Activity) objects[0],(String) objects[1], "1", (Service.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
