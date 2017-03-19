package com.im_hero.utils;

import android.content.Context;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import static android.widget.Toast.makeText;

/**
 * @author ItsFated
 * @version 1.0
 */

public final class Toaster {
    public Toaster() { throw new Error("不要实例化此类"); }

    private static Toast toast;

    private static WeakReference<Context> aContext;

    public static void setupContext(Context context) {
        Toaster.aContext = new WeakReference<>(context.getApplicationContext());
        toast = makeText(context, "", Toast.LENGTH_LONG);
    }

    public static void newLong(int resId) {
        show(null, resId, null, Toast.LENGTH_LONG);
    }

    public static void newLong(CharSequence msg) {
        show(null, 0, msg, Toast.LENGTH_LONG);
    }

    public static void newShort(int resId) {
        show(null, resId, null, Toast.LENGTH_SHORT);
    }

    public static void newShort(CharSequence msg) {
        show(null, 0, msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(int resId) {
        show(toast, resId, null, Toast.LENGTH_LONG);
    }

    public static void showLong(CharSequence msg) {
        show(toast, 0, msg, Toast.LENGTH_LONG);
    }

    public static void showShort(int resId) {
        show(toast, resId, null, Toast.LENGTH_SHORT);
    }

    public static void showShort(CharSequence msg) {
        show(toast, 0, msg, Toast.LENGTH_SHORT);
    }

    private static void show(Toast toast, int resId, CharSequence msg, int duration) {
        if (toast == null) {
            Context context = aContext.get();
            if (context != null) {
                if (msg == null) {
                    Toast.makeText(context, resId, duration).show();
                } else {
                    Toast.makeText(context, msg, duration).show();
                }
            }
        } else {
            toast.setDuration(Toast.LENGTH_LONG);
            if (msg == null) {
                toast.setText(resId);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }
}
