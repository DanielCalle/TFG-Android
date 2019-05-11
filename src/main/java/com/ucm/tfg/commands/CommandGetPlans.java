package com.ucm.tfg.commands;

import android.app.Activity;

import com.ucm.tfg.Session;
import com.ucm.tfg.service.RecommendationService;
import com.ucm.tfg.service.Service;

public class CommandGetPlans implements Command{
    private final static String action = "getPlans";

    @Override
    public String execute(Object... objects) {
        RecommendationService.getThreePlans((Activity) objects[0], Session.user.getId(), (String) objects[1], (Service.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
