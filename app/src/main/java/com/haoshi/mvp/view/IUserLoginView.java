package com.haoshi.mvp.view;


import com.haoshi.mvp.bean.User;

/**
 * Created by yugu on 2016/12/23.
 */

public interface IUserLoginView {

    String getUserName();

    String getPassword();

    void showLoading();

    void hideLoading();
    
    void clearInfo();

    void toActivity(User user);

    void showFailedError();
}
