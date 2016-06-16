package com.pickachu.momma.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by vaibhavsinghal on 3/11/15.
 */
public class ServiceUtils {

    private final static String TAG = ServiceUtils.class.getSimpleName();

    public static void startServiceWithRunCheck(Context context, Class<?> serviceClass) {
        if (!isMyServiceRunning(context, serviceClass)) {
            Log.w(TAG, "STARTING " + serviceClass.getSimpleName() + " " + "FROM" + " " + context.getClass().getSimpleName());
            /*****START  APP SERVICE******/
            Intent serviceIntent = new Intent(context, serviceClass);
            context.startService(serviceIntent);
            /*********************************************/
        }
    }

    public static void startServiceWithRunCheck(Context context, Class<?> serviceClass, Intent startServiceIntent) {
        if (!isMyServiceRunning(context, serviceClass)) {
            /*****START  APP SERVICE******/
            context.startService(startServiceIntent);
            /*********************************************/
        }
    }

    public static void stopServiceWithRunCheck(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                context.stopService(new Intent(context, serviceClass));
            }
        }
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
