package com.ucm.tfg.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.ViewGroupUtils;
import android.util.AttributeSet;
import android.view.View;

public class AnchorVisibilityBehavior<T extends View> extends CoordinatorLayout.Behavior<T> {

    private Rect mTmpRect;

    public AnchorVisibilityBehavior() { }

    public AnchorVisibilityBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, T child, View dependency) {
        // check that our dependency is the AppBarLayout
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, T child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            return updateProgressBarVisibility(parent, (AppBarLayout) dependency, child);
        }
        return false;
    }

    private boolean updateProgressBarVisibility(CoordinatorLayout parent, AppBarLayout appBarLayout, T child) {
        final CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.getAnchorId() != appBarLayout.getId()) {
            // The anchor ID doesn't match the dependency, so we won't automatically
            // show/hide the FAB
            return false;
        }

        if (mTmpRect == null) {
            mTmpRect = new Rect();
        }

        // First, let's get the visible rect of the dependency
        if (child.getVisibility() != View.GONE) {
            final Rect rect = mTmpRect;
            ViewGroupUtils.getDescendantRect(parent, appBarLayout, rect);

            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                // If the anchor's bottom is below the seam, hide it
                child.setVisibility(View.INVISIBLE);
            } else {
                // Else, show it
                child.setVisibility(View.VISIBLE);
            }
        }
        return true;
    }

}
