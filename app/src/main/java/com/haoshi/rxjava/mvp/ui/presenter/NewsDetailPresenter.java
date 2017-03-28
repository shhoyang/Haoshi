package com.haoshi.rxjava.mvp.ui.presenter;


import com.haoshi.rxjava.mvp.ui.contract.NewsDetailContract;

/**
 * @author Haoshi
 */

public class NewsDetailPresenter extends NewsDetailContract.Presenter {
    public NewsDetailPresenter(NewsDetailContract.View view, NewsDetailContract.Model model) {
        super(view, model);
    }
}
