package com.haoshi.baidumap;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.KeyBoardUtils;
import com.haoshi.utils.ToastUtils;

public class BaiduMapActivity extends BaseActivity implements TextView.OnEditorActionListener {

    private MapView mapView;
    private EditText editSearch;
    private BaiduMap baiduMap;
    private LocationUtils locationUtils;

    @Override
    public void initView() {
        mapView = (MapView) findViewById(R.id.map);
        editSearch = (EditText) findViewById(R.id.edit_search);
        editSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editSearch.setOnEditorActionListener(this);
    }

    @Override
    public void setData() {
        baiduMap = mapView.getMap();
        baiduMap = mapView.getMap();
        locationUtils = new LocationUtils(this, baiduMap);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.baidumap_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                editSearch.setVisibility(View.VISIBLE);
                editSearch.setFocusable(true);
                editSearch.setFocusableInTouchMode(true);
                editSearch.requestFocus();
                KeyBoardUtils.openKeybord(editSearch, BaiduMapActivity.this);
                break;
            case R.id.action_location:
                locationUtils.start();
                break;
            case R.id.action_my_location:
                locationUtils.createMyLocation();
                break;
            case R.id.action_start_nav:
                break;
            case R.id.action_end_nav:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {//修改回车键功能
            ToastUtils.showShort(this, "开始搜索");
            editSearch.setText("");
            editSearch.setVisibility(View.GONE);
            KeyBoardUtils.closeKeybord(editSearch, BaiduMapActivity.this);
            return false;
        }
        return true;
    }
}
