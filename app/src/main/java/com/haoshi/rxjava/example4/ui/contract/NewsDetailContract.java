package com.haoshi.rxjava.example4.ui.contract;


import com.haoshi.rxjava.example4.common.base.BaseModel;
import com.haoshi.rxjava.example4.common.base.BasePresenter;
import com.haoshi.rxjava.example4.common.base.BaseView;

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
