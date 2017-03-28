package com.haoshi.rxjava.mvp.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.haoshi.rxjava.mvp.common.baserx.RxBus;
import com.haoshi.rxjava.mvp.common.widget.StatusBarCompat;

/**
 * Created by qihuang on 16-11-5.
 */

public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel>
        extends AppCompatActivity {
    protected T mPresenter;
    protected E mModel;
    public Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        initPM();
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        //setStatusBar();
        initView();
        setBackGesture();
    }

    protected void setStatusBar() {
        StatusBarCompat.translucentStatusBar(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initPM();

    protected abstract void initView();

    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void setBackGesture() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        RxBus.getInstance().unSubscribeAll();
    }
}
