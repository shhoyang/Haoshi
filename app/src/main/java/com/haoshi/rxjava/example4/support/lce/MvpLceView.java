package com.haoshi.rxjava.example4.support.lce;

import com.haoshi.rxjava.example4.base.view.MvpView;

/**
 * @author: HaoShi
 * <p>
 * 目标接口
 */
public interface MvpLceView<M> extends MvpView {

    /**
     * 显示loading页面 pullToRefresh：true代表你用的是下拉刷新组件
     */
    void showLoading(boolean pullToRefresh);

    /**
     * 显示ContentView
     */
    void showContent();

    /**
     * 显示异常界面
     */
    void showError();

    /**
     * 绑定数据
     */
    void bindData(M data);

    /**
     * 加载数据
     */
    void loadData(boolean pullToRefresh);

}
