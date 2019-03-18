package com.ucm.tfg.integration;

import android.util.Log;

import com.ucm.tfg.entities.User;

import org.springframework.web.client.RestTemplate;

public class DaoUser {

    public User getUserById(String uuid){
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject("http://tfg-spring.herokuapp.com/user/"+ uuid, User.class);
        Log.d("Spring", user.toString());
        /*Toast toast = Toast.makeText(this, film.getName().toString(), Toast.LENGTH_SHORT);
        toast.show();*/
        return user;
    }

}
