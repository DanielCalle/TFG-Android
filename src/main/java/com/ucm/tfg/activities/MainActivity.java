package com.ucm.tfg.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.ucm.tfg.fragments.CenterFragment;
import com.ucm.tfg.fragments.LeftFragment;
import com.ucm.tfg.R;
import com.ucm.tfg.fragments.RightFragment;
import com.ucm.tfg.adapters.SwipeAdapter;
import com.unity3d.player.UnityPlayer;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements
        LeftFragment.OnFragmentInteractionListener,
        CenterFragment.OnFragmentInteractionListener,
        RightFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);

        viewPager.setAdapter(
                new SwipeAdapter(getSupportFragmentManager())
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
}
