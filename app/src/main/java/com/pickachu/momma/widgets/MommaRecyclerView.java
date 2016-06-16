package com.pickachu.momma.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by vaibhavsinghal on 27/11/15.
 */
public class MommaRecyclerView extends RecyclerView {

    private final String TAG = this.getClass().getSimpleName();


    public MommaRecyclerView(Context context) {
        super(context);
    }

    public MommaRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MommaRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
