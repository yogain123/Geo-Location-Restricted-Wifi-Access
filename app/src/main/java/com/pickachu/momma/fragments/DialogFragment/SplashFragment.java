package com.pickachu.momma.fragments.DialogFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pickachu.momma.R;

import static com.pickachu.momma.utils.UIUtils.animateBounceShow;

/**
 * Created by vaibhavsinghal on 25/11/15.
 */
public class SplashFragment extends BaseDialogFragment {

    private final String TAG = this.getClass().getSimpleName();

    private Handler splashDismissHandler;

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_splash, null);

        setupView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        splashDismissHandler = new Handler();
        dismissSplash();
    }

    @Override
    public void onPause() {
        super.onPause();

        dismissAllowingStateLoss();
        splashDismissHandler.removeCallbacksAndMessages(null);
        Log.i(TAG, "CALLBACKS FROM HANDLER REMOVED");
    }

    private void setupView(View view) {

        ImageView sfxLogo = (ImageView) view.findViewById(R.id.sfxSplashLogo);
        animateBounceShow(getActivity(), sfxLogo);
    }

    public void dismissSplash() {

        splashDismissHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                dismissAllowingStateLoss();
            }
        }, 3000);
    }
}