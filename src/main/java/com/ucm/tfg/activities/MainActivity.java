package com.ucm.tfg.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ucm.tfg.fragments.CenterFragment;
import com.ucm.tfg.fragments.LeftFragment;
import com.ucm.tfg.R;
import com.ucm.tfg.fragments.RightFragment;
import com.ucm.tfg.adapters.SwipeAdapter;

public class MainActivity extends AppCompatActivity implements
        LeftFragment.OnFragmentInteractionListener,
        CenterFragment.OnFragmentInteractionListener,
        RightFragment.OnFragmentInteractionListener
{

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.container);
        pagerAdapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, UnityPlayerActivity.class));
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
