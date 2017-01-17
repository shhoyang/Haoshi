package com.haoshi.rxjava.example4.support.delegate.viewgroup;

import android.os.Parcelable;

import com.haoshi.rxjava.example4.base.presenter.MvpPresenter;
import com.haoshi.rxjava.example4.base.view.MvpView;

/**
 * @author: HaoShi
 * 
 * 以下代理是针对布局的代理
 */
public interface ViewGroupMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

	/**
	 * 关联布局
	 */
	 void onAttachedToWindow();

	/**
	 * 解除关联
	 */
	 void onDetachedFromWindow();

	/**
	 * 保存布局实例状态（这里是指布局相关数据）
	 */
	 Parcelable onSaveInstanceState();

	/**
	 * 恢复布局实例状态
	 */
	 void onRestoreInstanceState(Parcelable state);
}
