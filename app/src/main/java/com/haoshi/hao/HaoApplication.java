package com.haoshi.hao;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.alipay.euler.andfix.patch.PatchManager;
import com.baidu.mapapi.SDKInitializer;
import com.haoshi.utils.LogUtils;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.io.File;
import java.io.IOException;

import cn.sharesdk.framework.ShareSDK;

/**
 * @author HaoShi
 */

@DefaultLifeCycle(
        application = "com.haoshi.Application",
        flags = ShareConstants.TINKER_ENABLE_ALL
)
public class HaoApplication extends DefaultApplicationLike {

    private static final String TAG = HaoApplication.class.getSimpleName();

    private static Application application;

    public HaoApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent, Resources[] resources, ClassLoader[] classLoader, AssetManager[] assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent, resources, classLoader, assetManager);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        application = getApplication();
        //初始化百度地图
        SDKInitializer.initialize(application);
        //初始化Tinker
        TinkerInstaller.install(this);
        //初始化AndFix
        andFix();
    }

    public static Application getInstance() {
        return application;
    }

    private void andFix() {
        try {
            PatchManager patchManager = new PatchManager(application);
            patchManager.init("1.0");
            LogUtils.d(TAG, "patch manager init");
            patchManager.loadPatch();
            LogUtils.d(TAG, "patch load");

            String patchPath = Constant.SD_PATH + "/patch.apatch";
            File patchFile = new File(patchPath);
            if (!patchFile.exists()) {
                return;
            }
            patchManager.addPatch(patchPath);
            File file = new File(application.getFilesDir(), "apatch/patch.apatch");
            if (!file.exists()) {
                return;
            }
            boolean result = patchFile.delete();
            if (!result) {
                LogUtils.d(TAG, patchPath + " delete fail");
            }
        } catch (IOException e) {
            LogUtils.e(TAG, e.toString());
        }
    }
}
