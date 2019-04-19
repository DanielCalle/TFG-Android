package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        FilmFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);

        viewPager.setSwipePagingEnabled(false);

        viewPager.setAdapter(
                new FragmentAdapter(getSupportFragmentManager())
        );

        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout)
        );

        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager)
        );

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.unity);
        floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(this, UnityPlayerActivity.class));
        });

        SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Session.IS_LOGGED, false);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(Session.SESSION_FILE, 0);
        boolean isLogged = sharedPreferences.getBoolean(Session.IS_LOGGED, false);
        if (!isLogged) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
