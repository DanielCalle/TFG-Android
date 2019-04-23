package com.ucm.tfg.entities;

import java.io.Serializable;

public class RatingFilm implements Serializable {

    private String userUuid;
    private String filmUuid;
    private float rating;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getFilmUuid() {
        return filmUuid;
    }

    public void setFilmUuid(String filmUuid) {
        this.filmUuid = filmUuid;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
