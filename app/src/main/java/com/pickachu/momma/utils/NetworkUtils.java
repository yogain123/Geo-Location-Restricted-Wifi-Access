package com.pickachu.momma.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * Created by vaibhavsinghal on 4/11/15.
 */
public class NetworkUtils {

    public static final String TAG = NetworkUtils.class.getSimpleName();
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_NOT_CONNECTED = 0;
    public static final int NETWORK_STAUS_WIFI = 1;
    public static final int NETWORK_STATUS_MOBILE = 2;

    public static boolean isInternetReachable() {
        try {
            Log.w(TAG, "PINGING GOOGLE TO CHECK INTERNET CONNECTIVITY");
            Process pingProcess = Runtime.getRuntime().exec("ping -c 1 google.com");
            boolean reachable = pingProcess.waitFor() == 0;

            if (reachable) {
                Log.i(TAG, "Internet access available");
                return true;
            }
        } catch (Exception e) {
            Log.w(TAG, "EXCEPTION IN PINGING GOOGLE");
            e.printStackTrace();
        }

        Log.i(TAG, "Internet access unavailable");
        return false;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    public static String getDeviceInternetSource(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

            return activeNetwork.getTypeName();
        } else {

            return activeNetwork.getSubtypeName();
        }
    }

    /*
        Check if the internet is enabled by user or not
         */
    public static boolean isConnectivityEnabledByUser(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /*
        @Ishaan
         */
    public static String getNetworkType(Context context) {
        TelephonyManager teleMan = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = teleMan.getNetworkType();
        return getNetworkTypeStringified(networkType);
    }

    public static String getNetworkTypeStringified(int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "eHRPD";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO rev. B";
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDen";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "Unknown";
        }
        throw new RuntimeException("New type of network");
    }

    public static String getNetworkStateStringified(int state) {
        String stringifiedNetworkState = "";

        switch (state) {
            case TelephonyManager.DATA_DISCONNECTED:
                stringifiedNetworkState = "DISCONNECTED";
                break;
            case TelephonyManager.DATA_CONNECTING:
                stringifiedNetworkState = "CONNECTING";
                break;
            case TelephonyManager.DATA_CONNECTED:
                stringifiedNetworkState = "CONNECTED";
                break;
            case TelephonyManager.DATA_SUSPENDED:
                stringifiedNetworkState = "SUSPENDED";
                break;
            default:
                stringifiedNetworkState = "UNKNOWN " + state;
                break;
        }

        return stringifiedNetworkState;
    }
}
