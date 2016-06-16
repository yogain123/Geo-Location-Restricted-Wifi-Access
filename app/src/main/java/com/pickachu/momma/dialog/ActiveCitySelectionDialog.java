package com.pickachu.momma.dialog;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pickachu.momma.R;

import java.util.List;

import static com.pickachu.momma.utils.UIUtils.animateFadeHide;
import static com.pickachu.momma.utils.UIUtils.animateFadeShow;

public class ActiveCitySelectionDialog extends BaseDialog {


    public ActiveCitySelectionDialog(Context context) {
        super(context);
    }

    @Override
    public void show() {
        this.setupView("Title", R.layout.row_layout_dialog_header);
        super.show();
    }

    @Override
    protected void setupView(String dialogTitle, int layoutId) {
        super.setupView(dialogTitle, layoutId);


    }
}
