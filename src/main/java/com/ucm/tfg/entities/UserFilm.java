package com.ucm.tfg.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFilm {

    private String userUuid;
    private String filmUuid;

    public UserFilm(String userUuid, String filmUuid) {
        this.userUuid = userUuid;
        this.filmUuid = filmUuid;
    }

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
}
