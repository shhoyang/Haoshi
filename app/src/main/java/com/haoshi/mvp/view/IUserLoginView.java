package com.haoshi.mvp.view;


import com.haoshi.mvp.bean.User;

/**
 * @author HaoShi
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
