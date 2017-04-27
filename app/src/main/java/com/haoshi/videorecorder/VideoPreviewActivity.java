package com.haoshi.videorecorder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPreviewActivity extends BaseActivity {

    private VideoView videoView;
    private ImageView imageShow;
    private Button buttonDone, buttonPlay;
    private RelativeLayout relativeBottom;
    private TextView textTime;
    private ProgressBar progressBar;
    //视频路径
    private String path;
    //视频时间
    private int time;
    private int currentTime;
    private Timer timer;
    //视频文件
    private File file;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            time = (videoView.getDuration() + 1000) / 1000;
            currentTime = (videoView.getCurrentPosition() + 1500) / 1000;
            progressBar.setMax(videoView.getDuration());
            progressBar.setProgress(videoView.getCurrentPosition());
            if (currentTime < 10) {
                textTime.setText("00:0" + currentTime);
            } else {
                textTime.setText("00:" + currentTime);
            }
            //达到指定时间，停止播放
            if (!videoView.isPlaying() && time > 0) {
                progressBar.setProgress(videoView.getDuration());
                if (timer != null) {
                    timer.cancel();
                }
            }
        }
    };

    @Override
    public void initView() {
        videoView = (VideoView) findViewById(R.id.video);
        imageShow = (ImageView) findViewById(R.id.image_show);
        relativeBottom = (RelativeLayout) findViewById(R.id.relative_bottom);
        buttonDone = (Button) findViewById(R.id.button_done);
        buttonPlay = (Button) findViewById(R.id.button_play);
        textTime = (TextView) findViewById(R.id.text_time);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        FrameLayout.LayoutParams videoViewLayoutParams = (FrameLayout.LayoutParams) videoView.getLayoutParams();
        videoViewLayoutParams.height = width * 4 / 3;//根据屏幕宽度设置预览控件的尺寸，为了解决预览拉伸问题
        videoView.setLayoutParams(videoViewLayoutParams);
        imageShow.setLayoutParams(videoViewLayoutParams);

        LinearLayout.LayoutParams relativeBottomLayoutParams = (LinearLayout.LayoutParams) relativeBottom.getLayoutParams();
        relativeBottomLayoutParams.height = width / 3 * 2;
        relativeBottom.setLayoutParams(relativeBottomLayoutParams);

        textTime.setText("00:00");
        buttonDone.setOnClickListener(this);
        buttonPlay.setOnClickListener(this);

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
    }

    @Override
    public void setData() {
        Intent intent = getIntent();
        if (intent != null) {
            path = intent.getExtras().getString("path", "");
            file = new File(path);
        }

        //获取第一帧图片，预览使用
        if (file.length() != 0) {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(path);
            Bitmap bitmap = media.getFrameAtTime();
            imageShow.setImageBitmap(bitmap);
        }
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_video_preview;
    }

    @Override
    public String setTitle() {
        return TAG = VideoPreviewActivity.class.getSimpleName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_play:
                playVideo();
                break;
            case R.id.button_done:
                finish();
                break;
        }
    }

    /**
     * 播放视频
     */
    private void playVideo() {
        textTime.setText("00:00");
        progressBar.setProgress(0);
        buttonPlay.setVisibility(View.GONE);

        //视频控制面板，不需要可以不设置
        //MediaController controller = new MediaController(this);
        //controller.setVisibility(View.GONE);
        //videoView.setMediaController(controller);
        videoView.setVideoPath(path);
        videoView.start();

        currentTime = 0;//时间计数器重新赋值
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, 0, 100);
    }
}
