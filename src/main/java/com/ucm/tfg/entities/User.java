package com.ucm.tfg.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private String uuid;

    private String name;

    private String email;

    private String password;

    /**
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public JSONObject getJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("uuid", uuid);
            json.put("name", name);
            json.put("email", email);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
