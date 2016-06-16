package com.pickachu.momma.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.pickachu.momma.BaseActivity;
import com.pickachu.momma.R;

import java.io.File;

/**
 * Created by vaibhavsinghal on 3/11/15.
 */
public class UIUtils {

    private static final String TAG = UIUtils.class.getSimpleName();

    public static void hideKeyboard(Context context) {
        if (context != null && ((Activity) context).getCurrentFocus() != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showPageLoadingIndicator(Context context) {
        if (context != null && context instanceof BaseActivity) {
            animateFadeShow(context, ((BaseActivity) context).findViewById(R.id.pageLoadingIndicator));
        }
    }

    public static void hidePageLoadingIndicator(Context context) {
        if (context != null && context instanceof BaseActivity) {
            animateFadeHide(context, ((BaseActivity) context).findViewById(R.id.pageLoadingIndicator));
        }
    }

    public static void showEmbeddedLoadingIndicator(Context context) {
        if (context != null && context instanceof BaseActivity) {
            animateFadeShow(context, ((BaseActivity) context).findViewById(R.id.embeddedFragmentLoadingIndicator));
        }
    }

    public static void hideEmbeddedLoadingIndicator(Context context) {
        if (context != null && context instanceof BaseActivity) {
            animateFadeHide(context, ((BaseActivity) context).findViewById(R.id.embeddedFragmentLoadingIndicator));
        }
    }

    public static void showToolbarShadow(BaseActivity baseActivity) {
        if (baseActivity != null) {
            baseActivity.findViewById(R.id.toolbarShadowLayout).setVisibility(View.VISIBLE);
        }
    }

    public static void hideToolbarShadow(BaseActivity baseActivity) {
        if (baseActivity != null) {
            baseActivity.findViewById(R.id.toolbarShadowLayout).setVisibility(View.INVISIBLE);
        }
    }

    public static int getColorRedId(Context context, int colorCode) {
        return ContextCompat.getColor(context, colorCode);
    }

    public static Drawable getDrawable(Context context, int drawableCode) {
        return ContextCompat.getDrawable(context, drawableCode);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics;
    }

    public static double getScreenDensity(Context context) {
        double density = getDisplayMetrics(context).density;
        return density;
    }

    public static String getScreenType(Context context) {
        String screenType = "";
        double density = getScreenDensity(context);
        if (density >= 4.0) {
            screenType = "xxxhdpi";
        } else if (density >= 3.0 && density < 4.0) {
            screenType = "xxhdpi";
        } else if (density >= 2.0) {
            screenType = "xhdpi";
        } else if (density >= 1.5 && density < 2.0) {
            screenType = "hdpi";
        } else if (density >= 1.0 && density < 1.5) {
            screenType = "mdpi";
        } else {
            screenType = "ldpi";
        }

        return screenType;
    }

    public static void animateImageAddition(Bitmap imageBitmap, ImageView imageView) {
        //imageView.setImageBitmap(null);
        imageView.setAlpha(0f);
        imageView.setImageBitmap(imageBitmap);

        imageView.animate().alpha(1f).setDuration(500);
    }

    public static void animateFlipHide(View view) {
        animateFlipShow(view);
        view.animate().scaleY(0).alpha(0f).setDuration(200);
        view.setVisibility(View.GONE);
    }

    public static void animateFlipShow(View view) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);

        view.setScaleY(0);
        view.animate().scaleY(1).alpha(1f).setDuration(200);
    }

    public static void animateSlideDownHide(Context context, View view) {
        if (view.getVisibility() == View.VISIBLE) {
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down_hide);
            slideDown.setDuration(500);
            view.startAnimation(slideDown);
            view.setVisibility(View.GONE);
        }
    }

    public static void animateSlideUpShow(Context context, View view) {
        if (view.getVisibility() != View.VISIBLE) {
            Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up_show);
            slideUp.setDuration(500);
            view.startAnimation(slideUp);
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void animateSlideUpHide(Context context, View view) {
        if (view.getVisibility() == View.VISIBLE) {
            Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up_hide);
            slideUp.setDuration(500);
            view.startAnimation(slideUp);
            view.setVisibility(View.GONE);
        }
    }

    public static void animateSlideDownShow(Context context, View view) {
        if (view.getVisibility() != View.VISIBLE) {
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down_show);
            slideDown.setDuration(500);
            view.startAnimation(slideDown);
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void animateFadeHide(Context context, View view) {
        if (view.getVisibility() == View.VISIBLE) {
            Animation animFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);

            view.startAnimation(animFadeOut);
            view.setVisibility(View.GONE);
        }
    }

    public static void animateFadeShow(Context context, View view) {
        if (view.getVisibility() != View.VISIBLE) {
            Animation animFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);

            view.startAnimation(animFadeIn);
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void animateBounceHide(Context context, View view) {
        if (view.getVisibility() == View.VISIBLE) {
            Animation bounceHide = AnimationUtils.loadAnimation(context, R.anim.bounce_hide);

            view.startAnimation(bounceHide);
            view.setVisibility(View.GONE);
        }
    }

    public static void animateBounceShow(Context context, View view) {
        if (view.getVisibility() != View.VISIBLE) {
            Animation bounceShow = AnimationUtils.loadAnimation(context, R.anim.bounce_show);

            view.startAnimation(bounceShow);
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Called for getting Round Shape Bitmap
     *
     * @param bitmap Bitmap fo which round shape is required
     * @return Bitmap rounded bitmap
     */
    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static View inflateViewFromResource(Context context, int viewLayoutId) {

        return LayoutInflater.from(context).inflate(viewLayoutId, null, false);
    }
}
