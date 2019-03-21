package com.ucm.tfg.entities;

import java.util.Date;
import java.util.List;

public class Plan {

    private Long id;

    private User creator;

    private Film film;

    private List<User> joinedUsers;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public List<User> getJoinedUsers() {
        return joinedUsers;
    }

    public void setJoinedUsers(List<User> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
