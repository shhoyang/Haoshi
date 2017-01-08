package com.haoshi.dialog;

import android.provider.Settings;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

public class DialogActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_dialog;
    }

    @Override
    public void setData() {
        TAG = DialogActivity.class.getSimpleName();
        setTitle(TAG);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                new BottomDialog(this, R.layout.bottom_dialog).show();
                break;
            case R.id.button1:
                new LoadingDialog(this, "正在加载.....").show();
                break;
        }
    }


}
