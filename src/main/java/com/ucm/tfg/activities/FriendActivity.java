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

/**
 * Shows a list of friends for the logged user
 */
public class FriendActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FriendAdapter friendAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

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
            // this is used for an animation effect when changing interfaces
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

        // Swipe down action -> refresh data
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            updateFriends();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateFriends();
    }

    private void updateFriends() {
        swipeRefreshLayout.setRefreshing(true);
        if (Session.user != null) {
            UserService.getFriends(FriendActivity.this, Session.user.getId(), new Service.ClientResponse<ArrayList<User>>() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
