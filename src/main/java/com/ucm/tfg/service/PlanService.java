package com.ucm.tfg.service;

import android.app.Activity;

public class PlanService {

    private static String url = "http://tfg-spring.herokuapp.com/plan/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/plan/";

    public static void getPlans(Activity activity, Service.ClientResponse<String> callback) {
        Service.getInstance()
                .setContext(activity)
                .GET(PlanService.develop_url, callback, String.class);
    }

}
