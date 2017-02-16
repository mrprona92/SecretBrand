package com.mrprona.dota2assitant.base.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heetch.countrypicker.Country;
import com.heetch.countrypicker.CountryPickerCallbacks;
import com.heetch.countrypicker.CountryPickerDialog;
import com.heetch.countrypicker.Utils;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.base.model.ContentBug;
import com.mrprona.dota2assitant.quiz.util.UltilsConnection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.trim;


/**
 * Project : TRS Social
 * Author :DANGLV
 * Date : 31/05/2016
 * Time : 14:40
 * Description :
 */

public class SubmitBugDialog extends DABaseDialog {

    public interface ConfirmDialogListener {
        void onSelect(int indexButton, int mode);
    }

    private int mTitleId, mMessageId, mConfirmTextId, mCancelTextId;
    private String mMessageString;
    private String mTitleString;
    private int mConfirmBtnResId;
    private long score;
    protected View mView;


    public boolean isChangeLayout() {
        return isChangeLayout;
    }

    public void setChangeLayout(boolean changeLayout) {
        isChangeLayout = changeLayout;
    }

    private boolean isChangeLayout;

    public int getTitleId() {
        return mTitleId;
    }

    public void setTitleId(int titleId) {
        this.mTitleId = titleId;
    }

    public int getMessageId() {
        return mMessageId;
    }

    public void setMessageId(int messageId) {
        this.mMessageId = messageId;
    }

    public int getConfirmTextId() {
        return mConfirmTextId;
    }

    public void setConfirmTextId(int confirmTextId) {
        this.mConfirmTextId = confirmTextId;
    }

    public int getCancelTextId() {
        return mCancelTextId;
    }

    public void setCancelTextId(int cancelTextId) {
        this.mCancelTextId = cancelTextId;
    }

    public String getMessageString() {
        return mMessageString;
    }

    public void setMessageString(String messageString) {
        this.mMessageString = messageString;
    }

    public String getTitleString() {
        return mTitleString;
    }

    public void setTitleString(String titleString) {
        this.mTitleString = titleString;
    }

    public int getConfirmBtnResId() {
        return mConfirmBtnResId;
    }

    public void setConfirmBtnResId(int confirmBtnResId) {
        this.mConfirmBtnResId = confirmBtnResId;
    }

    public SubmitBugDialog(Context context, int themeResId) {
        super(context);
    }


    public SubmitBugDialog(Context context) {
        super(context);
    }


    private Context mContext;

    public SubmitBugDialog(Context context, Bundle mBundle) {
        super(context);
        this.mContext = context;
        this.mBundle = mBundle;
    }

    private Bundle mBundle;
    private int mode;



    public void setupViewWithTitle(int titleId, int messageId, int confirmText, int cancelText, final ConfirmDialogListener listener) {

        this.mMessageId = messageId;
        this.mTitleId = titleId;
        this.mConfirmTextId = confirmText;
        this.mCancelTextId = cancelText;
        this.mTitleString = "";
        this.mMessageString = "";
    }

    @Override
    protected void onStart() {
        super.onStart();


      /*  mSpinner.setAdapter(new CountriesListAdapter(getContext(), getContext().getResources().getStringArray(R.array.CountryCodes)));

        mSpinner.setSelected(true);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "Ranking type: onItemSelected() called with: " + "position = [" + position + "], id = [" + id + "]");
                mSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        btnActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitBugDialog.this.dismiss();
            }
        });


        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UltilsConnection.isNetworkConnected()) {
                    Toast.makeText(mContext, "Connection error. Please recheck your connect!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBug.setContentBug(mEditTextName.getText().toString());
                if (mBug.getContentBug() != null && !mBug.getContentBug().isEmpty() && (trim(mBug.getContentBug()).length() != 0)) {
                    mDBreference.child("bug").child(getUid()).setValue(mBug);
                    SubmitBugDialog.this.dismiss();
                } else {
                    Toast.makeText(mContext, "Content bug invalid. Please recheck content!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    ;

    private TextView lblChooseCountry;
    private LinearLayout layoutCountry;
    private TextView lblTextMessage;
    private Button btnActionCancel;
    private Button mRightButton;
    private ImageView mImageFlag;
    private ContentBug mBug;
    private EditText mEditTextName;
    private DatabaseReference mDBreference;
    private TextView mTitleText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mConfirmBtnResId > 0) mRightButton.setBackgroundResource(mConfirmBtnResId);

        setContentView(R.layout.dialog_status_confirm);

        lblChooseCountry = (TextView) findViewById(R.id.lblChooseCountry);
        lblTextMessage = (TextView) findViewById(R.id.lblMessage);
        btnActionCancel = (Button) findViewById(R.id.btnActionCancel);
        mRightButton = (Button) findViewById(R.id.btnSettle);
        mImageFlag = (ImageView) findViewById(R.id.imgViewFlag);
        mEditTextName = (EditText) findViewById(R.id.eTxtName);
        mTitleText = (TextView) findViewById(R.id.lblTitle);
        mTitleText = (TextView) findViewById(R.id.lblTitle);
        mTitleText.setText("REPORT A BUG");

        layoutCountry = (LinearLayout) findViewById(R.id.layoutCountry);
        layoutCountry.setVisibility(View.GONE);

        lblTextMessage.setText("Many thanks for your support to make app better");
        mBug = new ContentBug();
        mDBreference = FirebaseDatabase.getInstance().getReference();

    }

    public String getUid() {
        String key = mDBreference.push().getKey();
        return key;
    }

}
