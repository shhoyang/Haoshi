package com.haoshi.mvp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.haoshi.R;
import com.haoshi.mvp.bean.User;
import com.haoshi.mvp.presenter.LoginPresenter;
import com.haoshi.mvp.view.IUserLoginView;


public class MvpActivity extends AppCompatActivity implements IUserLoginView {

    private EditText editUserName, editPassword;
    private ProgressDialog dialog;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        initView();
    }

    private void initView() {
        setTitle(MvpActivity.class.getSimpleName());
        editUserName = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("正在登录...");
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.login();
            }
        });
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
