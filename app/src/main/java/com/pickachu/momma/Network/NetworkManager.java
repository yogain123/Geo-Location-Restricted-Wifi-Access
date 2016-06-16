package com.pickachu.momma.Network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pickachu.momma.BaseActivity;
import com.pickachu.momma.R;
import com.pickachu.momma.adapters.BaseRecyclerAdapter;
import com.pickachu.momma.fragments.BaseForTabbedFragment;
import com.pickachu.momma.fragments.BaseFragment;
import com.pickachu.momma.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.pickachu.momma.utils.StringUtils.getTextFromStringResource;
import static com.pickachu.momma.utils.StringUtils.isValidString;
import static com.pickachu.momma.utils.UIUtils.getColorRedId;
import static com.pickachu.momma.utils.UIUtils.hideEmbeddedLoadingIndicator;
import static com.pickachu.momma.utils.UIUtils.hidePageLoadingIndicator;

/**
 * Created by vaibhavsinghal on 3/11/15.
 */
public class NetworkManager implements RequestQueue.RequestFilter {

    public static final String TAG = "NETWORK MANAGER";
    private static final int HTTP_TIMEOUT_MS = 10000; //10 seconds
    private static final int RETRY_COUNT = 0;

    private Context context;
    private static RequestQueue requestQueue;
    private static String BASE_URL;
    private static String lastUrl;

    @Override
    public boolean apply(Request<?> request) {
        return request.getTag() == this;
    }


    private NetworkManager(Context context, String savedBaseUrl) {
        if (requestQueue == null) {
            this.context = context;
            BASE_URL = savedBaseUrl;
            lastUrl = "";
            requestQueue = Volley.newRequestQueue(context, null);
            requestQueue.start();
        }
    }

    public static NetworkManager newInstance(Context context, String baseUrl) {
        NetworkManager instance = new NetworkManager(context, baseUrl);
        return instance;
    }

