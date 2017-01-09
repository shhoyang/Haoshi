package com.haoshi.sqlite;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.sqlite.ormlite.OrmliteActivity;

public class SqliteActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_sqlite;
    }

    @Override
    public void setData() {
        TAG = SqliteActivity.class.getSimpleName();
        setTitle(TAG);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, OrmliteActivity.class);
                break;
        }
        startActivity(intent);
    }
}
