package com.haoshi.lottie;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

public class LottieActivity extends BaseActivity {

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
        return R.layout.activity_lottie;
    }

    @Override
    public String setTitle() {
        return TAG = LottieActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, NetWorkAnimActivity.class);
                break;
            case R.id.button1:
                intent = new Intent(this, LottieSimpleActivity.class);
                break;
            case R.id.button2:
                intent = new Intent(this, ProgressAnimActivity.class);
                break;
        }
        startActivity(intent);
    }
}
