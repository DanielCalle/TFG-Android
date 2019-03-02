package com.ucm.tfg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ucm.tfg.R;

public class PlanListItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plans_list_item);

        TextView title = (TextView) findViewById(R.id.title);
        TextView friends = (TextView) findViewById(R.id.friends);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null) {
            title.setText(b.getString("TITLE"));
            friends.setText(b.getString("FRIENDS"));
        }
    }

}
