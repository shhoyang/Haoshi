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
    public int setContentViewID() {
        return R.layout.activity_dialog;
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.button:
                new BottomDialog(this).showDiaog(R.layout.bottom_dialog);
                break;
            case R.id.button1:
                break;
        }
    }


}
