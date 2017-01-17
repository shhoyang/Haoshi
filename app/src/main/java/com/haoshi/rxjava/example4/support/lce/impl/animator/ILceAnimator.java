package com.haoshi.rxjava.example4.support.lce.impl.animator;

import android.view.View;

/**
 * @author HaoShi
 *         <p>
 *         策略模式
 */
public interface ILceAnimator {
    void showLoading(View loadingView, View contentView, View errorView);

    void showErrorView(View loadingView, View contentView, View errorView);

    void showContent(View loadingView, View contentView, View errorView);
}
