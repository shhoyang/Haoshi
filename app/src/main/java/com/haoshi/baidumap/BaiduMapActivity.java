package com.haoshi.baidumap;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.KeyBoardUtils;

public class BaiduMapActivity extends BaseActivity {

    private MapView mapView;
    private AutoCompleteTextView textSearch;
    private BaiduMap baiduMap;
    private LocationUtils locationUtils;
    private SearchUtils searchUtils;
    private NavUtils navUtils;

    @Override
    public void initView() {
        setEnableSwipe(false);
        mapView = (MapView) findViewById(R.id.map);
        textSearch = (AutoCompleteTextView) findViewById(R.id.text_search);
    }

    @Override
    public void setData() {
        baiduMap = mapView.getMap();
        locationUtils = new LocationUtils(this, baiduMap);
        searchUtils = new SearchUtils(this, baiduMap, textSearch);
        navUtils = new NavUtils(this);

        locationUtils.start();
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_baidu_map;
    }

    @Override
    public String setTitle() {
        return TAG = BaiduMapActivity.class.getSimpleName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationUtils.stop();
        searchUtils.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.baidumap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                textSearch.setVisibility(View.VISIBLE);
                textSearch.setFocusable(true);
                textSearch.setFocusableInTouchMode(true);
                textSearch.requestFocus();
                KeyBoardUtils.openKeybord(textSearch, BaiduMapActivity.this);
                break;
            case R.id.action_location:
                locationUtils.createMyLocation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getCity() {
        if (locationUtils.getBdLocation() == null) {
            return "杭州";
        } else {
            return locationUtils.getBdLocation().getCity();
        }
    }

    public void startNavi(PoiDetailResult poiDetailResult) {
        if (locationUtils.getBdLocation() != null) {
            navUtils.routeplanToNavi(locationUtils.getBdLocation(), poiDetailResult);
        }
    }
}
