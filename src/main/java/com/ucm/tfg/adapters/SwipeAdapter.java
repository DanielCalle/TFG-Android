package com.ucm.tfg.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ucm.tfg.fragments.CenterFragment;
import com.ucm.tfg.fragments.LeftFragment;
import com.ucm.tfg.fragments.RightFragment;

public class SwipeAdapter extends FragmentPagerAdapter {

    public  SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }



    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return new LeftFragment();
            case 1: return new CenterFragment();
            case 2: return new RightFragment();
            default: return null;
        }
    }

}
