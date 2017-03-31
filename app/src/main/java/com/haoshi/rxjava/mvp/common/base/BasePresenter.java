package com.haoshi.rxjava.mvp.common.base;

import android.content.Context;

import com.haoshi.rxjava.mvp.common.baserx.RxManager;

/**
 * Created by qihuang on 16-11-5.
 */

public abstract class BasePresenter<T extends BaseView, E extends BaseModel> {
    public Context context;
    public T view;
    public E model;
    public RxManager rxManager;

    public BasePresenter(T view, E model) {
        this.model = model;
        this.view = view;
        onStart();
    }

    protected void onStart() {
        rxManager = new RxManager();
    }

    protected void onDestroy() {
        view = null;
        rxManager.clear();
    }
}
