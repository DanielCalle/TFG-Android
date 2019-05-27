package com.ucm.tfg.service;

import android.app.Activity;

import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;

import org.springframework.core.ParameterizedTypeReference;

import java.util.ArrayList;

/**
 * All http requests against plans route
 */
public class PlanRequest {

    private static String url = "http://tfg-spring.herokuapp.com/plans/";
    private static String develop_url = "http://filmar-develop.herokuapp.com/plans/";

    // Returns all plans stored in database
    public static <T> void getPlans(Activity activity, Request.ClientResponse<ArrayList<Plan>> callback) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url)
                .execute(callback, new ParameterizedTypeReference<ArrayList<Plan>>(){});
    }

    public static <T> void getPlanById(Activity activity, long id, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}")
                .addPathVariable("id", "" + id)
                .execute(callback, c);
    }

    // Saves a new plan into database
    public static <T> void createPlan(Activity activity, Plan plan, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .post()
                .url(develop_url)
                .body(plan)
                .execute(callback, c);
    }

    // given the plan id and a new user, the user joins the plan
    public static <T> void joinPlan(Activity activity, long id, long userId, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .put()
                .url(develop_url + "{id}" + "/join/" + "{userId}")
                .addPathVariable("id", "" + id)
                .addPathVariable("userId", "" + userId)
                .execute(callback, c);
    }

    // given the plan id and a new user, the user quits the plan
    public static <T> void quitPlan(Activity activity, long id, long userId, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .put()
                .url(develop_url + "{id}" + "/quit/" + "{userId}")
                .addPathVariable("id", "" + id)
                .addPathVariable("userId", "" + userId)
                .execute(callback, c);
    }

    // Given the plan id, returns all users joined
    public static <T> void getJoinedUsers(Activity activity, long id, Request.ClientResponse<ArrayList<User>> callback) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}" + "/joined-users")
                .addPathVariable("id", "" + id)
                .execute(callback, new ParameterizedTypeReference<ArrayList<User>>() {
                });
    }

    // Returns all users joined in the plan including the creator, given a plan id
    public static <T> void getUsers(Activity activity, long id, Request.ClientResponse<ArrayList<User>> callback) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .url(develop_url + "{id}" + "/users")
                .addPathVariable("id", "" + id)
                .execute(callback, new ParameterizedTypeReference<ArrayList<User>>() {
                });
    }

    // Given a plan id, removes it
    public static <T> void deletePlan(Activity activity, long id, Request.ClientResponse<T> callback, Class<T> c) {
        Request.getInstance()
                .setContext(activity)
                .delete()
                .url(develop_url + "{id}")
                .addPathVariable("id", "" + id)
                .execute(callback, c);
    }

    // Given a string, returns all plans which title contain this string
    public static <T> void searchPlansByTitle(Activity activity, String title, Request.ClientResponse<ArrayList<Plan>> callback) {
        Request.getInstance()
                .setContext(activity)
                .get()
                .addPathVariable("title", title)
                .url(develop_url + "search/{title}")
                .execute(callback, new ParameterizedTypeReference<ArrayList<Plan>>() {
                });
    }

}
