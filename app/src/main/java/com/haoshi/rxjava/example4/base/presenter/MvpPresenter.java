package com.haoshi.rxjava.example4.base.presenter;


import com.haoshi.rxjava.example4.base.view.MvpView;

/**
 * @author: HaoShi
 * 
 * 抽象为接口
 */
public interface MvpPresenter<V extends MvpView> {

    /**
     * 绑定视图
     */
    public void attachView(V view);

    /**
     * 解除绑定
     */
    public void dettachView();

}
