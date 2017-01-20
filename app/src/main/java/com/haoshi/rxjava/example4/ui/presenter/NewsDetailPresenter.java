package com.haoshi.rxjava.example4.ui.presenter;


import com.haoshi.rxjava.example4.ui.contract.NewsDetailContract;

/**
 * @author Haoshi
 */

public class NewsDetailPresenter extends NewsDetailContract.Presenter {
    public NewsDetailPresenter(NewsDetailContract.View view, NewsDetailContract.Model model) {
        super(view, model);
    }
}
