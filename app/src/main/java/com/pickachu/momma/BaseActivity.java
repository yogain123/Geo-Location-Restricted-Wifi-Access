package com.pickachu.momma;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import com.pickachu.momma.Network.ImageRequestManager;
import com.pickachu.momma.Network.NetworkManager;
import com.pickachu.momma.database.DbSingleton;
import com.pickachu.momma.database.SqliteHelper;
import com.pickachu.momma.fragments.BaseFragment;
import com.pickachu.momma.fragments.DialogFragment.SplashFragment;
import com.pickachu.momma.utils.DateTimeUtils;
import com.pickachu.momma.widgets.ProgressBarWidget;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.pickachu.momma.utils.UIUtils.animateFadeHide;
import static com.pickachu.momma.utils.UIUtils.animateFadeShow;
import static com.pickachu.momma.utils.UIUtils.hideKeyboard;

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    public static final int MAIN_ACTIVITY_CONTAINER_ID = R.id.main_frame;

    private static final int REQUEST_CHECK_SETTINGS = 1100;

    protected Toolbar toolbar;
    private NetworkManager networkManager;
    private ImageRequestManager imageRequestManager;
    protected static Dialog currentDialog;
    private static String currentDialogTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.networkManager = NetworkManager.newInstance(this, MommaApp.BASE_URL);
        this.imageRequestManager = ImageRequestManager.getInstance(this);

        hideKeyboard(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        ProgressBarWidget appWideCircularProgressBar = (ProgressBarWidget) findViewById(R.id.pageLoadingIndicator).findViewById(R.id.app_wide_circular_progress_bar);
        ProgressBarWidget appWideEmbeddedCircularProgressBar = (ProgressBarWidget) findViewById(R.id.embeddedFragmentLoadingIndicator).findViewById(R.id.app_wide_circular_progress_bar);

        //changeProgressBarDrawableInPreLollipop(this, appWideCircularProgressBar);
        //changeProgressBarDrawableInPreLollipop(this, appWideEmbeddedCircularProgressBar);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "RESUMED");

        ((MommaApp) getApplicationContext()).setCurrentActivity(this);
        hideKeyboard(this);
    }

    @Override
    protected void onPause() {
        clearActivityReferenceInApplication();
        super.onPause();

        Log.d(TAG, "PAUSED");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        setToolbarTitle();

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.onBackPressed();
        } else if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        clearActivityReferenceInApplication();

        super.onDestroy();
    }

    @Override
    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        super.onChildTitleChanged(childActivity, title);
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        break;
                    case Activity.RESULT_CANCELED:

                        break;
                    default:
                        return;
                }
                break;
            default:
                break;
        }

        return;
    }

    public void configureToolbar(int toolbarId, int custom_layout) {
        toolbar = (Toolbar) findViewById(toolbarId);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setCustomView(custom_layout);
    }

    private void setToolbarTitle() {
        View customView = getSupportActionBar().getCustomView();

        TextView textView = (TextView) customView.findViewById(R.id.my_toolbar_title);
        BaseFragment currFragment = getBaseFragmentInContainer();
        if (currFragment != null) {
            animateFadeHide(this, textView);
            textView.setText(currFragment.getToolBarTitle());
            animateFadeShow(this, textView);
        }
    }

    protected void showSplashScreen() {

        //SHOW SPLASH SCREEN
        new SplashFragment().show(getSupportFragmentManager(), SplashFragment.class.getSimpleName());
    }

    public BaseFragment getBaseFragmentInContainer() {
        String activityName = this.getClass().getSimpleName();

        Log.i(TAG, activityName);

        return getFragmentFromCurrentActivity(this);
    }

    public static BaseFragment getFragmentFromCurrentActivity(Context context) {
        BaseFragment currentFragment;
        int containerId = getContainerIdForCurrentActivity(context);

        currentFragment = (BaseFragment) ((BaseActivity) context).getSupportFragmentManager().findFragmentById(containerId);

        return currentFragment;
    }

    public static int getContainerIdForCurrentActivity(Context context) {
        int containerId = BaseActivity.MAIN_ACTIVITY_CONTAINER_ID;

        if (context instanceof MainActivity) {
            containerId = BaseActivity.MAIN_ACTIVITY_CONTAINER_ID;
        }

        return containerId;
    }

    public ImageLoader getImageLoader() {
        return this.imageRequestManager.getImageLoader();
    }

    public static void openAnimatedDialog(Dialog dialog) {
        try {
            String dialogName = dialog.getClass().getSimpleName();
            if (!currentDialogTag.equals(dialogName)) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                dialog.show();
                currentDialog = dialog;
                currentDialogTag = dialog.getClass().getSimpleName();

                Log.i(TAG, "CURRENT DIALOG SHOWN: " + currentDialogTag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetCurrentDialogTag() {

        currentDialog = null;
        currentDialogTag = "";
    }

    private void clearActivityReferenceInApplication() {
        Activity currActivity = ((MommaApp) getApplicationContext()).getCurrentActivity();
        if (this.equals(currActivity)) {
            ((MommaApp) getApplicationContext()).setCurrentActivity(null);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public SqliteHelper getSqliteHelper() {
        return DbSingleton.getInstance();
    }
}