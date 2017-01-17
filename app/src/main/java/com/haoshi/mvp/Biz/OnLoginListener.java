package com.haoshi.mvp.Biz;


import com.haoshi.mvp.bean.User;

/**
 * @author: HaoShi
 */

public interface OnLoginListener {
    
    void loginSuccess(User user);
    void loginFailed();
}
