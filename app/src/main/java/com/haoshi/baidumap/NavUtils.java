package com.haoshi.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.haoshi.hao.Constant;
import com.haoshi.utils.LogUtils;
import com.haoshi.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoshi
 */

public class NavUtils {

    private static final String TAG = NavUtils.class.getSimpleName();
    private static final String MAP_FOLDER_NAME = "map";
    private BaiduMapActivity activity;
    private String authInfo;
    private boolean hasInitSuccess = false;

    public NavUtils(BaiduMapActivity activity) {
        this.activity = activity;
        if (initDirs()) {
            initNavi();
        }
    }

    public void routeplanToNavi(BDLocation bdLocation, PoiDetailResult poiDetailResult) {
        if (!hasInitSuccess) {
            ToastUtils.showShort(activity, "未初始化!");
            return;
        }
        BNRoutePlanNode startNode = new BNRoutePlanNode(bdLocation.getLongitude(), bdLocation.getLatitude(), bdLocation.getAddrStr(), null, BNRoutePlanNode.CoordinateType.BD09LL);
        BNRoutePlanNode endNode = new BNRoutePlanNode(poiDetailResult.getLocation().longitude, poiDetailResult.getLocation().latitude, poiDetailResult.getAddress(), null, BNRoutePlanNode.CoordinateType.BD09LL);

        if (startNode != null && endNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<>();
            list.add(startNode);
            list.add(endNode);
            BaiduNaviManager.getInstance().launchNavigator(activity, list, 1, true, new HaoRoutePlanListener(startNode));
        }
    }

    private boolean initDirs() {

        File f = new File(Constant.ROOT_DIR, MAP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdirs();
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    private void initNavi() {

        BaiduNaviManager.getInstance().init(activity, Constant.ROOT_DIR, MAP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    //authInfo = "key校验成功!";
                } else {
                    authInfo = "key校验失败, " + msg;
                }
                activity.runOnUiThread(() -> ToastUtils.showShort(activity, authInfo));
            }

            @Override
            public void initSuccess() {
                //ToastUtils.showShort(activity, "百度导航引擎初始化成功");
                hasInitSuccess = true;
                initSetting();
            }

            @Override
            public void initStart() {
                //ToastUtils.showShort(activity, "百度导航引擎初始化开始");
            }

            @Override
            public void initFailed() {
                ToastUtils.showShort(activity, "百度导航引擎初始化失败");
            }

        }, null, ttsHandler, ttsPlayStateListener);
    }

    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    LogUtils.d(TAG,"TTSPlayMsgType.PLAY_START_MSG");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    LogUtils.d(TAG,"TTSPlayMsgType.PLAY_END_MSG");
                    break;
                }
            }
        }
    };

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            LogUtils.d(TAG,"playEnd");
        }

        @Override
        public void playStart() {
            LogUtils.d(TAG,"playStart");
        }
    };

    private void initSetting() {
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
        BNaviSettingManager.setIsAutoQuitWhenArrived(true);
        Bundle bundle = new Bundle();
        // 必须设置APPID，否则会静音
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "9436233");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }

    public class HaoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode bnRoutePlanNode = null;

        public HaoRoutePlanListener(BNRoutePlanNode node) {
            bnRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {

            Intent intent = new Intent(activity, NavGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("routePlanNode", bnRoutePlanNode);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            ToastUtils.showShort(activity, "算路失败");
        }
    }
}
