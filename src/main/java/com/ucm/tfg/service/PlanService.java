package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.activities.PlanActivity;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;

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

    public static <T> void createPlan(Activity activity, Plan plan, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .post()
                .url(develop_url)
                .body(plan)
                .execute(callback, c);
    }
    public static <T> void joinPlan(Activity activity, String id, String userUuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .put()
                .addPathVariable("id", id)
                .addPathVariable("userUuid", userUuid)
                .url(develop_url + "{id}" + "/join/" + "{userUuid}")
                .execute(callback, c);
    }
    public static <T> void quitPlan(Activity activity, String id, String userUuid, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .put()
                .addPathVariable("id", id)
                .addPathVariable("userUuid", userUuid)
                .url(develop_url + "{id}" + "/quit/" + "{userUuid}")
                .execute(callback, c);
    }
    public static <T> void getJoinedUsers(Activity activity, String uuid, Service.ClientResponse<ArrayList<User>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("id", uuid)
                .url(develop_url + "{id}" + "/joined-users")
                .execute(callback, new ParameterizedTypeReference<ArrayList<User>>() {
                });
    }

    public static <T> void getUsers(Activity activity, String uuid, Service.ClientResponse<ArrayList<User>> callback) {
        Service.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("id", uuid)
                .url(develop_url + "{id}" + "/users")
                .execute(callback, new ParameterizedTypeReference<ArrayList<User>>() {
                });
    }

    public static <T> void deletePlan(Activity activity, String id, Service.ClientResponse<T> callback, Class<T> c) {
        Service.getInstance()
                .setContext(activity)
                .delete()
                .addPathVariable("id", id)
                .url(develop_url + "{id}")
                .execute(callback, c);
    }

}
