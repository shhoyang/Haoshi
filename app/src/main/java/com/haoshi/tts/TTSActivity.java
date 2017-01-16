package com.haoshi.tts;

import android.media.AudioManager;
import android.view.View;
import android.widget.EditText;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.L;

public class TTSActivity extends BaseActivity implements SpeechSynthesizerListener {

    private EditText editText;
    private SpeechSynthesizer speechSynthesizer;

    @Override
    public void initView() {
        editText = (EditText) findViewById(R.id.edit);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void setData() {
        initTTS();
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_tts;
    }

    @Override
    public String setTitle() {
        return TAG = TTSActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                String content = editText.getText().toString();
                if (content != null) {
                    speechSynthesizer.cancel();
                    int speak = speechSynthesizer.speak(content);
                    if (speak != -1) {
                        L.e(TAG, speak);
                    }

                }
                break;
        }
    }

    private void initTTS() {
        speechSynthesizer = new SpeechSynthesizer(getApplicationContext(), "holder", this);
        speechSynthesizer.setApiKey("UV2Y9oeCzl7UvSqent7ODOfy", "e3f034994bdcb7d34c6fefbddeef03d3");
        speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE, SpeechSynthesizer.AUDIO_ENCODE_AMR);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE, SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);
    }

    @Override
    public void onStartWorking(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechStart(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onNewDataArrive(SpeechSynthesizer speechSynthesizer, byte[] bytes, boolean b) {

    }

    @Override
    public void onBufferProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

    }

    @Override
    public void onSpeechProgressChanged(SpeechSynthesizer speechSynthesizer, int i) {

    }

    @Override
    public void onSpeechPause(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechResume(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onCancel(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSynthesizeFinish(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onSpeechFinish(SpeechSynthesizer speechSynthesizer) {

    }

    @Override
    public void onError(SpeechSynthesizer speechSynthesizer, SpeechError speechError) {
        L.e(TAG, speechError.toString());
    }
}
