package com.haoshi.baidumap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * @author Haoshi
 */

public class OrientationListener implements SensorEventListener {

    private Context context;
    private OnOrientationListener onOrientationListener;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float lastX;

    public OrientationListener(Context context, OnOrientationListener onOrientationListener) {
        this.context = context;
        this.onOrientationListener = onOrientationListener;
    }

    public void start() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float x = sensorEvent.values[SensorManager.DATA_X];
            if (Math.abs(lastX - x) > 1) {
                if (onOrientationListener != null) {
                    onOrientationListener.onOrientationChange(x);

                }
            }
            lastX = x;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface OnOrientationListener {
        void onOrientationChange(float x);
    }
}
