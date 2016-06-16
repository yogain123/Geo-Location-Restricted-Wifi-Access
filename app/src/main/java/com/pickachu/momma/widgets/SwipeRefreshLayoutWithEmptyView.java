package com.pickachu.momma.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ishaangarg on 02/12/15.
 */
public class SwipeRefreshLayoutWithEmptyView extends SwipeRefreshLayout {
    private RecyclerView mRecyclerView;

    public SwipeRefreshLayoutWithEmptyView(Context context) {
        super(context);
    }

    public SwipeRefreshLayoutWithEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {

        if (mRecyclerView == null) {
            //Log.e(SwipeRefreshLayoutWithEmptyView.class.getSimpleName(), "recyclerView is not defined!");
            return false;
        }

        if (mRecyclerView.getVisibility() == View.VISIBLE) {

            return ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() > 0 ||
                    mRecyclerView.getChildAt(0) == null ||
                    mRecyclerView.getChildAt(0).getTop() <= 0;
        } else {
            return false;
        }
    }

    /**
     * Listener that controls if scrolling up is allowed to child views or not
     */
    public void setOnChildScrollUpListener(LinearLayout clickableLinearLayout) {
        clickableLinearLayout.setClickable(true);
    }

    public void setOnChildScrollUpListener(View clickableLinearLayout, RecyclerView recyclerView) {
        clickableLinearLayout.setClickable(true);
        mRecyclerView = recyclerView;
    }
}
