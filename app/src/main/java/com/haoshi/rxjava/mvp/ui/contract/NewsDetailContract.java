package com.haoshi.rxjava.mvp.ui.contract;


import com.haoshi.rxjava.mvp.common.base.BaseModel;
import com.haoshi.rxjava.mvp.common.base.BasePresenter;
import com.haoshi.rxjava.mvp.common.base.BaseView;

/**
 * @author Haoshi
 */

public interface NewsDetailContract {
    interface View extends BaseView {

    }

    interface Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public Presenter(View view, Model model) {
            super(view, model);
        }
    }
}
