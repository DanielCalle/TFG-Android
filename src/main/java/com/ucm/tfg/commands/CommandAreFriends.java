package com.ucm.tfg.commands;

import com.ucm.tfg.service.Service;

public class CommandAreFriends implements Command {

    private final static String action = "areFriends";

    @Override
    public Object execute(Object... objects) {
        ((Service.ClientResponse) objects[2]).onSuccess("false");
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
