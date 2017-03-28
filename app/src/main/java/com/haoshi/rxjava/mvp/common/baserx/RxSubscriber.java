package com.haoshi.rxjava.mvp.common.baserx;

import android.content.Context;

import com.haoshi.hao.HaoApplication;
import com.haoshi.utils.NetWorkUtil;
import com.haoshi.utils.ToastUtils;

import rx.Subscriber;

/**
 * Created by qihuang on 16-11-5.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable t) {
        Context context = HaoApplication.getInstance();
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, "请检查网络");
        } else {
            ToastUtils.showShort(context, t.getMessage());
        }
    }

    @Override
    public void onCompleted() {
    }

    protected abstract void _onNext(T t);
}
