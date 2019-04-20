package com.ucm.tfg.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ucm.tfg.R;
import com.ucm.tfg.fragments.FilmFragment;
import com.ucm.tfg.fragments.RecommendationFragment;
import com.ucm.tfg.fragments.PlanFragment;

import java.nio.charset.Charset;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
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

    @Override
    public CharSequence getPageTitle(int pos) {
        switch (pos) {
            case 0: return context.getString(R.string.plans);
            case 1: return context.getString(R.string.recommendations);
            case 2: return context.getString(R.string.saved_films);
            default: return null;
        }
    }

}
