package com.ucm.tfg.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ucm.tfg.R;
import com.ucm.tfg.fragments.FilmFragment;
import com.ucm.tfg.fragments.RecommendationFragment;
import com.ucm.tfg.fragments.PlanFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    private PlanFragment planFragment;
    private RecommendationFragment recommendationFragment;
    private FilmFragment filmFragment;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        planFragment = new PlanFragment();
        recommendationFragment = new RecommendationFragment();
        filmFragment = new FilmFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return planFragment;
            case 1: return recommendationFragment;
            case 2: return filmFragment;
            default: return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int pos) {
        switch (pos) {
            case 0: return context.getString(R.string.action_plans);
            case 1: return context.getString(R.string.action_recommendations);
            case 2: return context.getString(R.string.action_films);
            default: return null;
        }
    }

}
