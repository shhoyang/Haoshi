package com.haoshi.rxjava.example4.ui.presenter;


import com.haoshi.rxjava.example4.bean.Data;
import com.haoshi.rxjava.example4.common.baserx.RxSubscriber;
import com.haoshi.rxjava.example4.ui.contract.NewsContract;

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
        rxManager.add(mModel.getChannelList(type).subscribe(new RxSubscriber<List<Data>>() {
            @Override
            protected void _onNext(List<Data> dataList) {
                mView.returnChannelList(dataList);
                mView.finishLoading();
            }
        }));
    }
}
