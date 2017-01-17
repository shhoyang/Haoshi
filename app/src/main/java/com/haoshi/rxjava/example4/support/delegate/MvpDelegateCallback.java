package com.haoshi.rxjava.example4.support.delegate;

import com.haoshi.rxjava.example4.base.presenter.MvpPresenter;
import com.haoshi.rxjava.example4.base.view.MvpView;

/**
 * @author: HaoShi
 * <p>
 * 代理模式－静态代理：目标接口
 */
public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {
    /**
     * 创建Presenter方法
     */
    P createPresenter();

    /**
     * 得到Presenter实例
     */
    P getPresenter();

    /**
     * 设置一个新的Presenter
     */
    void setPresenter(P presenter);

    /**
     * 得到具体的MvpView实例对象
     */
    V getMvpView();

    /**
     * 是否保存数据
     */
    void setRetainInstance(boolean retaionInstance);

    boolean isRetainInstance();

    /**
     * 判断是否保存数据(该方法还会处理横竖屏切换)
     */
    boolean shouldInstanceBeRetained();
}
