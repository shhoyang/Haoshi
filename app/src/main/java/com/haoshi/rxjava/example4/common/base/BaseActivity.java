package com.haoshi.rxjava.example4.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.haoshi.rxjava.example4.common.baserx.RxBus;
import com.haoshi.rxjava.example4.common.widget.StatusBarCompat;

/**
 * @author HaoShi
 */
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {
    protected T presenter;
    protected E model;
    public Context context;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;
        initPM();
        if (presenter != null) {
            presenter.context = this;
        }
        setStatusBar();
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
        if (presenter != null) {
            presenter.onDestroy();
        }
        RxBus.getInstance().unSubscribeAll();
    }
}
