package com.badr.infodota.base.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.fragment.LoaderDialogFragment;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 13:47
 */
public class DialogUtils {

    public static void showAlert(Context context, String title, String alert, final DialogInterface.OnClickListener listener) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.alert_dialog);
            dialog.setCancelable(false);
            if (TextUtils.isEmpty(title)) {
                dialog.findViewById(R.id.title).setVisibility(View.GONE);
            } else {
                ((TextView) dialog.findViewById(R.id.title)).setText(title);
            }
            ((TextView) dialog.findViewById(R.id.message)).setText(alert);
            dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(dialog, 0);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static <T> void showLoaderDialog(FragmentManager fragmentManager, ProgressTask<T> progressTask) {
        showLoaderDialog(fragmentManager, progressTask, null);
    }

    public static <T> void showLoaderDialog(FragmentManager fragmentManager, ProgressTask<T> progressTask, String loadingMessage) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);//todo вызывать dismiss мб сперва?
        }
        //ft.addToBackStack(null);
        LoaderDialogFragment<T> fragment = new LoaderDialogFragment<T>();
        fragment.setTask(progressTask);
        fragment.setLoadingMessage(loadingMessage);
        fragment.show(ft, "dialog");
    }

    public static void showYesNoDialog(Context context, String msg, String confirmMsg,
                                       DialogInterface.OnClickListener yesListener) {
        showYesNoDialog(context, msg, confirmMsg, yesListener, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public static void showYesNoDialog(Context context, String msg, String confirmMsg,
                                       final DialogInterface.OnClickListener yesListener, final DialogInterface.OnClickListener noListener) {
        showYesNoDialog(context, msg, confirmMsg, context.getString(android.R.string.no), yesListener, noListener);
    }

    public static void showYesNoDialog(Context context, String msg, String confirmMsg, String cancelMsg,
                                       final DialogInterface.OnClickListener yesListener, final DialogInterface.OnClickListener noListener) {
        showYesNoDialog(context, context.getString(R.string.alert), msg, confirmMsg, cancelMsg, yesListener, noListener);
    }

    public static void showYesNoDialog(Context context, String title, String msg, String confirmMsg, String cancelMsg,
                                       final DialogInterface.OnClickListener yesListener, final DialogInterface.OnClickListener noListener) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.yes_no_dialog);
            TextView titleView = (TextView) dialog.findViewById(R.id.title);
            titleView.setText(title);
            ((TextView) dialog.findViewById(R.id.message)).setText(msg);
            Button okButton = (Button) dialog.findViewById(R.id.ok);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (yesListener != null) {
                        yesListener.onClick(dialog, 0);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            okButton.setText(confirmMsg);
            Button noButton = (Button) dialog.findViewById(R.id.cancel);
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noListener != null) {
                        noListener.onClick(dialog, 0);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            noButton.setText(cancelMsg);
            dialog.show();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