    public Request<?> jsonGETRequest(Context context, Object requestListener, String subUrl, Listener<JSONObject> listener, ErrorListener errorListener, RetryPolicy defaultRetryPolicy, boolean shouldCache) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL + subUrl, listener, errorListener);
        return jsonRequest(context, request, new JSONObject(), requestListener.getClass().getSuperclass(), defaultRetryPolicy, shouldCache);
    }

    public Request<?> jsonPUTRequest(Context context, Object requestListener, String subUrl, JSONObject object, Listener<JSONObject> listener, ErrorListener errorListener, RetryPolicy defaultRetryPolicy, boolean shouldCache) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, BASE_URL + subUrl, object, listener, errorListener);
        return jsonRequest(context, request, object, requestListener.getClass().getSuperclass(), defaultRetryPolicy, shouldCache);
    }

    public Request<?> jsonPOSTRequest(Context context, Object requestListener, String subUrl, JSONObject object, Listener<JSONObject> listener, ErrorListener errorListener, RetryPolicy defaultRetryPolicy, boolean shouldCache) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + subUrl, object, listener, errorListener);
        return jsonRequest(context, request, object, requestListener.getClass().getSuperclass(), defaultRetryPolicy, shouldCache);
    }

    public Request<?> jsonRequest(Context context, JsonObjectRequest request, JSONObject params, Class<?> requestTag, RetryPolicy defaultRetryPolicy, boolean shouldCache) {

        //lastUrl = request.getUrl();

        request.setShouldCache(shouldCache);
        //request.setTag(this);
        request.setTag(requestTag);
        request.setRetryPolicy(defaultRetryPolicy);

        if (NetworkUtils.isConnectivityEnabledByUser(context.getApplicationContext())) {
            try {
                Log.i(TAG, "-------------------------");
                Log.i(TAG, "REQUEST HEADERS: " + request.getHeaders().toString());
                Log.i(TAG, "REQUEST URL: " + request.getUrl().toString());
                Log.i(TAG, "REQUEST PARAMS: " + params.toString());
                Log.i(TAG, "REQUEST ID: " + request.getIdentifier());
                Log.i(TAG, "-------------------------");
            } catch (AuthFailureError authFailureError) {
                authFailureError.printStackTrace();
            }

            addRequest(request);
        } else {

            hideEmbeddedLoadingIndicator(context);
            hidePageLoadingIndicator(context);

            //NoConnectionSnackbar.show(context);
            request.getErrorListener().onErrorResponse(new NoConnectionError());
        }
        return request;
    }

    public void addRequest(Request<?> request) {
        requestQueue.add(request);
    }

    public void cancel() {

        Log.i(TAG, "-------------------------");
        Log.i(TAG, "LAST PINGED URL: " + lastUrl);
        Log.i(TAG, "REQUEST QUEUE CACHE: " + requestQueue.getCache().get(lastUrl));

        requestQueue.cancelAll(this);
        requestQueue.getCache().clear();

        Log.i(TAG, "REQUEST QUEUE CACHE AFTER CLEAR: " + requestQueue.getCache().get(lastUrl));
        Log.i(TAG, "-------------------------");

    }

    public void cancelFragmentRequests() {

        hideEmbeddedLoadingIndicator(context);
        hidePageLoadingIndicator(context);

        Log.i(TAG, "-------------------------");
        Log.i(TAG, "LAST PINGED URL: " + lastUrl);
        Log.i(TAG, "REQUEST QUEUE CACHE: " + requestQueue.getCache().get(lastUrl));

        requestQueue.cancelAll(BaseFragment.class);
        requestQueue.cancelAll(BaseForTabbedFragment.class);
        requestQueue.cancelAll(BaseRecyclerAdapter.class);
        requestQueue.getCache().clear();

        Log.i(TAG, "REQUEST QUEUE CACHE AFTER CLEAR: " + requestQueue.getCache().get(lastUrl));
        Log.i(TAG, "-------------------------");

    }


    public static void handleSuccessResponseFromService(JSONObject response) {
        Log.i(TAG, "-------------------------");
        Log.i(TAG, "NETWORK RESPONSE: " + response.toString());
        Log.i(TAG, "-------------------------");

        return;
    }


    public static void handleSuccessResponse(Context context, JSONObject response) {
        Log.i(TAG, "-------------------------");
        Log.i(TAG, "NETWORK RESPONSE: " + response.toString());
        Log.i(TAG, "-------------------------");

        hideEmbeddedLoadingIndicator(context);
        hidePageLoadingIndicator(context);

        return;
    }

    public static void handleErrorResponse(Context context, VolleyError error) {
        String errorMessage = "";
        int snackBarLength = Snackbar.LENGTH_LONG;
        int textColorId = R.color.orange;

        hideEmbeddedLoadingIndicator(context);
        hidePageLoadingIndicator(context);

        if (error instanceof TimeoutError) {

            textColorId = R.color.light_teal;
            errorMessage = getTextFromStringResource(context, R.string.network_timeout);
            showNetworkErrorInSnackBar(context, errorMessage, snackBarLength, textColorId);
        } else if (error instanceof ServerError) {

            if (error.networkResponse != null && isValidString(new String(error.networkResponse.data))) {

                errorMessage = getErrorMessageFromResponse(error.networkResponse.data);
                textColorId = R.color.toolbar_color;
            }

            if (!isValidString(errorMessage)) {

                errorMessage = getTextFromStringResource(context, R.string.server_error);
            }

            showNetworkErrorInSnackBar(context, errorMessage, snackBarLength, textColorId);
        } else if (error instanceof AuthFailureError) {

            errorMessage = getTextFromStringResource(context, R.string.server_error);
            showNetworkErrorInSnackBar(context, errorMessage, snackBarLength, textColorId);
        }

        printErrorResponse(error);

        return;
    }

    public static Snackbar showNetworkErrorInSnackBar(final Context context, final String errorMessage, final int snackBarLength, final int textColorId) {
        Snackbar snackbar = null;

        if (context instanceof BaseActivity) {
            Log.i(TAG, "NETWORK ERROR IN SNACKBAR DISPLAYED FROM: " + context.getClass().getSimpleName());

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if (context instanceof BaseActivity) {
                        try {
                            View fragmentView = BaseActivity.getFragmentFromCurrentActivity(context).getView();
                            Snackbar snackbar = Snackbar.make(fragmentView, errorMessage, snackBarLength);
                            View snackBarView = snackbar.getView();
                            TextView snackBarTextView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);

                            snackBarTextView.setTextColor(getColorRedId(context, textColorId));
                            snackBarView.setBackgroundColor(getColorRedId(context, R.color.black));
                            snackbar.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        return snackbar;
    }

    public static void printErrorResponse(VolleyError error) {
        Log.i(TAG, "-------------------------");
        Log.e(TAG, "NETWORK ERROR RESPONSE: " + error.toString());

        /*********PRINT HTTP ERROR CODES*********/
        if (error.networkResponse != null) {
            Log.i(TAG, "HTTP ERROR CODE: " + error.networkResponse.statusCode);
            Log.e(TAG, "NETWORK ERROR MESSAGE: " + getErrorMessageFromResponse(error.networkResponse.data));
        }
        /****************************************/
        Log.i(TAG, "-------------------------");
    }

    public static DefaultRetryPolicy getDefaultPolicyForTransactions() {
        return new DefaultRetryPolicy(HTTP_TIMEOUT_MS, 0, 0);
    }

    public static DefaultRetryPolicy getDefaultPolicyForNonTransactions() {
        return new DefaultRetryPolicy(HTTP_TIMEOUT_MS, 2, 2);
    }

    public static String getErrorMessageFromResponse(byte[] byteArrayErrorMessage) {

        String serverErrorMessage = "";
        try {
            serverErrorMessage = new JSONObject(new String(byteArrayErrorMessage)).optString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return serverErrorMessage;
    }
}
