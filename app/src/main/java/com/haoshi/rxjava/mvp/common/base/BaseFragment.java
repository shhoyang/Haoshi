package com.haoshi.rxjava.mvp.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haoshi.hao.ActionBean;
import com.haoshi.rxjava.mvp.common.baserx.RxBus;


/**
 * Created by qihuang on 16-11-5.
 */

public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {

    protected Activity activity;
    protected T presenter;
    protected E model;
    protected View rootView;
    private RxBus rxBus = RxBus.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        activity = getActivity();
        initPM();
        if (presenter != null) {
            presenter.context = activity;
        }
        rxBus.addSubscription(rxBus.toObservable().subscribe(this::handleEvent));
        initView();
        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initPM();

    protected abstract void initView();

    protected abstract void handleEvent(Object o);

    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroy();
        }
//        rxBus.unSubscribeAll();
    }
}
