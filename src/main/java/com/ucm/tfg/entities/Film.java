package com.ucm.tfg.entities;
import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {

    private String uuid;

    private String name;

    private String director;

    private String trailer;

    private String infoURL;

    private byte[] image;

    private String genre;

    private int duration;

    private int valoration;

    private String country;

    public Film() {
    }


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
     * @return the director
     */
    public String getdirector() {
        return director;
    }

    /**
     * @param director the director to set
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * @return the trailer
     */
    public String getTrailer() {
        return trailer;
    }

    /**
     * @param trailer the trailer to set
     */
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    /**
     * @return the infoURL
     */
    public String getInfoURL() {
        return infoURL;
    }

    /**
     * @param infoURL the description to set
     */
    public void setInfoURL(String infoURL) {
        this.infoURL = infoURL;
    }

    /**
     * @return the image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * @param image the description to set
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the valoration
     */
    public int getValoration() {
        return valoration;
    }

    /**
     * @param valoration the valoration to set
     */
    public void setValoration(int valoration) {
        this.valoration = valoration;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }


    public JSONObject getJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("uuid", uuid);
            json.put("name", name);
            json.put("idDirector", director);
            json.put("trailer", trailer);
            json.put("description", infoURL);
            json.put("genre", genre);
            json.put("duration", duration);
            json.put("valoration", valoration);
            json.put("country", country);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
