package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ucm.tfg.Session;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;
import com.ucm.tfg.views.CustomViewPager;
import com.ucm.tfg.fragments.FilmFragment;
import com.ucm.tfg.fragments.RecommendationFragment;
import com.ucm.tfg.fragments.PlanFragment;
import com.ucm.tfg.R;
import com.ucm.tfg.adapters.FragmentAdapter;

public class MainActivity extends AppCompatActivity implements
        PlanFragment.OnFragmentInteractionListener,
        RecommendationFragment.OnFragmentInteractionListener,
        FilmFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FragmentAdapter adapter;
    private NavigationView navigationView;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new FragmentAdapter(MainActivity.this, getSupportFragmentManager());
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(adapter.getPageTitle(0));
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_plans);

        viewPager = findViewById(R.id.container);
        TabLayout tabLayout = findViewById(R.id.tab);

        viewPager.setSwipePagingEnabled(false);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        switch (i) {
                            case 0: ((PlanFragment) viewPager
                                    .getAdapter()
                                    .instantiateItem(viewPager, viewPager.getCurrentItem()))
                                    .setActive();
                                break;
                            case 1: ((RecommendationFragment) viewPager
                                    .getAdapter()
                                    .instantiateItem(viewPager, viewPager.getCurrentItem()))
                                    .setActive();
                            break;
                            case 2: ((FilmFragment) viewPager
                                    .getAdapter()
                                    .instantiateItem(viewPager, viewPager.getCurrentItem()))
                                    .setActive();
                            break;
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                }
        );

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

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
        boolean isLogged = sharedPreferences.getBoolean(Session.IS_LOGGED, false);

        if (!isLogged) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            long userId = sharedPreferences.getLong(Session.USER, 0);
            UserService.getUserById(MainActivity.this, userId, new Service.ClientResponse<User>() {
                @Override
                public void onSuccess(User result) {
                    Session.user = result;
                    TextView userName = navigationView.getHeaderView(0).findViewById(R.id.user_name);
                    userName.setText(Session.user.getName());
                }

                @Override
                public void onError(String error) {

                }
            }, User.class);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_logout:
                SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Session.IS_LOGGED, false);
                editor.putLong(Session.USER, 0);
                editor.apply();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
}
