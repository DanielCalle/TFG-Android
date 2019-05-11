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
        Friendship tfriendship = new Friendship();
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        Date date = new Date();
        tfriendship.setDate(sdf.format(date));
        tfriendship.setRequesterId((Long) objects[4]);
        tfriendship.setFriendId(Long.parseLong(objects[1].toString()));
        FriendshipService.request((Activity) objects[0], tfriendship.getRequesterId(), tfriendship.getFriendId(), (Service.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
