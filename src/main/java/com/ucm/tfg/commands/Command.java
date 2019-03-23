package com.ucm.tfg.commands;

public interface Command {

    Object execute(Object... objects);

    boolean action(String action);

}
