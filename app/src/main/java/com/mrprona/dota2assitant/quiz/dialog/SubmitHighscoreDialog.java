package com.mrprona.dota2assitant.quiz.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.base.dialog.DABaseDialog;
import com.mrprona.dota2assitant.quiz.model.HighScore;
import com.mrprona.dota2assitant.quiz.util.UltilsConnection;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heetch.countrypicker.Country;
import com.heetch.countrypicker.CountryPickerCallbacks;
import com.heetch.countrypicker.CountryPickerDialog;
import com.heetch.countrypicker.Utils;

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

public class SubmitHighscoreDialog extends DABaseDialog {

    public interface ConfirmDialogListener {
        void onSelect(int indexButton,int mode);
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

    public SubmitHighscoreDialog(Context context, int themeResId) {
        super(context);
    }

    public SubmitHighscoreDialog(Context context) {
        super(context);
    }


    private Context mContext;

    public SubmitHighscoreDialog(Context context, Bundle mBundle, ConfirmDialogListener mListener) {
        super(context);
        this.mContext = context;
        this.mBundle = mBundle;
        this.mListener= mListener;
    }

    private ConfirmDialogListener mListener;
    private Bundle mBundle;
    private int mode;

    public void setOnConfirmDialogListener(ConfirmDialogListener confirmDialogListener) {
        mListener = confirmDialogListener;
    }

    public void setupViewWithTitle(int titleId, int messageId, int confirmText, int cancelText, final ConfirmDialogListener listener) {
        this.mListener = listener;
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
        score = mBundle.getLong("score");
        mode = mBundle.getInt("mode");
        mListener= (ConfirmDialogListener)ListHolderActivity.getAppContext();

        lblTextMessage.setText(String.format(mContext.getResources().getString(R.string.quiz_high_score_dialog_title_score), score + ""));

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
                SubmitHighscoreDialog.this.dismiss();
                if (mListener != null) {
                    mListener.onSelect(0,mode);
                }
            }
        });


        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UltilsConnection.isNetworkConnected()){
                    Toast.makeText(mContext, "Connection error. Please recheck your connect!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mHighScore.setName(getTextName());
                mHighScore.setDate(getDateTime());
                mHighScore.setScore(score);
                if (mHighScore.getName() != null && !mHighScore.getName().isEmpty()&&(trim(mHighScore.getName()).length() != 0))  {
                    if(mode==1){
                        mDBreference.child("score_item").child(getUid()).setValue(mHighScore);
                    }else if(mode ==2){
                        mDBreference.child("score_hero").child(getUid()).setValue(mHighScore);
                    }else{
                        mDBreference.child("score_random").child(getUid()).setValue(mHighScore);
                    }
                    SubmitHighscoreDialog.this.dismiss();
                    if (mListener != null) {
                        mListener.onSelect(1,mode);
                    }
                }else{
                    Toast.makeText(mContext, "Name invalid. Please refill your name!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private TextView lblChooseCountry;
    private TextView lblTextMessage;
    private Button btnActionCancel;
    private Button mRightButton;
    private ImageView mImageFlag;
    private HighScore mHighScore;
    private EditText mEditTextName;
    private DatabaseReference mDBreference;


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
        mHighScore = new HighScore();
        mDBreference= FirebaseDatabase.getInstance().getReference();

        lblChooseCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryPickerDialog countryPicker =
                        new CountryPickerDialog(mContext, new CountryPickerCallbacks() {
                            @Override
                            public void onCountrySelected(Country country, int flagResId) {
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        lblChooseCountry.setVisibility(View.GONE);
                                    }
                                });
                                setContentHS(country.getIsoCode());
                                Log.d("Binh", "onCountrySelected() called with: country = [" + country + "], flagResId = [" + flagResId + "]");
                                //Utils.getMipmapResId(mContext, c);
                            }
                        });
                countryPicker.show();
            }
        });

        mImageFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountryPickerDialog countryPicker =
                        new CountryPickerDialog(mContext, new CountryPickerCallbacks() {
                            @Override
                            public void onCountrySelected(Country country, int flagResId) {
                                String drawableName = country.getIsoCode().toLowerCase(Locale.ENGLISH) + "_flag";
                                mImageFlag.setImageResource(Utils.getMipmapResId(mContext, drawableName));
                                lblChooseCountry.setVisibility(View.GONE);
                                //Utils.getMipmapResId(mContext, c);
                                setContentHS(country.getIsoCode());

                            }
                        });
                countryPicker.show();
            }
        });

  /*      if (mTitleId > 0) {
            ((TextView) findViewById(R.id.lblTitle)).setText(mTitleId);
        } else if (mTitleString != null && !mTitleString.equals("")) {
            ((TextView) findViewById(R.id.lblTitle)).setText(mTitleString);
        } else
            (findViewById(R.id.lblTitle)).setVisibility(View.GONE);
*/

       /* if (mMessageId > 0) ((TextView) findViewById(R.id.lblMessage)).setText(mMessageId);
        else if (mMessageString != null && !mMessageString.equals("")) {
            ((TextView) findViewById(R.id.lblTitle)).setText(mMessageString);
        }
*/

    }

    private void setContentHS(String state) {
        mHighScore.setState(state);
    }


    private String getTextName() {
        return mEditTextName.getText().toString() + "";
    }


    public String getUid() {
        String key = mDBreference.push().getKey();
        return key;
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
