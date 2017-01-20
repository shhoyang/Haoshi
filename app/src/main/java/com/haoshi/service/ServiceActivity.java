package com.haoshi.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import com.haoshi.IAidlInterface;
import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.ToastUtils;

/**
 * @author HaoShi
 */
public class ServiceActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }
    
    @Override
    public void setData() {
        
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_service;
    }

    @Override
    public String setTitle() {
        return TAG = ServiceActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                aidl();
                break;
            case R.id.button1:
                messagerBind();
                break;
            case R.id.button2:
                messengerSend();
                break;
            case R.id.button3:

                break;
        }
    }

    /******************************************************************/

    private Messenger messenger;
    //将该handler发送Service
    private Messenger outMessenger = new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==2){
                ToastUtils.showShort(ServiceActivity.this, (String) msg.obj);
            }
        }
    });

    private void messagerBind() {
        Intent intent = new Intent(this, MessengerService.class);
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ToastUtils.showShort(ServiceActivity.this, "链接成功");
                messenger = new Messenger(service);
                Message msg = new Message();
                msg.what = 1;
                //Activity绑定Service的时候给Service发送一个消息,该消息是Messenger对象;
                msg.obj = outMessenger;
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent,connection, Service.BIND_AUTO_CREATE);
    }

    private void messengerSend() {
        if(messenger == null){
            ToastUtils.showShort(this,"服务不可用");
            return;
        }
        Message msg = new Message();
        msg.obj = "0707,我是01,收到请将!";
        msg.what = 0;
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /******************************************************************/
    private IAidlInterface iAidlInterface;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iAidlInterface = IAidlInterface.Stub.asInterface(service);
            String info  = null;
            try {
                info = iAidlInterface.getInfo("hello");
                ToastUtils.showLong(ServiceActivity.this,info);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void aidl() {
        Intent intent =new Intent("haoshi");
        bindService(intent, connection, BIND_AUTO_CREATE);
    }
}
