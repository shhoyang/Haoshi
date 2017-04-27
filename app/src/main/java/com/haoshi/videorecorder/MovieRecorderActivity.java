package com.haoshi.videorecorder;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.ToastUtils;
import com.haoshi.videorecorder.view.MovieRecorderView;

import java.io.File;
import java.io.IOException;

public class MovieRecorderActivity extends BaseActivity implements View.OnTouchListener {

    //录制进度
    private static final int RECORD_PROGRESS = 100;
    //录制结束
    private static final int RECORD_FINISH = 101;
    private static final int RECORD_MAX_TIME = 10;


    private MovieRecorderView movieRecorderView;
    private Button buttonShoot;
    private RelativeLayout relativeBottom;
    private TextView textTime, textUpToCancel, textReleaseToCancel;
    //进度条
    private TextView progress;
    //进度条动画
    private ScaleAnimation scaleAnimation;
    //按下的位置
    private float startY;
    //是否结束录制
    private boolean isFinish = true;
    //是否触摸在松开取消的状态
    private boolean isTouchOnUpToCancel = false;
    //当前进度
    private int currentTime = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECORD_PROGRESS:
                    if (currentTime < 10) {
                        textTime.setText("00:0" + currentTime);
                    } else {
                        textTime.setText("00:" + currentTime);
                    }
                    break;
                case RECORD_FINISH:
                    if (isTouchOnUpToCancel) {//录制结束，还在上移删除状态没有松手，就复位录制
                        resetData(true);
                    } else if (!isFinish) {//录制结束，在正常位置，录制完成跳转页面
                        isFinish = true;
                        buttonShoot.setEnabled(false);
                        finishActivity();
                    }
                    break;
            }
        }
    };

    @Override
    public void initView() {
        movieRecorderView = (MovieRecorderView) findViewById(R.id.movie_recorder);
        relativeBottom = (RelativeLayout) findViewById(R.id.relative_bottom);
        buttonShoot = (Button) findViewById(R.id.button_shoot);
        textTime = (TextView) findViewById(R.id.text_time);
        textUpToCancel = (TextView) findViewById(R.id.text_up_to_cancel);
        textReleaseToCancel = (TextView) findViewById(R.id.text_release_to_cancel);
        progress = (TextView) findViewById(R.id.progress);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        FrameLayout.LayoutParams movieRecorderViewLayoutParams = (FrameLayout.LayoutParams) movieRecorderView.getLayoutParams();
        movieRecorderViewLayoutParams.height = width * 4 / 3;//根据屏幕宽度设置预览控件的尺寸，为了解决预览拉伸问题
        movieRecorderView.setLayoutParams(movieRecorderViewLayoutParams);
        movieRecorderView.setOnRecordProgressListener((maxTime, currentTime1) -> {
            MovieRecorderActivity.this.currentTime = currentTime1;
            handler.sendEmptyMessage(RECORD_PROGRESS);
        });
        movieRecorderView.setRecordMaxTime(RECORD_MAX_TIME);

        LinearLayout.LayoutParams relativeBottomLayoutParams = (LinearLayout.LayoutParams) relativeBottom.getLayoutParams();
        relativeBottomLayoutParams.height = width / 3 * 2;
        relativeBottom.setLayoutParams(relativeBottomLayoutParams);

        buttonShoot.setOnTouchListener(this);
    }

    @Override
    public void setData() {
        scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(RECORD_MAX_TIME * 1000);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_movie_recorder;
    }

    @Override
    public String setTitle() {
        return TAG = MovieRecorderActivity.class.getSimpleName();
    }


    @Override
    protected void onStop() {
        super.onStop();
        movieRecorderView.stop();
    }

    @Override
    public void onDestroy() {
        //退出界面删除文件，如果要删除文件夹，需要提供文件夹路径
        super.onDestroy();
        if (movieRecorderView.getRecordFile() != null) {
            File file = new File(movieRecorderView.getRecordFile().getAbsolutePath());
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                doStart(motionEvent);
                break;
            case MotionEvent.ACTION_UP:
                doStop(motionEvent);
                break;
            case MotionEvent.ACTION_MOVE:
                move(motionEvent);
                break;
            case MotionEvent.ACTION_CANCEL:
                resetData(true);
                break;

        }
        return true;
    }

    /**
     * 开始录制
     *
     * @param motionEvent
     */
    private void doStart(MotionEvent motionEvent) {
        //缩小按钮
        buttonShoot.animate().scaleX(0.8f).scaleY(0.8f).setDuration(500).start();
        //提示上移取消
        textUpToCancel.setVisibility(View.VISIBLE);
        //开始录制
        isFinish = false;
        //记录按下的坐标
        startY = motionEvent.getY();
        progress.startAnimation(scaleAnimation);
        movieRecorderView.record(() -> handler.sendEmptyMessage(RECORD_FINISH));
    }

    /**
     * 结束录制
     *
     * @param motionEvent
     */
    private void doStop(MotionEvent motionEvent) {
        //放大按钮
        textUpToCancel.setVisibility(View.GONE);
        textReleaseToCancel.setVisibility(View.GONE);

        if (startY - motionEvent.getY() > 100) {//上移超过一定距离取消录制，删除文件
            if (!isFinish) {
                resetData(true);
            }
        } else {
            if (movieRecorderView.getTimeCount() > 3) {//录制时间超过三秒，录制完成
                handler.sendEmptyMessage(RECORD_FINISH);
            } else {//时间不足取消录制，删除文件
                ToastUtils.showShort(this, "视频录制时间太短");
                resetData(true);
            }
        }
    }

    /**
     * 手指移动
     *
     * @param motionEvent
     */
    private void move(MotionEvent motionEvent) {
        //根据触摸上移状态切换提示
        if (startY - motionEvent.getY() > 100) {
            isTouchOnUpToCancel = true;//触摸在松开就取消的位置
            if (textUpToCancel.getVisibility() == View.VISIBLE) {
                textUpToCancel.setVisibility(View.GONE);
                textReleaseToCancel.setVisibility(View.VISIBLE);
            }
        } else {
            isTouchOnUpToCancel = false;//触摸在正常录制的位置
            if (textUpToCancel.getVisibility() == View.GONE) {
                textUpToCancel.setVisibility(View.VISIBLE);
                textReleaseToCancel.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 重置状态
     */
    private void resetData(boolean isDelete) {
        if (movieRecorderView.getRecordFile() != null && isDelete) {
            movieRecorderView.getRecordFile().delete();
        }
        buttonShoot.animate().scaleX(1).scaleY(1).setDuration(500).start();
        movieRecorderView.stop();
        isFinish = true;
        currentTime = 0;
        textTime.setText("00:00");
        buttonShoot.setEnabled(true);
        textUpToCancel.setVisibility(View.GONE);
        textReleaseToCancel.setVisibility(View.GONE);
        scaleAnimation.cancel();
        try {
            movieRecorderView.initCamera();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finishActivity() {
        if (isFinish) {
            Intent intent = new Intent(this, VideoPreviewActivity.class);
            intent.putExtra("path", movieRecorderView.getRecordFile().getAbsolutePath());
            startActivity(intent);
            resetData(false);
        }
    }
}
