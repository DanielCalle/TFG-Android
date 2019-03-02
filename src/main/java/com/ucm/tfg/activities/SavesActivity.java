package com.ucm.tfg.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ucm.tfg.R;

public class SavesActivity extends AppCompatActivity {

    ListView list;
    String [][] data = {{"My list"}};

    int[] dataImg = {R.drawable.moana_poster, R.drawable.deadpool_poster};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves);


    }

    public void lanzaAr(View view){
        Intent ar = new Intent(this, UnityPlayerActivity.class);
        startActivity(ar);
    }

    public void goPlans(View view){
        Intent plans = new Intent(this, PlansActivity.class);
        startActivity(plans);
    }
}
