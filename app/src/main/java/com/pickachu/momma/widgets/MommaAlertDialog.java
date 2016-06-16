package com.pickachu.momma.widgets;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;
import android.util.Log;

import static com.pickachu.momma.BaseActivity.resetCurrentDialogTag;

/**
 * Created by vaibhavsinghal on 4/11/15.
 */
public class MommaAlertDialog extends AlertDialog {

    private final String TAG = this.getClass().getSimpleName();

    public MommaAlertDialog(Context context) {
        super(context);
    }

    public MommaAlertDialog(Context context, String message) {
        super(context);

        this.setMessage(message);
    }

    public MommaAlertDialog(Context context, Spanned message) {
        super(context);

        this.setMessage(message);
    }

    public MommaAlertDialog(Context context, String title, String message) {
        super(context);

        this.setTitle(title);
        this.setMessage(message);
    }

    public MommaAlertDialog(Context context, String title, Spanned message) {
        super(context);

        this.setTitle(title);
        this.setMessage(message);
    }

    @Override
    public void show() {
        super.show();
        Log.i(TAG, TAG + " Shown");
    }

    @Override
    public void dismiss() {
        super.hide();
        Log.i(TAG, TAG + " Hidden");
        resetCurrentDialogTag();
    }


}
