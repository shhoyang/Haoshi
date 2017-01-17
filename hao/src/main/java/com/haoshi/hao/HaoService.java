package com.haoshi.hao;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.haoshi.IAidlInterface;

/**
 * @author: HaoShi
 */

public class HaoService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IAidlInterface.Stub() {
            @Override
            public String getInfo(String info) throws RemoteException {
                if ("hello".equals(info)) {
                    return "hello haoshi";
                }
                return "error";
            }
        };
    }
}
