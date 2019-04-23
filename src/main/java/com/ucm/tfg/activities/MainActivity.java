package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ucm.tfg.Session;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new FragmentAdapter(MainActivity.this, getSupportFragmentManager());
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(adapter.getPageTitle(0));
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_plans);

        CustomViewPager viewPager = findViewById(R.id.container);
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
                        toolbar.setTitle(adapter.getPageTitle(i));
                        toolbar.getMenu().clear();
                        switch (i) {
                            case 0:
                                toolbar.inflateMenu(R.menu.menu_plans);
                                break;
                            case 1:
                                toolbar.inflateMenu(R.menu.menu_recommendations);
                                break;
                            case 2:
                                toolbar.inflateMenu(R.menu.menu_films);
                                break;
                            default:
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

        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        userName.setText("USERNAME");

        if (!isLogged) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
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
            case R.id.logout:
                SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Session.IS_LOGGED, false);
                editor.putString(Session.USER, null);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            default: break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
