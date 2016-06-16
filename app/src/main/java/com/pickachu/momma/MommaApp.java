package com.pickachu.momma;

import android.app.Application;

import com.pickachu.momma.database.DbSingleton;
import com.pickachu.momma.database.SqliteHelper;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ishaan on 29-Sep-15.
 */

public class MommaApp extends Application {

    private final String TAG = this.getClass().getSimpleName();

    public static final String BASE_URL = "http://api.shadowfax.in//app/";

    private BaseActivity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        //INITIALIZE SQLITE DB
        DbSingleton.initializeInstance(new SqliteHelper(getApplicationContext()));

        //INITIALIZE CALLIGRAPHY
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(getResources().getString(R.string.custom_font_normal))
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        /*if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {

            Log.w(TAG, "APP WENT TO BACKGROUND");

            RiderInfoSharedPreferences.setIsAppInBackground(getApplicationContext(), true);

            Map<String, Object> mapForEvent = new HashMap<>();

            mapForEvent.put("time", DateTimeUtils.currDateTimeInString());
            BaseActivity.createEvent(IntercomEvents.APP_WENT_TO_BACKGROUND_EVENT, mapForEvent);
        }*/
    }

    public BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(BaseActivity mCurrentActivity) {
        this.currentActivity = mCurrentActivity;
    }
}
