package com.haoshi.rxjava.example4.ui.model;

import com.haoshi.hao.Constant;
import com.haoshi.rxjava.example4.api.Api;
import com.haoshi.rxjava.example4.bean.Data;
import com.haoshi.rxjava.example4.bean.News;
import com.haoshi.rxjava.example4.common.baserx.RxSchedulers;
import com.haoshi.rxjava.example4.ui.contract.NewsContract;

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
