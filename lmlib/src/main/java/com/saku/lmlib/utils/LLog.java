package com.saku.lmlib.utils;

import android.util.Log;

/**
 * Created by liumin on 2017/9/2.
 */

public class LLog {
    private static boolean sIsAllowed;

    public static void setAllowLog(boolean isAllowed) {
        sIsAllowed = isAllowed;
    }

    public static void v(String tag, String msg) {
        if(sIsAllowed) {
            Log.v(tag, msg);
        }
    }

    public static void v(String msg) {
        if(sIsAllowed) {
            Log.v("lm", msg);
        }
    }
    public static void d(String tag, String msg) {
        if(sIsAllowed) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if(sIsAllowed) {
            Log.d("lm", msg);
        }
    }

    public static void i(String tag, String msg) {
        if(sIsAllowed) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if(sIsAllowed) {
            Log.i("lm", msg);
        }
    }

    public static void w(String tag, String msg) {
        if(sIsAllowed) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        if(sIsAllowed) {
            Log.w("lm", msg);
        }
    }

    public static void e(String tag, String msg) {
        if(sIsAllowed) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if(sIsAllowed) {
            Log.e("lm", msg);
        }
    }

}
