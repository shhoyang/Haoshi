package com.haoshi.rxjava.mvp.ui.contract;

import com.haoshi.rxjava.mvp.bean.Data;
import com.haoshi.rxjava.mvp.common.base.BaseModel;
import com.haoshi.rxjava.mvp.common.base.BasePresenter;
import com.haoshi.rxjava.mvp.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * @author Haoshi
 */

public interface NewsContract {

    interface View extends BaseView {
        void returnChannelList(List<Data> dataList);
    }

    interface Model extends BaseModel {
        Observable getChannelList(String type);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        
        public Presenter(View view, Model model) {
            super(view, model);
        }
        public abstract void getChannelList(String type);
    }
}
