package com.haoshi.rxjava.example4.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haoshi.rxjava.example4.common.baserx.RxBus;

import rx.Subscription;
import rx.functions.Action1;

/**
 * @author HaoShi
 */

public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    protected T presenter;
    protected E model;
    protected View rootView;
    protected Subscription subscribe;
    private RxBus rxBus = RxBus.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        initPM();
        if (presenter != null) {
            presenter.context = getActivity();
        }
        addSubscription();
        initView();
        return rootView;
    }

    protected abstract int getLayoutId();

    protected abstract void initPM();

    protected abstract void initView();

    protected abstract void addSubscription();

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
        if (subscribe != null) {
            subscribe.unsubscribe();
        }
    }
}
