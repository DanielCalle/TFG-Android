package com.ucm.tfg.commands;

public class CommandJoinToPlan implements Command {

    private final static String action = "joinPlan";


    @Override
    public Object execute(Object... objects) {
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
