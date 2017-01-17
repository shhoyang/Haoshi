package com.haoshi.utils;

import android.util.Log;

/**
 * @author: HaoShi
 */
public class L {

    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void i(String tag, int msg) {
        if (isDebug)
            Log.i(tag, msg + "");
    }

    public static void d(String tag, int msg) {
        if (isDebug)
            Log.d(tag, msg + "");
    }

    public static void e(String tag, int msg) {
        if (isDebug)
            Log.e(tag, msg + "");
    }

    public static void v(String tag, int msg) {
        if (isDebug)
            Log.v(tag, msg + "");
    }
}