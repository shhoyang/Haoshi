package com.haoshi.rxjava.example4.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haoshi.rxjava.example4.common.baserx.RxBus;


/**
 * Created by qihuang on 16-11-5.
 */

public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    protected T mPresenter;
    protected E mModel;
    protected View rootView;
    private RxBus rxBus = RxBus.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        initPM();
        if (mPresenter != null) {
            mPresenter.mContext = getActivity();
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
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
//        rxBus.unSubscribeAll();
    }
}
