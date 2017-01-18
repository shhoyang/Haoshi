package com.haoshi.mvp.Biz;

/**
 * @author HaoShi
 */
public interface IUserBiz {
    
    void login(String userName, String password, OnLoginListener onLoginListener);
}
