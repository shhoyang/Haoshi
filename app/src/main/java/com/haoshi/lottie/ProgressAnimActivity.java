package com.haoshi.lottie;

import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

public class ProgressAnimActivity extends BaseActivity {

    private TextView textProgress;
    private LottieAnimationView animationView;

    @Override
    public void initView() {
        textProgress = (TextView) findViewById(R.id.text_progress);
        animationView = (LottieAnimationView) findViewById(R.id.lottie_view);
        LottieComposition.Factory.fromAssetFileName(this, "lottie_logo.json", composition -> {
            animationView.setComposition(composition);
            animationView.setProgress(0.333f);
            animationView.playAnimation();
        });

        findViewById(R.id.button_start).setOnClickListener(this);
        findViewById(R.id.button_stop).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_progress_anim;
    }

    @Override
    public String setTitle() {
        return TAG = ProgressAnimActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_start:
                startAnim();
                break;
            case R.id.button_stop:
                stopAnim();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        animationView.cancelAnimation();
    }

    private void startAnim() {
        boolean isPlaying = animationView.isAnimating();
        if (!isPlaying) {
            animationView.setProgress(0f);
            animationView.playAnimation();
        }
    }

    private void stopAnim() {
        boolean isPlaying = animationView.isAnimating();
        if (isPlaying) {
            animationView.cancelAnimation();
        }
    }
}
