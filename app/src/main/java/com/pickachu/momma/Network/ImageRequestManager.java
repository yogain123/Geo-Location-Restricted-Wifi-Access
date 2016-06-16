package com.pickachu.momma.Network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by vaibhavsinghal on 30/11/15.
 */
public class ImageRequestManager {

    private static ImageRequestManager instance;

    private ImageLoader imageLoader;
    private RequestQueue queue;

    private ImageRequestManager(Context context) {

        this.queue = Volley.newRequestQueue(context, null);
        this.imageLoader = new ImageLoader(queue, new ImageCacheManager());
        this.queue.start();
    }

    public static ImageRequestManager getInstance(Context context) {
        if (instance == null) {
            synchronized (ImageCacheManager.class) {
                if (instance == null) {
                    instance = new ImageRequestManager(context);
                }
            }
        }
        return instance;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public RequestQueue getQueue() {
        return queue;
    }
}
