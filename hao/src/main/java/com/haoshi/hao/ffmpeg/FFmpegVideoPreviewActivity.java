package com.haoshi.hao.ffmpeg;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.haoshi.hao.R;
import com.haoshi.hao.base.BaseActivity;
import com.haoshi.hao.utils.ScreenUtils;

import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.MediaRecorderBase;


public class FFmpegVideoPreviewActivity extends BaseActivity {

    private VideoView videoView;
    private ImageView imageShow;
    private Button buttonPlay;

    private String videoPath;

    @Override
    public void initView() {
        // 防止锁屏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        videoView = (VideoView) findViewById(R.id.video);

        int screenWidth = ScreenUtils.getScreenWidth(this);
        int videoHeight = (int) (screenWidth / (MediaRecorderBase.SMALL_VIDEO_WIDTH / (MediaRecorderBase.SMALL_VIDEO_HEIGHT * 1.0f)));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        layoutParams.height = videoHeight;
        videoView.setLayoutParams(layoutParams);

        imageShow = (ImageView) findViewById(R.id.image_show);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageShow.getLayoutParams();
        params.height = videoHeight;
        imageShow.setLayoutParams(params);
        buttonPlay = (Button) findViewById(R.id.button_play);

        videoView.setOnCompletionListener(mp -> {
            if (!videoView.isPlaying()) {
                imageShow.setVisibility(View.VISIBLE);
                buttonPlay.setVisibility(View.VISIBLE);
            }
        });
        videoView.setOnErrorListener((mediaPlayer, i, i1) -> true);

        videoView.setOnPreparedListener(mediaPlayer ->
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                        if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            imageShow.setVisibility(View.GONE);
                            return true;
                        }
                        return false;
                    }
                }));

        buttonPlay.setOnClickListener(view -> {
            buttonPlay.setVisibility(View.GONE);
            videoView.setVideoPath(videoPath);
            videoView.start();
        });
    }

    @Override
    public void setData() {
        Intent intent = getIntent();
        videoPath = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI);
        if (TextUtils.isEmpty(videoPath)) {
            finish();
        }

        String videoScreenshot = intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
        if (!TextUtils.isEmpty(videoScreenshot)) {
            imageShow.setImageBitmap(BitmapFactory.decodeFile(videoScreenshot));
        }
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_ffmpeg_video_preview;
    }

    @Override
    public String setTitle() {
        return TAG = FFmpegVideoPreviewActivity.class.getSimpleName();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }
}
