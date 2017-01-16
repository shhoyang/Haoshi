package com.haoshi.rxjava.example3;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.rxjava.example3.bean.Login;
import com.haoshi.rxjava.example3.bean.Register;
import com.haoshi.rxjava.example3.utils.NetWorks;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

public class RxJava3Activity extends BaseActivity {

    private EditText editUserName, editPassword;
    private TextView textResult;
    private String username, password;

    @Override
    public void initView() {
        editUserName = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        textResult = (TextView) findViewById(R.id.text_result);

        findViewById(R.id.button_register).setOnClickListener(this);
        findViewById(R.id.button_login).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_rx_java3;
    }

    @Override
    public String setTitle() {
        return TAG = RxJava3Activity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        username = editUserName.getText().toString();
        password = editPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            return;
        }
        switch (v.getId()) {
            case R.id.button_register:
                register();
                break;
            case R.id.button_login:
                login();
                break;
        }
    }

    private void register() {
        dialog.setMessage("正在注册...");
        dialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("nikename", username);
        map.put("username", username);
        map.put("password", password);
        NetWorks.doRegister(map, new Observer<Register>() {
            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.toString());
                dialog.dismiss();
            }

            @Override
            public void onNext(Register register) {
                textResult.setText(register.toString());
            }
        });
    }

    private void login() {
        dialog.setMessage("正在登陆...");
        dialog.show();
        NetWorks.doLogin(username, password, new Observer<Login>() {
            @Override
            public void onCompleted() {
                dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.toString());
                dialog.dismiss();
                dialog.dismiss();
            }

            @Override
            public void onNext(Login login) {
                textResult.setText(login.toString());
            }
        });
    }
}
