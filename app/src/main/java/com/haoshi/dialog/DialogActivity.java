package com.haoshi.dialog;

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
    public void setData() {
       
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_dialog;
    }
    
    @Override
    public String setTitle() {
        return TAG = DialogActivity.class.getSimpleName();
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
