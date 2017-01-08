package com.haoshi.hao;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.dialog.DialogActivity;
import com.haoshi.mvp.activity.MvpActivity;
import com.haoshi.progressbar.ProgressBarActivity;
import com.haoshi.rxjava.RxJavaActivity;
import com.haoshi.tts.TTSActivity;

public class IndexActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_index;
    }

    @Override
    public void setData() {
        TAG = IndexActivity.class.getSimpleName();
        setTitle(TAG);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, RxJavaActivity.class);
                break;
            case R.id.button1:
                intent = new Intent(this, TTSActivity.class);
                break;
            case R.id.button2:
                intent = new Intent(this, MvpActivity.class);
                break;
            case R.id.button3:
                intent = new Intent(this, ProgressBarActivity.class);
                break;
            case R.id.button4:
                intent = new Intent(this, DialogActivity.class);
                break;
        }
        startActivity(intent);
    }
}
