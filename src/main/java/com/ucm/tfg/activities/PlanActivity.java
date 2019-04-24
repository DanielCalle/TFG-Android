package com.ucm.tfg.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
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
import com.ucm.tfg.Utils;
import com.ucm.tfg.adapters.PlanJoinedUsersAdapter;
import com.ucm.tfg.adapters.PlanUserAdapter;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ImageView filmPoster;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Plan plan = (Plan) getIntent().getExtras().getSerializable("plan");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("");
        }

        filmPoster = findViewById(R.id.film_poster);
        TextView date = findViewById(R.id.date);
        TextView time = findViewById(R.id.time);
        TextView location = findViewById(R.id.location);
        TextView description = findViewById(R.id.description);
        RecyclerView users = findViewById(R.id.users);

        date.setText(Utils.dateFormat(plan.getDate()));
        time.setText(Utils.timeFormat(plan.getDate()));
        location.setText(plan.getLocation());
        description.setText(plan.getDescription());
        ArrayList<User> usersList = new ArrayList<>();

        PlanService.getUsers(this, plan.getId(), new Service.ClientResponse<ArrayList<User>>(){

            @Override
            public void onSuccess(ArrayList<User> result) {
                PlanUserAdapter planUserAdapter = new PlanUserAdapter(PlanActivity.this);
                RecyclerView users = PlanActivity.this.findViewById(R.id.users);
                users.setHasFixedSize(true);
                users.setLayoutManager(new LinearLayoutManager(PlanActivity.this, LinearLayoutManager.HORIZONTAL, false));
                users.setAdapter(planUserAdapter);
                planUserAdapter.setData(result);
            }

            @Override
            public void onError(String error) {

            }
        });


       /*

        planJoinedUsersAdapter = new PlanJoinedUsersAdapter(PlanActivity.this);
        planJoinedUsersAdapter.addPlanOnClickListener((User u) -> {
            Toast.makeText(PlanActivity.this, u.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(planJoinedUsersAdapter);
        */
        floatingActionButton = findViewById(R.id.film_info);

        FilmService.getFilmById(PlanActivity.this, plan.getFilmUuid(), new Service.ClientResponse<Film>() {
            @Override
            public void onSuccess(Film result) {
                if (actionBar != null) {
                    actionBar.setTitle(result.getName());
                }

                Picasso.get()
                        .load(result.getImageURL())
                        .into(filmPoster);

                floatingActionButton.setOnClickListener((View v) -> {
                    Intent intent = new Intent(PlanActivity.this, InfoActivity.class);
                    intent.putExtra("film", result);
                    startActivity(intent);
                });
            }

            @Override
            public void onError(String error) {

            }
        }, Film.class);
        /*
        PlanService.getJoinedUsers(PlanActivity.this, plan.getId(), new Service.ClientResponse<ArrayList<User>>() {

            @Override
            public void onSuccess(ArrayList<User> result) {
                planJoinedUsersAdapter.setData(result);
            }

            @Override
            public void onError(String error) {

            }
        });
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
