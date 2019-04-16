package com.ucm.tfg.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan implements Serializable {

    private String id;

    private String creatorUuid;

    private String filmUuid;

    private List<String> joinedUsers;

    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorUuid() {
        return creatorUuid;
    }

    public void setCreator(String creatorUuid) {
        this.creatorUuid = creatorUuid;
    }

    public String getFilmUuid() {
        return filmUuid;
    }

    public void setFilmUuid(String filmUuid) {
        this.filmUuid = filmUuid;
    }

    public List<String> getJoinedUsers() {
        return joinedUsers;
    }

    public void setJoinedUsers(List<String> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
