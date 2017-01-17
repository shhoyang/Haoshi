package com.haoshi.rxjava.example4.support.delegate.activity;

import android.os.Bundle;

import com.haoshi.rxjava.example4.base.presenter.MvpPresenter;
import com.haoshi.rxjava.example4.base.view.MvpView;
import com.haoshi.rxjava.example4.support.delegate.MvpDelegateCallbackProxy;

/**
 * @author: HaoShi
 * 
 * 代理模式－静态代理：具体的目标接口实现类 该实现类对应的代理类是Activity
 */
public class ActivityMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements ActivityMvpDelegate<V, P> {

    private MvpDelegateCallbackProxy<V, P> proxy;
    // 具体的目标接口实现类，需要持有创建Mvp实例
    private ActivityMvpDelegateCallback<V, P> delegateCallback;

    public ActivityMvpDelegateImpl(
            ActivityMvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("delegateCallback is not null!");
        }
        this.delegateCallback = delegateCallback;
    }

    private MvpDelegateCallbackProxy<V, P> getDelegateProxy() {
        if (this.proxy == null) {
            this.proxy = new MvpDelegateCallbackProxy<V, P>(delegateCallback);
        }
        return this.proxy;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //绑定缓存的MvpPresenter
        //如果存在就绑定，否则重新创建
        Object instance = delegateCallback.getLastCustomNonConfigurationInstance();

        //以下是处理保存状态逻辑
        if (instance != null && instance instanceof ActivityMvpConfigurationInstance) {
            ActivityMvpConfigurationInstance<V, P> configurationInstance = (ActivityMvpConfigurationInstance<V, P>) instance;
            if (configurationInstance.getPresenter() == null) {
                getDelegateProxy().createPresenter();
            } else {
                getDelegateProxy().setPresenter(configurationInstance.getPresenter());
            }
        } else {
            //Activity不保存状态的处理
            getDelegateProxy().createPresenter();
        }


        getDelegateProxy().attachView();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        getDelegateProxy().detachView();
    }

    @Override
    public void onContentChanged() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onAttachedToWindow() {

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        // 存储对象(思考：到底要存什么数据？)
        // 保存：Presenter对象引用（时时刻刻保持引用对象）
        // 保存：数据（List数据、对象数据）例如：用户名、密码等等......
        // 判断到底要不要缓存该对象（可配置）
        // 不是所有的Activity都需要缓存(具体的要更具需求确定)
        // 例如：表单页面
        // 思考怎么判断我要保存呢？
        boolean retained = delegateCallback.shouldInstanceBeRetained();
        // 根据状态，判断是否保存Presenter，不保存我就立马释放
        P presenter = retained ? delegateCallback.getPresenter() : null;
        // 保存数据对象
        Object instence = delegateCallback
                .onRetainCustomNonConfigurationInstance();
        // 两个对象都等于空，不需要缓存了
        if (presenter == null && instence == null) {
            return null;
        }
        // 需要缓存
        return new ActivityMvpConfigurationInstance<V, P>(presenter, instence);
    }

    @Override
    public Object getLastCustomNonConfigurationInstance() {
        // 该对象在activity中配置
        Object instance = delegateCallback
                .getLastCustomNonConfigurationInstance();
        if (instance != null && instance instanceof ActivityMvpConfigurationInstance) {
            ActivityMvpConfigurationInstance<V, P> configurationInstance = (ActivityMvpConfigurationInstance<V, P>) instance;
            return configurationInstance.getCustomeConfigurationInstance();
        }
        return null;
    }

}
