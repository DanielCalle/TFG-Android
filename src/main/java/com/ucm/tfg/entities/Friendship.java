package com.ucm.tfg.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Friendship implements Serializable {

    private String requesterUuid;
    private String friendUuid;
    private String date;
    public String getRequesterUuid() {
        return requesterUuid;
    }
    public void setRequesterUuid(String requesterUuid) {
        this.requesterUuid = requesterUuid;
    }
    public String getFriendUuid() {
        return friendUuid;
    }
    public void setFriendUuid(String friendUuid) {
        this.friendUuid = friendUuid;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
