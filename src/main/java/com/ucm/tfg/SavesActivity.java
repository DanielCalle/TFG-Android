package com.ucm.tfg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SavesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves);
    }

    public void lanzaAr(View view){
        Intent ar = new Intent(this, UnityPlayerActivity.class);
        startActivity(ar);
    }

    public void goPlans(){
        Intent plans = new Intent(this, PlansActivity.class);
        startActivity(plans);
    }
}
