package com.badr.infodota.base.util;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by ABadretdinov
 * 17.08.2015
 * 12:03
 */
public class GrayImageLoadListener extends SimpleTarget<GlideDrawable> {
    private ImageView mImageView;

    public GrayImageLoadListener(ImageView mImageView) {
        super();
        this.mImageView = mImageView;
    }

    @Override
    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
        mImageView.setImageBitmap(BitmapUtils.drawableToGrayScale(resource));
    }
}
