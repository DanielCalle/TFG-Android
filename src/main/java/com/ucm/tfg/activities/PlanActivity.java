package com.ucm.tfg.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.adapters.PlanJoinedUsersAdapter;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;

public class PlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Plan plan = (Plan) getIntent().getExtras().getSerializable("plan");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setTitle(plan.getFilm().getName());
        }

        ImageView filmPoster = findViewById(R.id.film_poster);
        /*Picasso.get()
                .load(plan
                        .getFilm()
                        .getImageURL()
                )
                .into(filmPoster);*/

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.joined);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PlanActivity.this));

        PlanJoinedUsersAdapter planJoinedUsersAdapter = new PlanJoinedUsersAdapter(PlanActivity.this);
        planJoinedUsersAdapter.addPlanOnClickListener((User u) -> {
            Toast.makeText(PlanActivity.this, u.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(planJoinedUsersAdapter);
        planJoinedUsersAdapter.setData(plan.getJoinedUsers());

        FloatingActionButton floatingActionButton = findViewById(R.id.film_info);
        floatingActionButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
           // intent.setData(Uri.parse(plan.getFilm().getInfoURL()));
            startActivity(intent);
        });

        //Toast.makeText(PlanActivity.this, plan.getFilm().getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
