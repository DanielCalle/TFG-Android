package com.ucm.tfg.commands;

public class CommandParser {

    // All commands available are listed below
    private final static Command[] commands = {
            new CommandGetFilmById(),
            new CommandGetUserById(),
            new CommandAreFriends(),
            new CommandAddFriends(),
            new CommandGetPlans(),
            new CommandShowInfoFilm()
    };

    // Iterate through all commands and finds out the wanted command and returns it
    public static Command parse(String action) {
        for (Command command : commands) {
            if (command.action(action)) {
                return command;
            }
        }
        return null;
    }
}
