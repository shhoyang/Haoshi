package com.haoshi.sqlite;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.sqlite.greendao.GreenDaoActivity;
import com.haoshi.sqlite.ormlite.OrmliteActivity;

/**
 * @author: HaoShi
 */
public class SqliteActivity extends BaseActivity {

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
        return R.layout.activity_sqlite;
    }

    @Override
    public String setTitle() {
        return TAG = SqliteActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, OrmliteActivity.class);
                break;
            case R.id.button1:
                intent = new Intent(this, GreenDaoActivity.class);
                break;
        }
        startActivity(intent);
    }
}
