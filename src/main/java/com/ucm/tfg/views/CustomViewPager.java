package com.ucm.tfg.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Added a function to enable swipe between fragments
 */
public class CustomViewPager extends ViewPager {

    // Determine if it can be swiped
    private boolean swipe;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.swipe = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.swipe) {
            return super.onTouchEvent(event);
        }
        return false;
    }
    @Override

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.swipe) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    // Enabling and desabling
    public void setSwipePagingEnabled(boolean swipe) {
        this.swipe = swipe;
    }
}
