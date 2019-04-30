package com.ucm.tfg.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan implements Serializable {

    private String id;

    private String title;

    private String creatorUuid;

    private String filmUuid;

    private List<String> joinedUsers;

    private Date date;

    private String location;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
