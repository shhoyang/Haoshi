package com.haoshi.rxjava.example4.support.delegate.fragment;

import com.haoshi.rxjava.example4.base.presenter.MvpPresenter;
import com.haoshi.rxjava.example4.base.view.MvpView;
import com.haoshi.rxjava.example4.support.delegate.MvpDelegateCallback;

/**
 * @author: HaoShi
 * 
 * 扩展目标接口 针对不同的模块，目标接口有差异
 */
public interface FragmentMvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
		extends MvpDelegateCallback<V, P> {
	
}
