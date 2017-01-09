package com.haoshi.rxjava;

import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.IndexActivity;
import com.haoshi.utils.L;

import rx.Observer;
import rx.subjects.PublishSubject;

public class RxJava2Activity extends BaseActivity {

    private PublishSubject publishSubject;

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
    }
    
    @Override
    public void setData() {
        publishSubject = PublishSubject.create();
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_rx_java2;
    }

    @Override
    public String setTitle() {
        return TAG = IndexActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                publishSubject.subscribe(new Observer() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o){
                        L.d(TAG,"收到数据" + o);
                    }
                });
                break;
            case R.id.button1:
                publishSubject.onNext("Hello RxJava!");
                break;
        }
    }
}