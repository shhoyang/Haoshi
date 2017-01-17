package com.haoshi.rxjava.example4.support.delegate.viewgroup;

import android.content.Context;
import android.os.Parcelable;

import com.haoshi.rxjava.example4.base.presenter.MvpPresenter;
import com.haoshi.rxjava.example4.base.view.MvpView;
import com.haoshi.rxjava.example4.support.delegate.MvpDelegateCallback;

/**
 * @author: HaoShi
 * <p>
 * 针对ViewGroup集成MVP代理 目标接口
 */
public interface ViewGroupMvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> extends MvpDelegateCallback<V, P> {
    /**
     * 保存布局实例状态（这里是指布局相关数据）
     */
    Parcelable superOnSaveInstanceState();

    /**
     * 恢复布局实例状态
     */
    void superOnRestoreInstanceState(Parcelable state);

    Context getSuperContext();
}
