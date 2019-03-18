package com.ucm.tfg.Integration;

import android.util.Log;
import android.widget.Toast;

import com.ucm.tfg.client.ClientResponse;
import com.ucm.tfg.client.FriendshipService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DaoFriendship {
    private boolean friends = false;

    public boolean areFriends(String friend1, String friend2){

        FriendshipService.getFriendsById(friend1, new ClientResponse<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("Friendship", result);
                Log.wtf("Friendship", result);
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for(int i = 0; i < jsonArray.length(); i++){
                        //Log.wtf("Friendship " + i, jsonArray.get(i).toString());
                        JSONObject json = jsonArray.getJSONObject(i);
                        //Log.wtf("Friendship requester " + i, json.getJSONObject("requester").get("uuid").toString());
                        //Log.wtf("Friendship friend " + i, json.getJSONObject("friend").get("uuid").toString());
                        String requester = json.getJSONObject("requester").get("uuid").toString();
                        String friend = json.getJSONObject("friend").get("uuid").toString();
                        if(requester.equalsIgnoreCase(friend1) && friend.equalsIgnoreCase(friend2) ||
                                requester.equalsIgnoreCase(friend2) && friend.equalsIgnoreCase(friend1)){
                            friends = true;
                            Log.wtf("Friendship solution " + i, "son amigos");
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        }, String.class); //5 seconds
        return friends;
    }
}
