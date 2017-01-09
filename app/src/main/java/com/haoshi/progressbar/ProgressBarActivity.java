package com.haoshi.progressbar;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

public class ProgressBarActivity extends BaseActivity {

    @Override
    public void initView() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_progress_bar;
    }

    @Override
    public void setData() {
        TAG = ProgressBarActivity.class.getSimpleName();
        setTitle(TAG);
    }
}
