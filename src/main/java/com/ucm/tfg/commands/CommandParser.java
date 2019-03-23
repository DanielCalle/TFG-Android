package com.ucm.tfg.commands;

public class CommandParser {

    private final static Command[] commands = {
            new CommandGetFilmById(),
            new CommandGetUserById(),
            new CommandAreFriends()
    };

    public static Command parse(String action) {
        for (Command command : commands) {
            if (command.action(action)) {
                return command;
            }
        }
        return null;
    }
}
