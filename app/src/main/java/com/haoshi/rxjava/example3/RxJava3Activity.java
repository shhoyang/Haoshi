package com.haoshi.rxjava.example3;


import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.rxjava.example3.bean.Login;
import com.haoshi.rxjava.example3.bean.Register;
import com.haoshi.rxjava.example3.utils.NetWorks;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

public class RxJava3Activity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
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


    private String username = "username";
    private String password = "password";


    private void register() {
        dialog.setMessage("正在注册...");
        dialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("username", "");
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
            }

            @Override
            public void onNext(Login login) {

            }
        });
    }
}
