package com.haoshi.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviBaseCallbackModel;
import com.baidu.navisdk.adapter.BaiduNaviCommonModule;
import com.baidu.navisdk.adapter.NaviModuleFactory;
import com.baidu.navisdk.adapter.NaviModuleImpl;
import com.haoshi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoshi
 */

public class NavGuideActivity extends AppCompatActivity {

    private static final String TAG = NavGuideActivity.class.getSimpleName();
    private static final String RET_COMMON_MODULE = "module.ret";
    private static final String KEY_TYPE_KEYCODE = "keyCode";
    private static final String KEY_TYPE_EVENT = "event";
    private static final int METHOD_TYPE_ON_KEY_DOWN = 0x01;
    private static final int MSG_SHOW = 1;
    private static final int MSG_HIDE = 2;
    private static final int MSG_RESET_NODE = 3;
    private double lat = 30.291808;
    private double lng = 120.035844;
    private String addr = "福鼎家园";

    private Handler handler = null;
    private BNRoutePlanNode bnRoutePlanNode = null;
    private BaiduNaviCommonModule baiduNaviCommonModule = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createHandler();
        baiduNaviCommonModule = NaviModuleFactory.getNaviModuleManager().getNaviCommonModule(
                NaviModuleImpl.BNaviCommonModuleConstants.ROUTE_GUIDE_MODULE, this,
                BNaviBaseCallbackModel.BNaviBaseCallbackConstants.CALLBACK_ROUTEGUIDE_TYPE, onNavigationListener);
        baiduNaviCommonModule.onCreate();
        setContentView(baiduNaviCommonModule.getView());
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                bnRoutePlanNode = (BNRoutePlanNode) bundle.getSerializable("routePlanNode");
            }
        }
        //显示自定义图标
        if (handler != null) {
            handler.sendEmptyMessageAtTime(MSG_SHOW, 5000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (baiduNaviCommonModule != null) {
            baiduNaviCommonModule.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (baiduNaviCommonModule != null) {
            baiduNaviCommonModule.onResume();
        }
    }

    protected void onPause() {
        super.onPause();
        if (baiduNaviCommonModule != null) {
            baiduNaviCommonModule.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (baiduNaviCommonModule != null) {
            baiduNaviCommonModule.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (baiduNaviCommonModule != null) {
            baiduNaviCommonModule.onDestroy();
        }
    }

    /**
     * 此处onBackPressed传递false表示强制退出，true表示返回上一级，非强制退出
     */
    @Override
    public void onBackPressed() {
        if (baiduNaviCommonModule != null) {
            baiduNaviCommonModule.onBackPressed(true);
        }
    }

    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (baiduNaviCommonModule != null) {
            baiduNaviCommonModule.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (baiduNaviCommonModule != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_TYPE_KEYCODE, keyCode);
            bundle.putParcelable(KEY_TYPE_EVENT, event);
            baiduNaviCommonModule.setModuleParams(METHOD_TYPE_ON_KEY_DOWN, bundle);
            try {
                Boolean ret = (Boolean) bundle.get(RET_COMMON_MODULE);
                if (ret) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void createHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper()) {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == MSG_SHOW) {
                        addCustomizedLayerItems();
                    } else if (msg.what == MSG_HIDE) {
                        BNRouteGuideManager.getInstance().showCustomizedLayer(false);
                    } else if (msg.what == MSG_RESET_NODE) {
                        BNRouteGuideManager.getInstance().resetEndNodeInNavi(
                                new BNRoutePlanNode(lng, lat, addr, null, BNRoutePlanNode.CoordinateType.GCJ02));
                    }
                }
            };
        }
    }

    private void addCustomizedLayerItems() {
        List<BNRouteGuideManager.CustomizedLayerItem> items = new ArrayList<>();
        BNRouteGuideManager.CustomizedLayerItem item;
        if (bnRoutePlanNode != null) {
            item = new BNRouteGuideManager.CustomizedLayerItem(bnRoutePlanNode.getLongitude(), bnRoutePlanNode.getLatitude(),
                    bnRoutePlanNode.getCoordinateType(), getResources().getDrawable(R.mipmap.ic_launcher),
                    BNRouteGuideManager.CustomizedLayerItem.ALIGN_CENTER);
            items.add(item);

            BNRouteGuideManager.getInstance().setCustomizedLayerItems(items);
        }
        BNRouteGuideManager.getInstance().showCustomizedLayer(true);
    }

    private BNRouteGuideManager.OnNavigationListener onNavigationListener = new BNRouteGuideManager.OnNavigationListener() {

        @Override
        public void onNaviGuideEnd() {
            finish();
        }

        @Override
        public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {

            if (actionType == 0) {
                //导航到达目的地 自动退出
                //LogUtils.d(TAG, "notifyOtherAction actionType = " + actionType + ",导航到达目的地！");
            }

            //LogUtils.d(TAG, "actionType:" + actionType + "arg1:" + arg1 + "arg2:" + arg2 + "obj:" + obj.toString());
        }
    };
}
