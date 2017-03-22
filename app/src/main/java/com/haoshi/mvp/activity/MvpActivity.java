package com.haoshi.mvp.activity;

import android.widget.EditText;
import android.widget.Toast;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.mvp.bean.User;
import com.haoshi.mvp.presenter.LoginPresenter;
import com.haoshi.mvp.view.IUserLoginView;

/**
 * @author HaoShi
 */
public class MvpActivity extends BaseActivity implements IUserLoginView {

    private EditText editUserName, editPassword;
    private LoginPresenter loginPresenter = new LoginPresenter(this);
    
    @Override
    public void initView() {
        editUserName = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        dialog.setMessage("正在登录...");
        findViewById(R.id.button).setOnClickListener(view -> loginPresenter.login());
    }

    @Override
    public void setData() {
        
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_mvp;
    }

    @Override
    public String setTitle() {
        return TAG = MvpActivity.class.getSimpleName();
    }

    @Override
    public String getUserName() {
        return editUserName.getText().toString();
    }

    @Override
    public String getPassword() {
        return editPassword.getText().toString();
    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void clearInfo() {
        editUserName.setText("");
        editPassword.setText("");
    }

    @Override
    public void toActivity(User user) {
        Toast.makeText(this, user.getUserName() + " login successful!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void showFailedError() {
        Toast.makeText(this, "login failed!", Toast.LENGTH_LONG).show();
    }
}
