package com.ucm.tfg.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.ucm.tfg.R;

public class YoutubeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        playVideo( extras.getString("url"));
    }
    public void playVideo(String url){
        WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadUrl(url);
    }
}
