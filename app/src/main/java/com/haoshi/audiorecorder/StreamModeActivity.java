package com.haoshi.audiorecorder;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.Constant;
import com.haoshi.utils.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StreamModeActivity extends BaseActivity {

    private static final int BUFFER_SIZE = 2048;
    private TextView textResult;
    private Button buttonSpeak;
    private ExecutorService executorService;
    private File audioFile;
    private FileOutputStream fileOutputStream;
    private AudioRecord audioRecord;
    private byte[] buffer;
    private long startRecordTime;
    private volatile boolean isRecording;
    private volatile boolean isPlaying;
    private Handler handler = new Handler();

    @Override
    public void initView() {
        textResult = (TextView) findViewById(R.id.text_speak_result);
        buttonSpeak = (Button) findViewById(R.id.button_speak);
        buttonSpeak.setOnClickListener(this);
        findViewById(R.id.button_play).setOnClickListener(this);
    }

    @Override
    public void setData() {
        executorService = Executors.newSingleThreadExecutor();
        buffer = new byte[BUFFER_SIZE];
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_stream_mode;
    }

    @Override
    public String setTitle() {
        return TAG = StreamModeActivity.class.getSimpleName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_speak:
                if (isRecording) {
                    stop();
                } else {
                    start();
                }
                break;
            case R.id.button_play:
                play();
                break;
        }
    }

    private void start() {
        buttonSpeak.setText("停止录音");
        isRecording = true;
        executorService.submit(() -> {
            if (!doStart()) {
                recordFail();
            }
        });
    }

    private void stop() {
        buttonSpeak.setText("开始录音");
        isRecording = false;
    }

    private boolean doStart() {
        try {
            //创建录音文件
            audioFile = new File(Constant.ROOT_DIR + "/sound/" + System.currentTimeMillis() + ".pcm");
            audioFile.getParentFile().mkdirs();
            audioFile.createNewFile();
            //创建文件输出流
            fileOutputStream = new FileOutputStream(audioFile);
            //配置AudioRecord
            //从麦克风采集
            int audioSource = MediaRecorder.AudioSource.MIC;
            //所有Android设备都支持的频率
            int sampleRate = 44100;
            //输入单声道
            int channelConfig = AudioFormat.CHANNEL_IN_MONO;
            //PCM16所有android设备都支持
            int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
            //计算AudioRecord内部buffer的大小
            int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
            audioRecord = new AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, Math.max(minBufferSize, BUFFER_SIZE));
            audioRecord.startRecording();
            startRecordTime = System.currentTimeMillis();
            while (isRecording) {
                int read = audioRecord.read(buffer, 0, BUFFER_SIZE);
                if (read > 0) {
                    fileOutputStream.write(buffer, 0, read);
                } else {
                    return false;
                }
            }

            return doStop();

        } catch (IOException | RuntimeException e) {
            return false;
        } finally {
            if (audioRecord != null) {
                audioRecord.release();
            }
        }
    }

    private boolean doStop() {
        try {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            long time = (System.currentTimeMillis() - startRecordTime) / 1000;
            if (time > 3) {
                handler.post(() -> textResult.setText(textResult.getText() + "\n" + "录音" + time + "s"));
            } else {
                handler.post(() -> ToastUtils.showShort(StreamModeActivity.this, "时间太短"));
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private void recordFail() {
        handler.post(() -> {
            ToastUtils.showShort(StreamModeActivity.this, "录音失败");
            buttonSpeak.setText("开始录音");
            isRecording = false;
        });
    }

    private void play() {
        if (audioFile != null && !isPlaying) {
            isPlaying = true;
            executorService.submit(() -> startPlay());
        }
    }

    private void startPlay() {
        //声音类型：音乐
        int streamType = AudioManager.STREAM_MUSIC;
        //录音和播放使用同样的采样频率
        int sampleRate = 44100;
        //输出单声道
        int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        //数据位宽，和录音一样
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        //流模式
        int mode = AudioTrack.MODE_STREAM;
        int minBufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat);
        AudioTrack audioTrack = new AudioTrack(streamType, sampleRate, channelConfig, audioFormat, Math.max(BUFFER_SIZE, minBufferSize), mode);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(audioFile);
            int read;
            audioTrack.play();
            while ((read = fileInputStream.read(buffer)) > 0) {
                int ret = audioTrack.write(buffer, 0, read);
                switch (ret) {
                    case AudioTrack.ERROR_BAD_VALUE:
                    case AudioTrack.ERROR_DEAD_OBJECT:
                    case AudioTrack.ERROR_INVALID_OPERATION:
                        playFail();
                        return;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            playFail();
        } finally {
            isPlaying = false;
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                audioTrack.stop();
                audioTrack.release();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void playFail() {
        Log.d("================","error");
        audioFile = null;
        handler.post(() -> ToastUtils.showShort(StreamModeActivity.this, "播放失败"));
    }
}
