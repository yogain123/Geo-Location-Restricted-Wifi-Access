package com.pickachu.momma.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Vaibhav on 22/08/15.
 */
public class VolleyRequestSingleton {

    private static VolleyRequestSingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public static synchronized VolleyRequestSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyRequestSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request request) {
        getRequestQueue().add(request);
    }

    private VolleyRequestSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }
}
