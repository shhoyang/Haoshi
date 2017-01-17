package com.haoshi.rxjava.example4.support.lce.impl;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.haoshi.rxjava.example4.base.presenter.MvpPresenter;
import com.haoshi.rxjava.example4.support.lce.MvpLceView;
import com.haoshi.rxjava.example4.support.lce.impl.animator.ILceAnimator;
import com.haoshi.rxjava.example4.support.view.MvpFragment;

/**
 * @author: HaoShi
 */
public abstract class MvpLceFragment<M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
		extends MvpFragment<V, P> implements MvpLceView<M> {

	// 初始化Lce UI布局（规定你的Lce布局文件的id）
	private MvpLceViewImpl<M> lceViewImpl;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (lceViewImpl == null) {
			lceViewImpl = new MvpLceViewImpl<M>();
		}
		initLceView(view);
	}

	private void initLceView(View v) {
		lceViewImpl.initLceView(v);
		lceViewImpl.setOnErrorViewClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onErrorClick();
			}
		});
	}

	public void setLceAnimator(ILceAnimator lceAnimator){
		lceViewImpl.setLceAnimator(lceAnimator);
	}

	@Override
	public void showLoading(boolean pullToRefresh) {
		lceViewImpl.showLoading(pullToRefresh);
	}

	@Override
	public void showContent() {
		lceViewImpl.showContent();
	}

	@Override
	public void showError() {
		lceViewImpl.showError();
	}

	@Override
	public void loadData(boolean pullToRefresh) {
		lceViewImpl.loadData(pullToRefresh);
	}

	@Override
	public void bindData(M data) {
		lceViewImpl.bindData(data);
	}

	public void onErrorClick() {
		loadData(false);
	}

}
