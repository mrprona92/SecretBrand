package com.badr.infodota.base.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

/**
 * Created by ABadretdinov
 * 12.10.2015
 * 17:00
 */
public class UiUtils {
    public static void moveView(final View viewToMove, final int moveToX, final int moveToY, long duration) {
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewToMove.getLayoutParams();
                layoutParams.topMargin = (int) (moveToY * interpolatedTime);
                layoutParams.leftMargin = (int) (moveToX * interpolatedTime);
                viewToMove.setLayoutParams(layoutParams);
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(duration);
        viewToMove.startAnimation(a);
    }

    public static void setViewVisible(final View view, long duration) {
        view.setAlpha(0.0f);
        view.setVisibility(View.VISIBLE);
        view.animate().setDuration(duration).alpha(1.0f);
    }
}
