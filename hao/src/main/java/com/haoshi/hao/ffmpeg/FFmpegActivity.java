package com.haoshi.hao.ffmpeg;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.haoshi.hao.R;
import com.haoshi.hao.base.BaseActivity;
import com.haoshi.hao.utils.LogUtils;
import com.haoshi.hao.utils.ToastUtils;
import com.haoshi.hao.view.RingProgressBar;

import java.io.File;

import mabeijianxi.camera.LocalMediaCompress;
import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.VCamera;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.LocalMediaConfig;
import mabeijianxi.camera.model.MediaRecorderConfig;
import mabeijianxi.camera.model.OnlyCompressOverBean;
import mabeijianxi.camera.util.DeviceUtils;

public class FFmpegActivity extends BaseActivity {

    private static final int VIDEO_WIDTH = 640;
    private static final int VIDEO_HEIGHT = 480;

    private RingProgressBar ringProgressBar;
    private Handler handler = new Handler();

    @Override
    public void initView() {

        ringProgressBar = (RingProgressBar) findViewById(R.id.progress);
        ringProgressBar.setValue(100, 100);

        findViewById(R.id.text_start).setOnClickListener(this);
        findViewById(R.id.text_compress).setOnClickListener(this);

    }

    @Override
    public void setData() {
        //获取相机目录
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/video/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/", "/sdcard-ext/") + "/video/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/video/");
        }
        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄SDK，必须
        VCamera.initialize(this);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_ffmpeg;
    }

    @Override
    public String setTitle() {
        return TAG = FFmpegActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_start:
                start();
                break;
            case R.id.text_compress:
                compress();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ringProgressBar.clearBitmap();
    }

    private void start() {
        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                .doH264Compress(new AutoVBRMode())
                .setMediaBitrateConfig(new AutoVBRMode())
                .smallVideoWidth(VIDEO_WIDTH)
                .smallVideoHeight(VIDEO_HEIGHT)
                .recordTimeMax(10 * 1000)
                .recordTimeMin(2 * 1000)
                .maxFrameRate(20)
                .captureThumbnailsTime(1)
                .build();
        MediaRecorderActivity.goSmallVideoRecorder(this, FFmpegVideoPreviewActivity.class.getName(), config);
    }

    private void compress() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    doCompress(uri.getPath());
                }
            }
        }
    }

    private void doCompress(String path) {
        new Thread(() -> {
            handler.post(() -> ToastUtils.showShort(FFmpegActivity.this, "压缩中"));

            LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
            LocalMediaConfig config = buidler
                    .setVideoPath(path)
                    .captureThumbnailsTime(1)
                    .doH264Compress(new AutoVBRMode())
                    .setFramerate(0)
                    .build();
            OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config).startCompress();
            handler.post(() -> ToastUtils.showLong(FFmpegActivity.this, "压缩完成,视频位置" + onlyCompressOverBean.getVideoPath()));
        }).start();
    }
}
