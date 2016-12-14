package com.badr.infodota.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.badr.infodota.R;

/**
 * Project : TRS Social
 * Author :DANGLV
 * Date : 31/05/2016
 * Time : 14:40
 * Description :
 */
public class SCAlertDialog extends SCBaseDialog {

    private int mTitleId, mMessageId, mConfirmTextId, cancelTextId;
    private String mMessageStr;
    private Button mConfirmButton;

    private OnConfirmButtonClickedListener mListener;

    public void setOnConfirmButtonClickedListener(OnConfirmButtonClickedListener confirmButtonClicked) {
        mListener = confirmButtonClicked;
    }

    public SCAlertDialog(Context context) {
        super(context);
    }

    public SCAlertDialog(Context context, int themeResId) {
        super(context);
    }

    public SCAlertDialog(Context context, int messageId, int confirmTextId) {
        super(context);
        this.mMessageId = messageId;
        this.mConfirmTextId = confirmTextId;
    }

    public SCAlertDialog(Context context, String messageStr, int confirmTextId) {
        super(context);
        this.mMessageStr = messageStr;
        this.mConfirmTextId = confirmTextId;
    }

    public void setupViewWithTitle(int titleId, int messageId, int confirmText) {
        this.mMessageId = messageId;
        this.mConfirmTextId = confirmText;
        this.mTitleId = titleId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alert);
        mConfirmButton = (Button) findViewById(R.id.btnSettle);
        if (mTitleId > 0) {
            ((TextView) findViewById(R.id.lblTitle)).setText(mTitleId);
        } else {
            (findViewById(R.id.lblTitle)).setVisibility(View.GONE);
        }
        if (mMessageId > 0) {
            ((TextView) findViewById(R.id.lblMessage)).setText(mMessageId);
        } else {
            ((TextView) findViewById(R.id.lblMessage)).setText(mMessageStr);
        }

        if (cancelTextId > 0) {
            ((Button) findViewById(R.id.btnActionCancel)).setText(cancelTextId);
        }

        if (mConfirmTextId > 0) {
            mConfirmButton.setText(mConfirmTextId);
        }else{
            mConfirmButton.setVisibility(View.GONE);
        }

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SCAlertDialog.this.dismiss();
                if(mListener != null) {
                    mListener.onConfirmButtonClicked();
                }
            }
        });
    }

    public interface OnConfirmButtonClickedListener {
        void onConfirmButtonClicked();
    }
}
