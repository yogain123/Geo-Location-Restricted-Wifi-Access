package com.pickachu.momma.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by vaibhavsinghal on 16/11/15.
 */
public class AppInfoUtils {


    /**
     * Called for getting app version name
     *
     * @param context Context where app version is required
     * @return String with app version name
     */
    public static String getAppVersionName(Context context) {

        PackageManager pm = context.getPackageManager();
        PackageInfo pInfo;
        String versionName = "";

        try {
            pInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    /**
     * Called for getting app version code
     *
     * @param context Context where app version code is required
     * @return int with app version code
     */
    public static int getAppVersionCode(Context context) {

        PackageManager pm = context.getPackageManager();
        PackageInfo pInfo;
        int versionCode = 0;

        try {
            pInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;

    }

    /**
     * Called for getting package name
     *
     * @param context Context where app package name is required
     * @return String with package name
     */
    public static String getPackageName(Context context) {

        PackageManager pm = context.getPackageManager();
        PackageInfo pInfo;
        String packageName = "";

        try {
            pInfo = pm.getPackageInfo(context.getPackageName(), 0);
            packageName = pInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return packageName;
    }

    public static void printCurrentThreadName(Object callingClass) {
        Log.w(callingClass.getClass().getSimpleName(), "CURRENT THREAD: " + Thread.currentThread().getName());
        Log.w(callingClass.getClass().getSimpleName(), "CURRENT THREAD ID: " + Thread.currentThread().getId());
    }
}

