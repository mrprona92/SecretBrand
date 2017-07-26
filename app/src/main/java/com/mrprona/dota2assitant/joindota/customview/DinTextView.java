package com.mrprona.dota2assitant.joindota.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.mrprona.dota2assitant.joindota.customview.CustomFontTextView.Style;
import me.zhanghai.android.materialprogressbar.*;
import com.mrprona.dota2assitant.R;

public class DinTextView extends CustomFontTextView {
    private AttributeSet attrs;
    private boolean mBold;

    /* renamed from: com.strafelib.android.fonts.DinTextView.1 */
    static /* synthetic */ class C18081 {
        static final /* synthetic */ int[] $SwitchMap$com$strafelib$android$fonts$CustomFontTextView$Style;

        static {
            $SwitchMap$com$strafelib$android$fonts$CustomFontTextView$Style = new int[Style.values().length];
            try {
                $SwitchMap$com$strafelib$android$fonts$CustomFontTextView$Style[Style.REGULAR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$strafelib$android$fonts$CustomFontTextView$Style[Style.MEDIUM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$strafelib$android$fonts$CustomFontTextView$Style[Style.MEDIUMBOLD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$strafelib$android$fonts$CustomFontTextView$Style[Style.BOLD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$strafelib$android$fonts$CustomFontTextView$Style[Style.LIGHT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public DinTextView(Context context) {
        this(context, null);
    }

    public DinTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        setFont(context, false);
    }

    public DinTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.attrs = attrs;
        setFont(context, false);
    }

    private void setFont(Context context, boolean bold) {
        this.mBold = bold;
        if (this.attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(this.attrs, R.styleable.SCTabLayout, 0, 0);
            try {
                switch (C18081.$SwitchMap$com$strafelib$android$fonts$CustomFontTextView$Style[Style.values()[a.getInteger(R.styleable.SCTabLayout_tabSelectIndex, 0)].ordinal()]) {
                    case R.styleable.View_android_focusable /*1*/:
                        if (!bold) {
                            setTypeface(Font.get(Font.DIN_REGULAR, context), 0);
                            break;
                        } else {
                            setTypeface(Font.get(Font.DIN_REGULAR, context), 1);
                            break;
                        }
                    case R.styleable.View_paddingStart /*2*/:
                        if (!bold) {
                            setTypeface(Font.get(Font.DIN_MEDIUM, context), 0);
                            break;
                        } else {
                            setTypeface(Font.get(Font.DIN_MEDIUM, context), 1);
                            break;
                        }
                    case R.styleable.View_paddingEnd /*3*/:
                        setTypeface(Font.get(Font.DIN_MEDIUM, context), 1);
                        this.mBold = true;
                        break;
                    case R.styleable.View_theme /*4*/:
                        setTypeface(Font.get(Font.DIN_BOLD, context));
                        this.mBold = true;
                        break;
                    case R.styleable.Toolbar_contentInsetStart /*5*/:
                        if (!bold) {
                            setTypeface(Font.get(Font.DIN_LIGHT, context), 0);
                            break;
                        } else {
                            setTypeface(Font.get(Font.DIN_LIGHT, context), 1);
                            break;
                        }
                }
                a.recycle();
            } catch (Throwable th) {
                a.recycle();
            }
        } else if (bold) {
            setTypeface(Font.get(Font.DIN_MEDIUM, context), 1);
        } else {
            setTypeface(Font.get(Font.DIN_MEDIUM, context), 0);
        }
    }

    public void setBold(boolean bold) {
        setFont(getContext(), bold);
    }

    public boolean isBold() {
        return this.mBold;
    }
}
