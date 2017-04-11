package com.haoshi.tinker;

import android.view.View;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.Constant;
import com.haoshi.hao.HaoApplication;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

public class TinkerActivity extends BaseActivity {

    private static final String PATCH_PATH = Constant.SD_PATH + "/patch_signed_7zip.apk";

    private TextView textView;

    @Override
    public void initView() {
        textView = (TextView) findViewById(R.id.text);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void setData() {
        textView.setText(getText());
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_tinker;
    }

    @Override
    public String setTitle() {
        return TAG = TinkerActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                //命令tinkerPatchRelease
                TinkerInstaller.onReceiveUpgradePatch(HaoApplication.getInstance().getApplication(), PATCH_PATH);
                break;
        }
    }

    private String getText() {
        return "this is new apk";
    }
}
