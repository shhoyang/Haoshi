package com.haoshi.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.L;
import com.haoshi.utils.T;

import java.util.Set;

/**
 * @author HaoShi
 */
public class BluetoothActivity extends BaseActivity {

    private BluetoothAdapter bluetoothAdapter;

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void setData() {

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(receiver, filter);

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_bluetooth;
    }

    @Override
    public String setTitle() {
        return TAG = BluetoothActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                bluetoothAdapter.enable();
                Set<BluetoothDevice> set = bluetoothAdapter.getBondedDevices();
                for (BluetoothDevice bluetoothDevice : set) {
                    L.d(TAG, bluetoothDevice.getName() + ":" + bluetoothDevice.getAddress());
                }
                break;
            case R.id.button1:
                bluetoothAdapter.disable();
                break;
            case R.id.button2:
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();
                dialog.setMessage("正在搜索...");
                dialog.show();
                break;
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    L.d(TAG, device.getName() + ":" + device.getAddress());
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                dialog.dismiss();
                T.showShort(BluetoothActivity.this, "搜索已完成");
            }
        }
    };
}
