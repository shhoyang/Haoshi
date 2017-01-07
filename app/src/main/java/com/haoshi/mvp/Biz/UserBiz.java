package com.haoshi.mvp.Biz;

import android.os.SystemClock;

import com.haoshi.mvp.bean.User;


/**
 * Created by yugu on 2016/12/23.
 */

public class UserBiz implements IUserBiz {

    @Override
    public void login(final String userName, final String password, final OnLoginListener onLoginListener) {

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                if (userName.equals("haoshi") && password.equals("123456")) {
                    User user = new User(userName, password);
                    onLoginListener.loginSuccess(user);
                } else {
                    onLoginListener.loginFailed();
                }
            }
        }.start();
    }
}
