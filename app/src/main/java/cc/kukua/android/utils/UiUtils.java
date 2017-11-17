package cc.kukua.android.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cc.kukua.android.KukuaApp;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author Ilo Calistus
 */

public class UiUtils {


    // Suppress default constructor for non-instantiability
    private UiUtils() {
        throw new AssertionError();
    }

    private static String TAG = UiUtils.class.getSimpleName();

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void showSafeToast(final String toastMessage) {
        runOnMain(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(KukuaApp.getInstance(), toastMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void runOnMain(final @NonNull Runnable runnable) {
        if (isMainThread()) runnable.run();
        else handler.post(runnable);
    }

    public static void showSnackbar(String message, View v) {
        final Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void blinkView(View mView) {
        try {
            Animation mFadeInFadeIn = getAnimation(KukuaApp.getInstance(), android.R.anim.fade_in);
            mFadeInFadeIn.setRepeatMode(Animation.REVERSE);
            animateView(mView, mFadeInFadeIn);
        } catch (IllegalStateException | NullPointerException ignored) {

        }
    }

    private static Animation getAnimation(Context context, int animationId) {
        return AnimationUtils.loadAnimation(context, animationId);
    }

    private static synchronized void animateView(View view, Animation animation) {
        if (view != null) {
            view.startAnimation(animation);
        }
    }

    private static ProgressDialog operationsProgressDialog;

    public static void showProgressDialog(final Context context, final String message) {
        runOnMain(new Runnable() {
            @Override
            public void run() {
                operationsProgressDialog = new ProgressDialog(context);
                operationsProgressDialog.setMessage(message);
                operationsProgressDialog.show();
            }
        });
    }

    public static void dismissAllProgressDialogs() {
        runOnMain(new Runnable() {
            @Override
            public void run() {
                try {
                    if (operationsProgressDialog != null) {
                        if (operationsProgressDialog.isShowing()) {
                            operationsProgressDialog.dismiss();
                        }
                    }
                } catch (WindowManager.BadTokenException e) {
                    LogUtils.log(TAG, "Dialog has leaked state" + e.getMessage());
                }
            }
        });
    }

    public static void toggleViewVisibility(View view, boolean show) {
        if (view != null) {
            if (show) {
                if (view.getVisibility() != View.VISIBLE) {
                    view.setVisibility(View.VISIBLE);
                    view.invalidate();
                }
            } else {
                if (view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                    view.invalidate();
                }
            }
        }
    }

    public static void loadImage(final Activity context, int placeHolder, final String photoPath, final ImageView imageView) {
        if (imageView != null) {
            if (isNotEmpty(photoPath)) {
                if (context != null) {
                    if (!context.isDestroyed()) {
                        Glide.with(context).load(photoPath).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeHolder).error(placeHolder).crossFade().into(imageView);
                        imageView.invalidate();
                    }
                }
            }
        }
    }

    public static void loadImage(final Activity context, int placeHolder, final Uri photoPath, final ImageView imageView) {
        if (imageView != null) {
            if (photoPath != null) {
                if (context != null) {
                    if (!context.isDestroyed()) {
                        Glide.with(context).load(photoPath).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeHolder).error(placeHolder).crossFade().into(imageView);
                        imageView.invalidate();
                    }
                }
            }
        }
    }

}
