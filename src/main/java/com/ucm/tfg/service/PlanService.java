package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.entities.Plan;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;
import java.util.List;

public class PlanService {

    private static String url = "http://tfg-spring.herokuapp.com/plans/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/plans/";

    public static <T> void getPlans(Activity activity, Service.ClientResponse<ArrayList<Plan>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url)
                .execute(callback, new ParameterizedTypeReference<ArrayList<Plan>>(){});
    }

}
