package com.haoshi.rxjava.example4.common.base;

import android.content.Context;

import com.haoshi.rxjava.example4.common.baserx.RxManager;

/**
 * @author HaoShi
 */
public abstract class BasePresenter<T extends BaseView, E extends BaseModel> {
    public Context context;
    public T view;
    public E model;
    public RxManager rxManager;

    public BasePresenter(T view, E model) {
        this.view = view;
        this.model = model;
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
