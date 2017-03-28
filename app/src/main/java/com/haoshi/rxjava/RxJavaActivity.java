package com.haoshi.rxjava;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.rxjava.cookie.RxJavaCookieActivity;
import com.haoshi.rxjava.mvp.ui.activity.RxJavaMvpActivity;
import com.haoshi.rxjava.rxbus.RxBusActivity;

/**
 * @author: HaoShi
 */
public class RxJavaActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_rx_java;
    }

    @Override
    public String setTitle() {
        return TAG = RxJavaActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, RxJavaSimple1Activity.class);
                break;
            case R.id.button1:
                intent = new Intent(this, RxJavaSimple2Activity.class);
                break;
            case R.id.button2:
                intent = new Intent(this, RxJavaCookieActivity.class);
                break;
        }
        startActivity(intent);
    }
}
