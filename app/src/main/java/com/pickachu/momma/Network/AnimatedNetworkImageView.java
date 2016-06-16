package com.pickachu.momma.Network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.pickachu.momma.R;

/**
 * Created by vaibhav on 30/11/15.
 */
public class AnimatedNetworkImageView extends NetworkImageView {

    private static final int FADE_IN_MS = 500;

    public AnimatedNetworkImageView(Context context) {
        super(context);
    }

    public AnimatedNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageUrl(String url, ImageLoader imageLoader) {
        try {
            super.setImageUrl(url, imageLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageUrl(String url, ImageLoader imageLoader, int backupDrawable) {
        try {
            super.setErrorImageResId(backupDrawable);
            super.setDefaultImageResId(backupDrawable);
            super.setImageUrl(url, imageLoader);
        } catch (Exception e) {
            e.printStackTrace();
            setImageResource(backupDrawable);
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{
                new ColorDrawable(ContextCompat.getColor(getContext(), R.color.transparent)),
                new BitmapDrawable(getContext().getResources(), bm)
        });

        setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        setImageDrawable(transitionDrawable);
        transitionDrawable.setCrossFadeEnabled(true);
        transitionDrawable.startTransition(FADE_IN_MS);
        /*BitmapDrawable bd;
        if(bm != null) {
            bd = new BitmapDrawable(RoundImage.getRoundBitmap(bm));
        }
        else {
            bd = new BitmapDrawable(bm);
        }
        setImageDrawable(bd);*/
    }
}
