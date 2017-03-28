package com.haoshi.rxjava.mvp.common.base;

/**
 * Created by qihuang on 16-11-5.
 */

public interface BaseView {
    void startLoading();
    void finishLoading();
    void cancelLoading();
}
