package com.haoshi.rxjava.example4.common.baserx;

import android.content.Context;

import com.haoshi.hao.HaoApplication;
import com.haoshi.rxjava.example4.exception.NewsException;
import com.haoshi.utils.NetWorkUtil;
import com.haoshi.rxjava.example4.util.ToastUtil;

import rx.Subscriber;

/**
 * Created by qihuang on 16-11-5.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {
    private Context mContext;


    public RxSubscriber(Context context) {
        mContext = context;
    }


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
        Context context = mContext.getApplicationContext();
        if (!NetWorkUtil.isNetworkConnected(context)) {
            com.haoshi.utils.T.showShort(HaoApplication.getInstance(), "请检查网络");
        } else if (t instanceof NewsException) {
            com.haoshi.utils.T.showShort(HaoApplication.getInstance(), t.getMessage());
        } else {
            com.haoshi.utils.T.showShort(HaoApplication.getInstance(), t.getMessage());
        }

    }

    @Override
    public void onCompleted() {
    }

    protected abstract void _onNext(T t);
}
