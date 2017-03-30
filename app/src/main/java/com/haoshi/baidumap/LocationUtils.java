package com.haoshi.baidumap;

import android.content.Context;

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

public class LocationUtils implements BDLocationListener {

    private Context context;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private BitmapDescriptor descriptor;
    private MyLocationConfiguration configuration;
    private double latitude;
    private double longitude;
    private boolean isFirst = true;

    public LocationUtils(Context context, BaiduMap baiduMap) {
        this.context = context;
        this.baiduMap = baiduMap;
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        locationClient = new LocationClient(context, option);
        locationClient.registerLocationListener(this);
        descriptor = BitmapDescriptorFactory.fromResource(R.mipmap.arrow);
        configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, descriptor);

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                .direction(0) // 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude())
                .build();
        baiduMap.setMyLocationData(locationData);
        //设置自定义图标
        baiduMap.setMyLocationConfigeration(configuration);
        latitude = bdLocation.getLatitude();
        longitude = bdLocation.getLongitude();

        if (isFirst) {
            createMyLocation();
            ToastUtils.showShort(context, bdLocation.getAddrStr());
            isFirst = false;
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }

    public void start() {
        baiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted()) {
            locationClient.start();
        }
    }

    public void stop() {
        baiduMap.setMyLocationEnabled(false);
        if (locationClient.isStarted()) {
            locationClient.stop();
        }
    }

    public void createMyLocation() {
        if (latitude == 0 || longitude == 0) {
            return;
        }
        MapStatus mapStatus = new MapStatus.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(20.0f)
                .build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        baiduMap.animateMapStatus(mapStatusUpdate);
    }
}
