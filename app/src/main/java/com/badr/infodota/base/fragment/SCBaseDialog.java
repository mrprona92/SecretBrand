package com.badr.infodota.base.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.badr.infodota.base.activity.ListHolderActivity;

import com.badr.infodota.R;
/**
 * Project : TRS Social
 * Author :Binh.TH
 * Date : 31/05/2016
 * Time : 14:39
 * Description :
 */
public class SCBaseDialog extends Dialog {

    protected Context mContext;
    private Handler mHandler;
    protected ListHolderActivity mActivity;

    public SCBaseDialog(Context context) {
        super(context);
        mContext = context;
        mActivity=(ListHolderActivity) context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(mContext, R.color.cmn_transparent)));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
    }

    protected Handler getMainHandler() {
        return mHandler;
    }

}
