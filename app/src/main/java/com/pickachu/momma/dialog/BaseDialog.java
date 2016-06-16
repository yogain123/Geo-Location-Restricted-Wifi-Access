package com.pickachu.momma.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pickachu.momma.MommaApp;
import com.pickachu.momma.Network.NetworkManager;
import com.pickachu.momma.R;
import com.pickachu.momma.database.DbSingleton;
import com.pickachu.momma.database.SqliteHelper;

import static com.pickachu.momma.BaseActivity.resetCurrentDialogTag;
import static com.pickachu.momma.utils.UIUtils.getColorRedId;
import static com.pickachu.momma.utils.UIUtils.getDisplayMetrics;

/**
 * Created by vaibhavsinghal on 27/11/15.
 */


public class BaseDialog extends Dialog implements View.OnClickListener {

    protected Context context;
    protected NetworkManager networkManager;
    protected SqliteHelper sqliteHelper;
    protected int computedWidth = 0;

    public BaseDialog(Context context) {
        super(context);
        this.context = context;
        this.networkManager = NetworkManager.newInstance(context, MommaApp.BASE_URL);
        this.sqliteHelper = DbSingleton.getInstance();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();

        resetCurrentDialogTag();
    }

    protected void setupView(String dialogTitle, int layoutId) {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(layoutId);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.row_layout_dialog_header);

        ((TextView) findViewById(R.id.txtTitle)).setText(dialogTitle);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int divierId = context.getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = findViewById(divierId);
            divider.setBackgroundColor(getColorRedId(context, R.color.white));
        }

        heightAdjust();
    }

    protected void setupView(int layoutId) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutId);

        heightAdjust();
    }

    protected void heightAdjust() {

        int screenWidth = getDisplayMetrics(context).widthPixels;
        int widthMargin = 40;
        computedWidth = screenWidth - widthMargin;
        getWindow().setLayout(computedWidth, WindowManager.LayoutParams.WRAP_CONTENT);

        /*Display display = ((WindowManager) context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();

        int dialogHeight = display.getHeight();
        int screenWidth = getDisplayMetrics(context).widthPixels;
        int widthMargin = 30;
        computedWidth = screenWidth - widthMargin;

        int screenHeight = getDisplayMetrics(context).heightPixels;
        int maxHeight = screenHeight * 9 / 10;
        if (dialogHeight > maxHeight) {
            this.getWindow().setLayout(computedWidth, maxHeight);
        }*/
    }

    @Override
    public void onClick(View v) {

    }
}
