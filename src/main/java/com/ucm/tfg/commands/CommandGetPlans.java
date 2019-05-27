package com.ucm.tfg.commands;

import android.app.Activity;

import com.ucm.tfg.service.RecommendationRequest;
import com.ucm.tfg.service.Request;

public class CommandGetPlans implements Command{
    private final static String action = "getPlans";

    @Override
    public String execute(Object... objects) {
        RecommendationRequest.getThreePlans((Activity) objects[0], (Long) objects[4], (String) objects[1], (Request.ClientResponse) objects[2], (Class) objects[3]);
        return null;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
