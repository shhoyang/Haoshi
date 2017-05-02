package com.haoshi.audiorecorder;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.Constant;
import com.haoshi.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileModeActivity extends BaseActivity implements View.OnTouchListener {

    private TextView textResult, textSpeak;
    private LinearLayout linearMic;
    private ImageView imageMic;
    private ExecutorService executorService;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private File audioFile;
    private long startRecordTime;
    private volatile boolean isPlaying;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateMic();
        }
    };

    @Override
    public void initView() {
        textResult = (TextView) findViewById(R.id.text_speak_result);
        textSpeak = (TextView) findViewById(R.id.text_speak);
        linearMic = (LinearLayout) findViewById(R.id.linear_mic);
        imageMic = (ImageView) findViewById(R.id.image_mic);
        textSpeak.setOnTouchListener(this);
        findViewById(R.id.text_play).setOnClickListener(this);
    }

    @Override
    public void setData() {
        //录音JNI函数不具备线程安全性
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_file_mode;
    }

    @Override
    public String setTitle() {
        return TAG = FileModeActivity.class.getSimpleName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
        releaseRecorder();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.text_play:
                play();
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startRecord();
                break;
            case MotionEvent.ACTION_UP:
                stopRecord();
                break;
        }
        return true;
    }

    private void updateMic() {
        if (mediaRecorder != null) {
            double ratio = (double) mediaRecorder.getMaxAmplitude();
            double db = 0;// 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
                imageMic.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
            }
            handler.sendEmptyMessageDelayed(0, 100);
        }
    }

    /**
     * 按下说话
     */
    private void startRecord() {
        linearMic.setVisibility(View.VISIBLE);
        textSpeak.setText("正在说话...");
        textSpeak.setBackgroundColor(Color.parseColor("#ff99cc00"));
        executorService.submit(() -> {
            //释放之前的录音
            releaseRecorder();
            if (!doStart()) {
                recordFail();
            }
        });
    }

    /**
     * 松开
     */
    private void stopRecord() {
        linearMic.setVisibility(View.GONE);
        textSpeak.setText("按下录音");
        textSpeak.setBackgroundColor(Color.parseColor("#ff33b5e5"));
        executorService.submit(() -> {
            if (!doStop()) {
                recordFail();
            }

            releaseRecorder();
        });
    }

    /**
     * 开始录音逻辑
     *
     * @return
     */
    private boolean doStart() {
        try {
            //创建MediaRecorder
            mediaRecorder = new MediaRecorder();
            //创建录音文件
            audioFile = new File(Constant.ROOT_DIR + "/sound/" + System.currentTimeMillis() + ".m4a");
            audioFile.getParentFile().mkdirs();
            audioFile.createNewFile();
            //从麦克风采集
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //保存为MP4格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //所有Android设备都支持的采样频率
            mediaRecorder.setAudioSamplingRate(44100);
            //通用的AAC编码格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //音质比较好的频率
            mediaRecorder.setAudioEncodingBitRate(96000);
            //录音文件位置
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            //开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
            startRecordTime = System.currentTimeMillis();
            //记录时间
            handler.sendEmptyMessage(0);
        } catch (IOException | RuntimeException e) {
            return false;
        }

        return true;
    }

    /**
     * 停止录音逻辑
     *
     * @return
     */
    private boolean doStop() {
        try {
            mediaRecorder.stop();
            long time = (System.currentTimeMillis() - startRecordTime) / 1000;
            if (time > 3) {
                handler.post(() -> textResult.setText(textResult.getText() + "\n" + "录音" + time + "s"));
            } else {
                handler.post(() -> ToastUtils.showShort(FileModeActivity.this, "时间太短"));
            }
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    /**
     * 释放MediaRecorder
     */
    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    /**
     * 录音错误的处理
     */
    private void recordFail() {
        audioFile = null;

        handler.post(() -> ToastUtils.showShort(FileModeActivity.this, "录音失败"));
    }

    /**
     * 播放
     */
    private void play() {
        if (audioFile != null && !isPlaying) {
            isPlaying = true;
            executorService.submit(() -> startPlay());
        }
    }

    /**
     * 开始播放逻辑
     */
    private void startPlay() {
        try {
            mediaPlayer = new MediaPlayer();
            //设置声音文件
            mediaPlayer.setDataSource(audioFile.getAbsolutePath());
            //播放结束监听
            mediaPlayer.setOnCompletionListener(mediaPlayer1 -> {
                stopPlay();
            });
            //播放错误监听
            mediaPlayer.setOnErrorListener((mediaPlayer12, i, i1) -> {
                playFail();
                return true;
            });
            //左右声道
            mediaPlayer.setVolume(1, 1);
            //是否循环
            mediaPlayer.setLooping(false);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException | RuntimeException e) {
            playFail();
        }
    }


    /**
     * 停止播放逻辑
     */
    private void stopPlay() {
        isPlaying = false;
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.setOnErrorListener(null);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 播放错误
     */
    private void playFail() {
        stopPlay();
        audioFile = null;
        handler.post(() -> ToastUtils.showShort(FileModeActivity.this, "播放失败"));
    }
}
