package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ucm.tfg.Session;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.requests.Request;
import com.ucm.tfg.requests.UserRequest;
import com.ucm.tfg.views.CustomViewPager;
import com.ucm.tfg.fragments.FilmFragment;
import com.ucm.tfg.fragments.RecommendationFragment;
import com.ucm.tfg.fragments.PlanFragment;
import com.ucm.tfg.R;
import com.ucm.tfg.adapters.FragmentAdapter;

/**
 * The principal activity, it contains three fragments, and it is where all flows start
 */
public class MainActivity extends AppCompatActivity implements
        PlanFragment.OnFragmentInteractionListener,
        RecommendationFragment.OnFragmentInteractionListener,
        FilmFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private final static int LOGIN_REQUEST = 1;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FragmentAdapter adapter;
    private NavigationView navigationView;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adapter for the three fragments
        adapter = new FragmentAdapter(MainActivity.this, getSupportFragmentManager());

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(adapter.getPageTitle(0));
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_plans);

        viewPager = findViewById(R.id.container);
        TabLayout tabLayout = findViewById(R.id.tab);

        viewPager.setSwipePagingEnabled(false);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager)
        );

        FloatingActionButton floatingActionButton = findViewById(R.id.unity);
        floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(this, UnityPlayerActivity.class));
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Sidebar menu
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Checking if user is logged
        SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
        boolean isLogged = sharedPreferences.getBoolean(Session.IS_LOGGED, false);

        if (!isLogged) {
            // if not logged redirect to login
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST);
        } else {
            // else saving logged user in session
            long userId = sharedPreferences.getLong(Session.USER, 0);
            UserRequest.getUserById(MainActivity.this, userId, new Request.ClientResponse<User>() {
                @Override
                public void onSuccess(User result) {
                    Session.user = result;
                    TextView userName = navigationView.getHeaderView(0).findViewById(R.id.user_name);
                    userName.setText(Session.user.getName());
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }, User.class);
        }
    }

    @Override
    public void onFragmentLoaded() {}

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Actions for sidebar menu
        switch (menuItem.getItemId()) {
            case R.id.action_logout:
                SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Session.IS_LOGGED, false);
                editor.putLong(Session.USER, 0);
                editor.apply();

                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), LOGIN_REQUEST);
                break;
            case R.id.action_friends:
                startActivity(new Intent(MainActivity.this, FriendActivity.class));
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOGIN_REQUEST:
                recreate();
                break;
        }
    }
}
