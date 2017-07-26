package com.mrprona.dota2assitant.joindota.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class CustomFontTextView extends AppCompatTextView {
    public final String TAG;

    public enum Gravity {
        CENTER
    }

    public enum Style {
        REGULAR,
        MEDIUM,
        MEDIUMBOLD,
        BOLD,
        LIGHT
    }

    public CustomFontTextView(Context context) {
        super(context);
        this.TAG = getClass().getSimpleName();
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.TAG = getClass().getSimpleName();
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.TAG = getClass().getSimpleName();
    }
}
