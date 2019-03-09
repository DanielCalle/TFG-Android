package com.ucm.tfg.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ucm.tfg.fragments.RecommendationFragment;
import com.ucm.tfg.fragments.PlanFragment;
import com.ucm.tfg.R;
import com.ucm.tfg.fragments.RightFragment;
import com.ucm.tfg.adapters.FragmentAdapter;

public class MainActivity extends AppCompatActivity implements
        PlanFragment.OnFragmentInteractionListener,
        RecommendationFragment.OnFragmentInteractionListener,
        RightFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);

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


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public String getCurrentUser(){
        return "5df9b1ab2e9742aa9bfd4a7d12dde033";
    }
}
