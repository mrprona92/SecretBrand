package com.badr.infodota.base.util;

import android.view.View;

import com.badr.infodota.base.view.TransformableViewPager;

/**
 * Created by ABadretdinov
 * 26.12.2014
 * 13:46
 */
public class CarouselPageTransformer implements TransformableViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        float offset = 0;
        float fakePosition = position;
        if (Math.abs(position) < 5) {
            if (fakePosition < 0) {
                while (fakePosition < 0) {
                    offset += fakePosition;
                    fakePosition++;
                }
            } else if (fakePosition > 0) {
                while (fakePosition > 0) {
                    offset += fakePosition;
                    fakePosition--;
                }
            }
        }
        offset -= position;
        offset *= 2;
        offset += position;
        float scale = (float) (1 - 0.2 * Math.abs(position));
        page.setTranslationX((float) (-pageWidth * 0.2 * offset) / 2);
        page.setScaleX(scale);
        page.setScaleY(scale);
        //page.setRotationY(position * -5);

    }
}
