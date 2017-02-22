package com.haoshi.androidtest;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

public class AndroidTestActivity extends BaseActivity {

    private EditText editText;
    private TextView textView;

    @Override
    public void initView() {
        editText = (EditText) findViewById(R.id.edit);
        textView = (TextView) findViewById(R.id.text);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_android_test;
    }

    @Override
    public String setTitle() {
        return TAG = AndroidTestActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        test();
    }

    private void test() {
        String str = editText.getText().toString();
        if (str != null) {
            textView.setText(str);
        }
    }
}
