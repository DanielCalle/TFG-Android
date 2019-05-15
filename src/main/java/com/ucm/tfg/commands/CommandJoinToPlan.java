package com.ucm.tfg.commands;

import android.app.Activity;
import android.content.Intent;

import com.ucm.tfg.activities.FilmActivity;
import com.ucm.tfg.activities.PlanActivity;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;

public class CommandJoinToPlan implements Command {

    private final static String action = "joinPlan";


    @Override
    public Object execute(Object... objects) {
        PlanService.getPlanById((Activity)objects[0], Long.parseLong((String) objects[1]), new Service.ClientResponse<Plan>(){
            @Override
            public void onSuccess(Plan result) {
                Intent i = new Intent((Activity)objects[0], PlanActivity.class);
                i.putExtra("plan", result);
                ((Activity)objects[0]).startActivity(i);
            }

            @Override
            public void onError(String error) {

            }
        }, Plan.class);
        return null;


    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
