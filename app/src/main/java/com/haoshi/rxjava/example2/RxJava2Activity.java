package com.haoshi.rxjava.example2;

import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.L;

import rx.Observer;
import rx.subjects.PublishSubject;

/**
 * @author: HaoShi
 */
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
        return TAG = RxJava2Activity.class.getSimpleName();
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