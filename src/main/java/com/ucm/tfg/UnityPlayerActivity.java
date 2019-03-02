package com.ucm.tfg;

import com.unity3d.player.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class UnityPlayerActivity extends Activity
{
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    // Setup activity layout
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        mUnityPlayer = new UnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }
    public void exitAR(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
    public void goPlans(){
        Intent plans = new Intent(this, PlansActivity.class);
        startActivity(plans);
    }
    public void goSaves(){
        Intent saves = new Intent(this, SavesActivity.class);
        startActivity(saves);
    }
    public void like(String film){
        Context context = getApplicationContext();
        String text = film;
        int duration = Toast.LENGTH_SHORT;
        Log.d("LIKE", text);
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        //En id está el id de la película que ha detectado con RA
        //y que le ha dado a guardar
        Intent prueba = new Intent(this, PruebaInfoPeli.class);
        prueba.putExtra("film", film);
        startActivity(prueba);
    }

    public void info(String info) {
        Log.d("INFO",info);

        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("info", info);
        startActivity(intent);
    }

    public void plan(String id){
        Log.d("PLAN",id);

        //En id está el id de la película que ha detectado con RA
        //y que le ha dado a plan
    }
    public void share(String id){
        Log.d("SHARE",id);
        //En id está el id de la película que ha detectado con RA
        //y que le ha dado a compartir
    }
    public void youtube(String info){
        Log.d("YOUTUBE",info );
        JSONObject json = null;
        try {
            json = new JSONObject(info);
            Log.d("JSSONN", json.getString("trailer"));
            Intent youtube = new Intent(this, YoutubeActivity.class);
            youtube.putExtra("url", json.getString("trailer"));
            startActivity(youtube);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    @Override protected void onNewIntent(Intent intent)
    {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override protected void onDestroy ()
    {
        mUnityPlayer.destroy();
        super.onDestroy();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override protected void onStart()
    {
        super.onStart();
        mUnityPlayer.start();
    }

    @Override protected void onStop()
    {
        super.onStop();
        mUnityPlayer.stop();
    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL)
        {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
