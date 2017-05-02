package com.haoshi.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.haoshi.utils.LogUtils;


/**
 * @author HaoShi
 */
public class MessengerService extends Service {

    private static final String TAG = MessengerService.class.getSimpleName();

    private Messenger activityMessenger;
    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    LogUtils.d(TAG, msg.toString());
                    if (activityMessenger != null) {
                        Message message = new Message();
                        message.what = 2;
                        message.obj = "0101,07收到！";
                        try {
                            activityMessenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    activityMessenger = (Messenger) msg.obj;
                    LogUtils.d(TAG, "已经获取到Activity发送的Messenger对象");
                    break;
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = messenger.getBinder();
        return binder;
    }
}
