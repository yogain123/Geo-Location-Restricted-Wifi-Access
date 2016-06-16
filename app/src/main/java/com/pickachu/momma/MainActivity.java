package com.pickachu.momma;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pickachu.momma.fragments.BaseFragment;

public class MainActivity extends BaseActivity {

    public static int dialogNum;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialogNum = 0;

        showSplashScreen();

        setContentView(R.layout.activity_main);
        //DISABLE WHITE SCREEN ON APP LAUNCH
        getWindow().setBackgroundDrawable(null);

        configureToolbar();

        navigationView.setCheckedItem(R.id.nav_orders);
    }

    private void configureToolbar() {
        super.configureToolbar(R.id.toolbar, R.layout.custom_toolbar_main);

        View customViewForToolbar = getSupportActionBar().getCustomView();

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Resetting drawer on close to default drawer_view
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.drawer_view);

                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer opens
                super.onDrawerOpened(drawerView);
                //getCachedDp(MainActivity.this, profilePicImageView);
            }
        };
    }

    private boolean handleNavigationDrawerClicks(MenuItem menuItem) {

        //Checking if the item is in checked state or not, if not make it in checked state
        if (!menuItem.isChecked())
            menuItem.setChecked(true);

        //Closing drawer on item click
        drawerLayout.closeDrawers();

        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {
            case R.id.nav_orders:
                BaseFragment.addToBackStackFromNavigationDrawer(this, BaseFragment.newInstance());
                return true;

            default:

        }

        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        /*if (hasFocus) {}*/
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BaseFragment.replaceStack(this, BaseFragment.newInstance());

        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "RESUMED");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "PAUSED");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "DESTROYED");

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        boolean inflationStatus = super.onCreateOptionsMenu(menu);

        return inflationStatus;
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
        // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        getFragmentFromCurrentActivity(this).onActivityResult(requestCode, resultCode, intent);
        return;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
            return;
        }

        super.onBackPressed();
    }
}