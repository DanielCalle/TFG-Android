package com.ucm.tfg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class YoutubeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        playVideo("");
    }
    public void playVideo(String url){
        WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadUrl("https://www.youtube.com/watch?v=_ujGv5dhGfk&t=1s");
    }
}
