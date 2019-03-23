package com.ucm.tfg.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ucm.tfg.commands.CommandParser;
import com.ucm.tfg.service.FriendshipService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.R;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.UserService;
import com.unity3d.player.UnityPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UnityPlayerActivity extends Activity {
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unity_player);

        mUnityPlayer = new UnityPlayer(this);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.unity_player);
        frameLayout.addView(mUnityPlayer, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mUnityPlayer.requestFocus();

        ImageButton imageButton = (ImageButton) findViewById(R.id.back);
        imageButton.bringToFront();
        imageButton.setOnClickListener(view -> {
            quit();
        });
    }

    private void quit() {
        mUnityPlayer.quit();
        finish();
    }


    public void info(String info) {
        /*
        DaoFriendship daoFriendship = new DaoFriendship();
        if(daoFriendship.areFriends("5df9b1ab2e9742aa9bfd4a7d12dde033", "1")){
            Log.wtf("Friendship", "true");
        }
        else{
            Log.wtf("Friendship", "false");
        }
        */
        /*
            FriendshipService.getFriendsById("5df9b1ab2e9742aa9bfd4a7d12dde033", new ClientResponse<String>() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            }, String.class); //5 seconds
            */
        JSONObject json = null;
        try {
            json = new JSONObject(info);
            FilmService.getFilmById(this, json.getString("uuid"), new Service.ClientResponse<Film>() {

                @Override
                public void onSuccess(Film film) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(film.getInfoURL()));
                    startActivity(intent);
                }

                @Override
                public void onError(String error) {
                    Log.e("Error", "Exception: " + error);
                }
            }, Film.class); //5 seconds

        } catch (Exception e) {
            Log.e("Error", "Exception: " + e.getMessage());
        }
    }

    public void plan(String id) {
        Log.d("PLAN", id);

        //En id está el id de la película que ha detectado con RA
        //y que le ha dado a plan
    }

    public void share(String id) {
        Log.d("SHARE", id);
        //En id está el id de la película que ha detectado con RA
        //y que le ha dado a compartir
    }

    public void youtube(String info) {
        Log.d("YOUTUBE", info);
        JSONObject json = null;
        try {
            json = new JSONObject(info);
            Log.d("JSSONN", json.getString("trailer"));
            Intent youtube = new Intent(Intent.ACTION_VIEW);
            youtube.setData(Uri.parse(json.getString("trailer")));
            startActivity(youtube);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void DAOController(String action, String info) {
        if (!action.equalsIgnoreCase("areFriends")) {
            CommandParser.parse(action).execute(this, info, new Service.ClientResponse<String>() {
                @Override
                public void onSuccess(String result) {
                    if (!result.equalsIgnoreCase("null")) {
                        switch (action) {
                            case "getFilmById":
                                UnityPlayer.UnitySendMessage("CloudRecognition", "recibeInfoFilm", result);
                                break;
                            case "getUserById":
                                UnityPlayer.UnitySendMessage("CloudRecognition", "recibeInfoUser", result);
                                break;
                            case "areFriends":
                                break;
                        }
                    }
                }

                @Override
                public void onError(String error) {

                }
            }, String.class);
        } else {
            UnityPlayer.UnitySendMessage("CloudRecognition", "recibeInfoFriends", "true");
        }
    }

    public String getCurrentUser() {
        return "1";
    }

    public void save(String uuid) {
        Intent intent = new Intent(this, SavedFilmActivity.class);
        intent.putExtra("uuid", uuid);
        startActivity(intent);
    }

    public void areFriends(String info) {
        Log.wtf("friends unity", info);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // To support deep linking, we need to make sure that the service can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override
    protected void onDestroy() {
        mUnityPlayer.destroy();
        super.onDestroy();
    }

    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUnityPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUnityPlayer.stop();
    }

    // Low Memory Unity
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            quit();
        }
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }
}