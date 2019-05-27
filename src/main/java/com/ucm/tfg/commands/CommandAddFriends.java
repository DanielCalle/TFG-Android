package com.ucm.tfg.commands;

import android.app.Activity;

import com.ucm.tfg.entities.Friendship;
import com.ucm.tfg.requests.FriendshipRequest;
import com.ucm.tfg.requests.Request;

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
        FriendshipRequest.request((Activity) objects[0], tfriendship.getRequesterId(), tfriendship.getFriendId(), (Request.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
