package com.pickachu.momma.Network;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

/**
 * Created by vaibhavsinghal on 30/11/15.
 */

public class ImageCacheManager extends LruCache<String, Bitmap> implements
        com.android.volley.toolbox.ImageLoader.ImageCache {

    public ImageCacheManager() {
        this((int) (4 * 1024 * 1024));
    }

    public ImageCacheManager(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        int size = sizeOf(bitmap);
        return size;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    @Override
    public Bitmap getBitmap(String key) {
        Bitmap bitmap = get(key);
        return bitmap;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue,
                                Bitmap newValue) {

        oldValue.recycle();
        super.entryRemoved(evicted, key, oldValue, newValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    protected int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < 12) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }
}