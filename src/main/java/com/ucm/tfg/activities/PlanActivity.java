package com.ucm.tfg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.Utils;
import com.ucm.tfg.adapters.PlanUserAdapter;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;

import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class PlanActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ImageView filmPoster;
    private FloatingActionButton floatingActionButton;
    private Plan plan;
    private PlanUserAdapter planUserAdapter;
    private Button joinPlan;
    private boolean isCreator;
    private boolean joined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        plan = (Plan) getIntent().getExtras().getSerializable("plan");

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
        joinPlan = findViewById(R.id.join_plan);
        isCreator = plan.getCreatorId() == Session.user.getId();
        joinPlan.setEnabled(!isCreator);
        joinPlan.setVisibility(isCreator ? View.GONE : View.VISIBLE);

        planUserAdapter = new PlanUserAdapter(PlanActivity.this);
        users.setHasFixedSize(true);
        users.setLayoutManager(new LinearLayoutManager(PlanActivity.this, LinearLayoutManager.HORIZONTAL, false));
        users.setAdapter(planUserAdapter);
        planUserAdapter.addUserOnClickListener((User u, PlanUserAdapter.RecyclerViewHolder recyclerViewHolder) -> {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                            PlanActivity.this,
                            Pair.create(recyclerViewHolder.userImage, "user_avatar")
                    );
            Intent i = new Intent(PlanActivity.this, UserActivity.class);
            i.putExtra("user", u);
            startActivity(i, optionsCompat.toBundle());
        });

        date.setText(Utils.dateFormat(plan.getDate()));
        time.setText(Utils.timeFormat(plan.getDate()));
        location.setText(plan.getLocation());
        description.setText(plan.getDescription());

        floatingActionButton = findViewById(R.id.film_info);

        FilmService.getFilmById(PlanActivity.this, plan.getFilmId(), new Service.ClientResponse<Film>() {
            @Override
            public void onSuccess(Film result) {
                if (actionBar != null) {
                    actionBar.setTitle(result.getName());
                }

                Picasso.get()
                        .load(result.getImageURL())
                        .into(filmPoster);

                floatingActionButton.setOnClickListener((View v) -> {
                    Intent intent = new Intent(PlanActivity.this, FilmActivity.class);
                    intent.putExtra("film", result);
                    startActivity(intent);
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PlanActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        }, Film.class);

        joinPlan.setOnClickListener((View v) -> {
            if (!joined) {
                PlanService.joinPlan(PlanActivity.this, plan.getId(), Session.user.getId(), new Service.ClientResponse<Plan>() {

                    @Override
                    public void onSuccess(Plan result) {
                        updateJoinedUsers();
                        Toast.makeText(PlanActivity.this, getText(R.string.plan_joined), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(PlanActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }, Plan.class);
            } else {
                PlanService.quitPlan(PlanActivity.this, plan.getId(), Session.user.getId(), new Service.ClientResponse<Plan>() {

                    @Override
                    public void onSuccess(Plan result) {
                        updateJoinedUsers();
                        Toast.makeText(PlanActivity.this, getText(R.string.plan_quit), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(PlanActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }, Plan.class);
            }
        });

        updateJoinedUsers();
    }

    private void updateJoinedUsers() {
        PlanService.getUsers(this, plan.getId(), new Service.ClientResponse<ArrayList<User>>() {

            @Override
            public void onSuccess(ArrayList<User> result) {
                planUserAdapter.setData(result);
                if (!isCreator) {
                    joined = false;
                    for (int i = 0; i < result.size() && !joined; i++) {
                        joined = result.get(i).getId() == Session.user.getId();
                    }
                    joinPlan.setText(joined ? R.string.quit_plan : R.string.join_plan);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PlanActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plan, menu);
        MenuItem deleteResource = menu.findItem(R.id.delete);
        deleteResource.setEnabled(isCreator);
        deleteResource.setVisible(isCreator);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:
                PlanService.deletePlan(PlanActivity.this, plan.getId(), new Service.ClientResponse<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(PlanActivity.this, getString(R.string.plan_deleted), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(PlanActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }, String.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
