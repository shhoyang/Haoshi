package com.haoshi.mvp.Biz;


import com.haoshi.mvp.bean.User;

/**
 * Created by yugu on 2016/12/23.
 */

public interface OnLoginListener {
    
    void loginSuccess(User user);
    void loginFailed();
}
