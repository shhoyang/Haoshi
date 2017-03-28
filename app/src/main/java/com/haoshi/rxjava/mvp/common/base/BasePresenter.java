package com.haoshi.rxjava.mvp.common.base;

import android.content.Context;

import com.haoshi.rxjava.mvp.common.baserx.RxManager;

/**
 * Created by qihuang on 16-11-5.
 */

public abstract class BasePresenter<T extends BaseView, E extends BaseModel> {
    public Context mContext;
    public T mView;
    public E mModel;
    public RxManager rxManager;

    public BasePresenter(T view, E model) {
        mModel = model;
        mView = view;
        onStart();
    }

    protected void onStart() {
        rxManager = new RxManager();
    }

    protected void onDestroy() {
        mView = null;
        rxManager.clear();
    }
}
