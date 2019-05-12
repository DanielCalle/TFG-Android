package com.ucm.tfg.commands;

public interface Command {

    // the action for execution
    Object execute(Object... objects);

    // Determines if the command is the desired one
    boolean action(String action);

}
