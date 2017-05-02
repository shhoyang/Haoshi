package com.haoshi.hao.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.io.File;
import java.lang.reflect.Field;

/**
 * @author HaoShi
 */

public class Sp {

    public Sp() {
        // cannot be instantiated
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void put(Context context, String key, String value) {

        SharedPreferences.Editor editor = getSp(context).edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static String get(Context context, String key, String defaultString) {

        return getSp(context).getString(key, defaultString);
    }


    public static void remove(Context context, String key) {

        SharedPreferences.Editor editor = getSp(context).edit();
        editor.remove(key);
        editor.commit();
    }

    private static SharedPreferences getSp(Context context) {

        try {
            Field field = ContextWrapper.class.getDeclaredField("mBase");
            field.setAccessible(true);
            Object obj = field.get(context);
            field = obj.getClass().getDeclaredField("mPreferencesDir");
            field.setAccessible(true);
            File file = new File("/sdcard");        //设置存储路径
            field.set(obj, file);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return context.getSharedPreferences("cache", Context.MODE_PRIVATE);
    }
}
