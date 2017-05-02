package com.haoshi.videorecorder;

import android.content.Intent;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.view.RingProgressBar;

public class VideoRecorderActivity extends BaseActivity {

    private RingProgressBar ringProgressBar;

    @Override
    public void initView() {

        ringProgressBar = (RingProgressBar) findViewById(R.id.progress);
        ringProgressBar.setValue(100,100);

        findViewById(R.id.text_start).setOnClickListener(view -> {
            startActivity(new Intent(this,MovieRecorderActivity.class));
        });
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_video_recorder;
    }

    @Override
    public String setTitle() {
        return TAG = VideoRecorderActivity.class.getSimpleName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ringProgressBar.clearBitmap();
    }
}
