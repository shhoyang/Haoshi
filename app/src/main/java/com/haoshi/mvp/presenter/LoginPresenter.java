package com.haoshi.mvp.presenter;

import android.os.Handler;

import com.haoshi.mvp.Biz.IUserBiz;
import com.haoshi.mvp.Biz.OnLoginListener;
import com.haoshi.mvp.Biz.UserBiz;
import com.haoshi.mvp.activity.MvpActivity;
import com.haoshi.mvp.bean.User;
import com.haoshi.mvp.view.IUserLoginView;

/**
 * @author HaoShi
 */
public class LoginPresenter {
    
    private IUserBiz iUserBiz;
    private IUserLoginView iUserLoginView;
    private Handler handler = new Handler();

    public LoginPresenter(MvpActivity iUserLoginView) {
        this.iUserLoginView = iUserLoginView;
        iUserBiz = new UserBiz();
    }
    
    public void login(){
        iUserLoginView.showLoading();
        iUserBiz.login(iUserLoginView.getUserName(), iUserLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserLoginView.toActivity(user);
                        iUserLoginView.clearInfo();
                        iUserLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserLoginView.showFailedError();
                        iUserLoginView.hideLoading();
                    }
                });
            }
        });
    }
}
