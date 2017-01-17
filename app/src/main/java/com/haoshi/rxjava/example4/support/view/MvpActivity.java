package com.haoshi.rxjava.example4.support.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.haoshi.rxjava.example4.base.presenter.MvpPresenter;
import com.haoshi.rxjava.example4.base.view.MvpView;
import com.haoshi.rxjava.example4.support.delegate.activity.ActivityMvpDelegate;
import com.haoshi.rxjava.example4.support.delegate.activity.ActivityMvpDelegateCallback;
import com.haoshi.rxjava.example4.support.delegate.activity.ActivityMvpDelegateImpl;

/**
 * @author: HaoShi
 * 
 * 代理模式－静态代理：MvpActivity是MvpDelegateCallback具体的实现类
 */
@SuppressLint("NewApi")
public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>>
		extends FragmentActivity implements ActivityMvpDelegateCallback<V, P>, MvpView {

	private P presenter;

	private ActivityMvpDelegate<V, P> activityMvpDelegate;
	
	//是否保存数据
	private boolean retainInstance;

	protected ActivityMvpDelegate<V, P> getActivityMvpDelegate() {
		if (this.activityMvpDelegate == null) {
			this.activityMvpDelegate = new ActivityMvpDelegateImpl<V, P>(this);
		}
		return this.activityMvpDelegate;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivityMvpDelegate().onCreate(savedInstanceState);
	}

	@Override
	public void setPresenter(P presenter) {
		this.presenter = presenter;
	}

	@Override
	public P getPresenter() {
		return this.presenter;
	}

	@Override
	public V getMvpView() {
		return (V) this;
	}

	@Override
	protected void onStart() {
		super.onStart();
		getActivityMvpDelegate().onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();
		getActivityMvpDelegate().onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getActivityMvpDelegate().onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getActivityMvpDelegate().onRestart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		getActivityMvpDelegate().onStop();
	}

	@Override
	public void onContentChanged() {
		super.onContentChanged();
		getActivityMvpDelegate().onContentChanged();
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		getActivityMvpDelegate().onAttachedToWindow();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getActivityMvpDelegate().onSaveInstanceState(outState);
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		getActivityMvpDelegate().onDestroy();
	}
	
	/**
	 * 保存一个对象实例
	 * onRetainCustomNonConfigurationInstance
	 * 该方法FragmentActivity本身就存在，这是我们只是扩展了功能而已
	 * 
	 * 我在这里我代理的是FragmentActivity的方法
	 */
	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		//回调
		return getActivityMvpDelegate().onRetainCustomNonConfigurationInstance();
	}
	
	/**
	 * 获取保存的实例
	 * 该方法我们是通过静态代理，对FragmentActivity中的方法进行代理，处理相关逻辑
	 * 保存我们自己想要的数据
	 */
	@Override
	public Object getLastCustomNonConfigurationInstance() {
		return super.getLastCustomNonConfigurationInstance();
	}

	//扩张方法
	@Override
	public Object getNonLastCustomNonConfigurationInstance() {
		return getActivityMvpDelegate().getLastCustomNonConfigurationInstance();
	}
	
	@Override
	public boolean isRetainInstance() {
		return retainInstance;
	}
	
	@Override
	public void setRetainInstance(boolean retaionInstance) {
		this.retainInstance = retaionInstance;
	}
	
	@Override
	public boolean shouldInstanceBeRetained() {
		//说明Activity出现了异常情况才缓存数据
		return this.retainInstance && isChangingConfigurations();
	}
}
