package com.pickachu.momma.widgets;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.pickachu.momma.R;

/**
 * Created by surbhi on 30/12/15.
 */
public class ProgressBarWidget extends ProgressBar {

    public ProgressBarWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setColorForProgressBar(context);
    }

    public ProgressBarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorForProgressBar(context);
    }

    public ProgressBarWidget(Context context) {
        super(context);
        setColorForProgressBar(context);
    }

    private void setColorForProgressBar(Context context) {
        setIndeterminate(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setIndeterminateDrawable(ContextCompat.getDrawable(context, R.drawable.progress_wheel));
        }
    }
}
