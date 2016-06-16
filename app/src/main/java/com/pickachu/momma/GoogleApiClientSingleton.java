package com.pickachu.momma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by vaibhavsinghal on 13/01/16.
 */
public class GoogleApiClientSingleton {

    private static final String TAG = GoogleApiClientSingleton.class.getSimpleName();

    private static Context contextForClient;
    private static GoogleApiClient googleApiClient;

    private GoogleApiClientSingleton() {
    }

    public static GoogleApiClient getInstance(Context context) {

        contextForClient = context;
        if(googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(locationApiClientConnectionCallbacks)
                    .addOnConnectionFailedListener(locationApiClientConnectionFailedListener)
                    .addApi(LocationServices.API)
                    .build();

            connectGoogleLocationApiClient();
        }

        return googleApiClient;
    }

    private static void connectGoogleLocationApiClient() {

        Log.w(TAG, "GOOGLE API (FOR LOCATIONS) CONNECTING INIT.");
        googleApiClient.connect();
    }

    private static GoogleApiClient.ConnectionCallbacks locationApiClientConnectionCallbacks =  new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {

            Log.w(TAG, "GOOGLE API (FOR LOCATIONS) CONNECTED.");
        }

        @Override
        public void onConnectionSuspended(int i) {

            Log.e(TAG, "GOOGLE API CONNECTION (FOR LOCATIONS) SUSPENDED.");
            connectGoogleLocationApiClient();
        }
    };

    private static GoogleApiClient.OnConnectionFailedListener locationApiClientConnectionFailedListener =  new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Log.d(TAG, "Connection Failed: " + connectionResult);
            //problem with Play Services
            Intent serviceStartIntent = new Intent("neigh");
            serviceStartIntent.putExtra("play services error", connectionResult.getErrorCode());
            LocalBroadcastManager.getInstance(contextForClient).sendBroadcast(serviceStartIntent);
        }
    };
}
