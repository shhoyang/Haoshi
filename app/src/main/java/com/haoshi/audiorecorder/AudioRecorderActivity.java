package com.haoshi.audiorecorder;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

public class AudioRecorderActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button_file).setOnClickListener(this);
        findViewById(R.id.button_stream).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_audio_recorder;
    }

    @Override
    public String setTitle() {
        return TAG = AudioRecorderActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.button_file:
                startActivity(new Intent(this,FileModeActivity.class));
                break;
            case R.id.button_stream:
                startActivity(new Intent(this,StreamModeActivity.class));
                break;
        }
    }
}
