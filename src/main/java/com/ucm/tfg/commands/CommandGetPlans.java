package com.ucm.tfg.commands;

import android.app.Activity;

public class CommandGetPlans implements Command{
    private final static String action = "getPlans";

    @Override
    public String execute(Object... objects) {
       String plan = "{\"id\":1,\"user\":\"" +
               "{\"uuid\":\"5df9b1ab2e9742aa9bfd4a7d12dde033\",\"name\":\"Donald Trump0\",\"email\":\"dtrump@eeuuisgreat.com\",\"password\":\"1234\",\"imageURL\":\"https://drive.google.com/uc?export=download&id=1oKxiawgsZ4HVpMngdLmePqSRYllNJ1bv\"}" +
               "\",\"film\":{\"uuid\":\"28f361ae08014c4bb94e8a8d59b91130\",\"name\":\"El Rey Leon\",\"director\":\"Roger Allers\",\"trailerURL\":\"https://www.youtube.com/watch?v=xB5ceAruYrI\",\"infoURL\":\"https://www.imdb.com/title/tt0110357/\",\"imageURL\":\"https://drive.google.com/uc?export=download&id=1WqyyVvg56pSeCyC0KAtjZLmmekZ0hyi9\",\"genre\":\"Animation\",\"duration\":88,\"rating\":8.0,\"country\":\"USA\"}," +
               "\"date\":\"2019-12-03\"," +
               "\"joinedUsers\":[{\"uuid\":\"1\",\"name\":\"Pepe\",\"email\":\"pepe@gmail.com\",\"password\":\"1234\",\"imageURL\":null},{\"uuid\":\"2\",\"name\":\"Juan\",\"email\":\"juan@gmail.com\",\"password\":\"1234\",\"imageURL\":null},{\"uuid\":\"a\",\"name\":\"a\",\"email\":\"a\",\"password\":\"a\",\"imageURL\":null}]}";
       return plan;
    }

    @Override
    public boolean action(String action) {
        return this.action.equalsIgnoreCase(action);
    }
}
