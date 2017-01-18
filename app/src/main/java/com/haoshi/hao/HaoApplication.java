package com.haoshi.hao;

import android.app.Application;

/**
 * @author HaoShi
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
