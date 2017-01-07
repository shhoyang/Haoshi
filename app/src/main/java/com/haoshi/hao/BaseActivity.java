package com.haoshi.hao;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Haoshi on 2017/1/7.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected static String TAG;
    protected ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewID());
        initView();
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        setData();
    }

    public abstract void initView();

    public abstract int setContentViewID();

    public abstract void setData();

    @Override
    public void onClick(View v) {
    }
}
