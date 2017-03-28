package com.haoshi.rxjava.mvp.ui.model;

import com.haoshi.hao.Constant;
import com.haoshi.rxjava.mvp.api.Api;
import com.haoshi.rxjava.mvp.bean.Data;
import com.haoshi.rxjava.mvp.bean.News;
import com.haoshi.rxjava.mvp.common.baserx.RxSchedulers;
import com.haoshi.rxjava.mvp.ui.contract.NewsContract;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author Haoshi
 */
public class NewsModel implements NewsContract.Model {
    @Override
    public Observable<List<Data>> getChannelList(String type) {
        return Api.getDefault()
                .getNews(type, Constant.API_KEY)
                .map(new Func1<News, List<Data>>() {
                    @Override
                    public List<Data> call(News news) {
                        return news.getResult().getData();
                    }
                })
                .compose(RxSchedulers.io_main());
    }
}
