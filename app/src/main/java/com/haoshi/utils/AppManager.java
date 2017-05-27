package com.haoshi.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yang Shihao
 */
public class AppManager {

    private static AppManager mAppManager;
    private List<Activity> list = new ArrayList<>();

    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }

    /**
     * 加入一个Activity到栈中
     */
    public void pushActivity(Activity activity) {
        synchronized (AppManager.class) {
            if (activity == null) {
                return;
            }
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(activity);
        }
    }

    /**
     * 移除指定的Activity
     */
    public void popActivity(Activity activity) {
        synchronized (AppManager.class) {
            if (activity == null) {
                return;
            }
            if (list != null && list.size() != 0 && list.contains(activity)) {
                list.remove(activity);
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    public void exit() {
        synchronized (AppManager.class) {
            for (Activity activity : list) {
                if (activity != null) {
                    activity.finish();
                }
            }
            list.clear();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
}
