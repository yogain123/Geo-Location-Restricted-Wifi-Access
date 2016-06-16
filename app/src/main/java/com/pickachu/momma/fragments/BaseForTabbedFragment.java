package com.pickachu.momma.fragments;

import android.os.Bundle;
import android.view.View;

import com.pickachu.momma.BaseActivity;

import static com.pickachu.momma.utils.UIUtils.hideToolbarShadow;

/**
 * Created by vaibhavsinghal on 2/11/15.
 */
public class BaseForTabbedFragment extends BaseFragment {

    public final String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hideToolbarShadow((BaseActivity) getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {

    }
}