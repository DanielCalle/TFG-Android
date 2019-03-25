package com.ucm.tfg.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ucm.tfg.R;
import com.ucm.tfg.entities.Plan;

public class PlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Plan plan = (Plan) getIntent().getExtras().getSerializable("plan");

        Toast.makeText(PlanActivity.this, plan.getFilm().getName(), Toast.LENGTH_SHORT).show();
    }

}
