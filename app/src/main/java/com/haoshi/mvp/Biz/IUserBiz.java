package com.haoshi.mvp.Biz;

/**
 * Created by yugu on 2016/12/23.
 */

public interface IUserBiz {
    
    void login(String userName, String password, OnLoginListener onLoginListener);
}
