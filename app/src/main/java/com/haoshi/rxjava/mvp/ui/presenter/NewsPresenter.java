package com.haoshi.rxjava.mvp.ui.presenter;


import com.haoshi.rxjava.mvp.bean.Data;
import com.haoshi.rxjava.mvp.common.baserx.RxSubscriber;
import com.haoshi.rxjava.mvp.ui.contract.NewsContract;

import java.util.List;

/**
 * @author Haoshi
 */

public class NewsPresenter extends NewsContract.Presenter {

    public NewsPresenter(NewsContract.View view, NewsContract.Model model) {
        super(view, model);
    }

    @Override
    public void getChannelList(String type) {
        rxManager.add(model.getChannelList(type).subscribe(new RxSubscriber<List<Data>>() {
            @Override
            protected void _onNext(List<Data> dataList) {
                view.returnChannelList(dataList);
                view.finishLoading();
            }
        }));
    }
}
