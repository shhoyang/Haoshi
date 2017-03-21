package com.haoshi.toast;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

public class ToastActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_toast;
    }

    @Override
    public String setTitle() {
        return TAG = ToastActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, ToastyActivity.class);
                break;
            case R.id.button1:
                intent = new Intent(this, StyleableToastActivity.class);
                break;
        }
        startActivity(intent);
    }
}
