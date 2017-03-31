package com.haoshi.dialog;

import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

/**
 * @author HaoShi
 */
public class DialogActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
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
                new LoadingDialog(this, "正在加载...").show();
        }
    }
}
