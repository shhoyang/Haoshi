package com.haoshi.hao;

import android.app.Application;

/**
 * Created by Haoshi on 2017/1/7.
 */

public class HaoApplication extends Application {

    private static HaoApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static HaoApplication getInstance() {
        return application;
    }
}
