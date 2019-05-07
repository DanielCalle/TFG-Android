package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.Utils;
import com.ucm.tfg.adapters.FriendAdapter;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;
import com.ucm.tfg.views.CustomViewPager;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FriendAdapter friendAdapter;
    private SearchView searchView;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        userId = getSharedPreferences(Session.SESSION_FILE, 0).getLong(Session.USER, 0);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.action_friends);
        }

        RecyclerView recyclerView = findViewById(R.id.friends);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(FriendActivity.this));

        friendAdapter = new FriendAdapter(FriendActivity.this);
        friendAdapter.addPlanOnClickListener((User friend, FriendAdapter.RecyclerViewHolder recyclerViewHolder) -> {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                            FriendActivity.this,
                            Pair.create(recyclerViewHolder.userAvatar, "user_avatar")
                    );
            Intent i = new Intent(FriendActivity.this, UserActivity.class);
            i.putExtra("user", friend);
            startActivity(i, optionsCompat.toBundle());
        });

        recyclerView.setAdapter(friendAdapter);

        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            updateFriends();
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(FriendActivity.this);

        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        userName.setText(Session.user.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        updateFriends();
    }

    private void updateFriends() {
        swipeRefreshLayout.setRefreshing(true);
        if (userId != 0) {
            UserService.getFriends(FriendActivity.this, userId, new Service.ClientResponse<ArrayList<User>>() {
                @Override
                public void onSuccess(ArrayList<User> result) {
                    friendAdapter.setFriendData(result);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(FriendActivity.this, error, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void searchUsers(String name) {
        if (!Utils.isNullOrEmpty(name)) {
            swipeRefreshLayout.setRefreshing(true);
            UserService.searchUsersByName(FriendActivity.this, name, new Service.ClientResponse<ArrayList<User>>() {
                @Override
                public void onSuccess(ArrayList<User> result) {
                    friendAdapter.setFriendData(result);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(FriendActivity.this, error, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friends, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!Utils.isNullOrEmpty(s)) {
                    Toast.makeText(FriendActivity.this, s, Toast.LENGTH_SHORT).show();
                    searchUsers(s);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            updateFriends();
            return false;
        });

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_logout:
                SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Session.IS_LOGGED, false);
                editor.putLong(Session.USER, 0);
                editor.apply();

                startActivity(new Intent(FriendActivity.this, LoginActivity.class));
                break;
            case R.id.action_friends:
                startActivity(new Intent(FriendActivity.this, FriendActivity.class));
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
