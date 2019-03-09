package com.ucm.tfg.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ucm.tfg.fragments.FilmFragment;
import com.ucm.tfg.fragments.RecommendationFragment;
import com.ucm.tfg.fragments.PlanFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }



    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return new PlanFragment();
            case 1: return new RecommendationFragment();
            case 2: return new FilmFragment();
            default: return null;
        }
    }

}
