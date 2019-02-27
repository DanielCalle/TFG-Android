package com.ucm.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {

    private static String LOGTAG = "InfoActivity";

    private ImageView poster;

    private TextView valoration;
    private TextView title;
    private TextView director;
    private TextView duration;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = this.getIntent();
        String info = intent.getStringExtra("info");
        Log.i(LOGTAG, info);

        poster = (ImageView)findViewById(R.id.poster);

        valoration = (TextView)findViewById(R.id.valoration);
        title = (TextView)findViewById(R.id.title);
        director = (TextView)findViewById(R.id.director);
        duration = (TextView)findViewById(R.id.duration);
        description = (TextView)findViewById(R.id.description);

        try {
            JSONObject json = new JSONObject(info);
            fillData(json);
        } catch (JSONException e) {
            Log.e(LOGTAG, "Error at parsing");
        }
    }

    private void fillData(JSONObject json) throws JSONException {
        valoration.setText(!json.isNull("valoration") ? "" + json.getInt("valoration") : "");
        title.setText(!json.isNull("name") ? json.getString("name") : "");
        director.setText(!json.isNull("idDirector") ? "" + json.getInt("idDirector") : "");
        duration.setText(!json.isNull("duration") ? "" + json.getInt("duration") : "");
        description.setText(!json.isNull("description") ? json.getString("description") : "");
    }

}
