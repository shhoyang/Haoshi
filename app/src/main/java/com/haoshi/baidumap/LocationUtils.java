package com.haoshi.baidumap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.haoshi.R;
import com.haoshi.utils.ToastUtils;

/**
 * @author Haoshi
 */

public class LocationUtils {

    private BaiduMapActivity activity;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private BitmapDescriptor descriptor;
    private MyLocationConfiguration configuration;
    private OrientationListener orientationListener;
    private float rotate = 0;
    private BDLocation bdLocation = null;

    public LocationUtils(BaiduMapActivity activity, BaiduMap baiduMap) {
        this.activity = activity;
        this.baiduMap = baiduMap;
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        locationClient = new LocationClient(activity, option);
        locationClient.registerLocationListener(bdLocationListener);
        descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.arrow);
        configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, descriptor);
        orientationListener = new OrientationListener(activity, onOrientationListener);
    }

    public BDLocation getBdLocation() {
        return bdLocation;
    }

    private BDLocationListener bdLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            LocationUtils.this.bdLocation = bdLocation;
            MyLocationData locationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(rotate) // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            baiduMap.setMyLocationData(locationData);
            //设置自定义图标
            //baiduMap.setMyLocationConfigeration(configuration);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    };

    private OrientationListener.OnOrientationListener onOrientationListener = x -> rotate = x;

    public void start() {
        baiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted()) {
            locationClient.start();
        }
        orientationListener.start();
    }

    public void stop() {
        baiduMap.setMyLocationEnabled(false);
        if (locationClient.isStarted()) {
            locationClient.stop();
        }
        orientationListener.stop();
    }

    public void createMyLocation() {
        if (bdLocation == null) {
            return;
        }

        MapStatus mapStatus = new MapStatus.Builder()
                .target(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()))
                .zoom(18f)
                .build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        baiduMap.animateMapStatus(mapStatusUpdate);
        ToastUtils.showShort(activity, bdLocation.getAddrStr());
    }
}
