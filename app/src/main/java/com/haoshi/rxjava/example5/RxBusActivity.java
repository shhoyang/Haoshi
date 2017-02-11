package com.haoshi.rxjava.example5;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

import rx.functions.Action1;

public class RxBusActivity extends BaseActivity {

    private EditText editText;
    private TextView textView;
    private RxBus rxBus = RxBus.getInstance();

    @Override
    public void initView() {
        editText = (EditText) findViewById(R.id.edit);
        textView = (TextView) findViewById(R.id.text);
        findViewById(R.id.button).setOnClickListener(this);
        rxBus.addSubscription(rxBus.toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                RxBusActivity.this.handleEvent(o);
            }
        }));
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_rx_bus;
    }

    @Override
    public String setTitle() {
        return TAG = RxBusActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                send();
                break;
        }
    }

    private void send() {
        String content = editText.getText().toString();
        if (content != null) {
            rxBus.send(new Event(content));
        }
    }

    private void handleEvent(Object o) {
        if (o instanceof Event) {
            Event event = (Event) o;
            textView.setText(event.content);
        }
    }
}
