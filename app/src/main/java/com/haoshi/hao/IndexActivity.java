package com.haoshi.hao;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.swipelayout.SwipeActivity;
import com.haoshi.dialog.DialogActivity;
import com.haoshi.listview.ListViewActivity;
import com.haoshi.mvp.activity.MvpActivity;
import com.haoshi.rxjava.RxJavaActivity;
import com.haoshi.scrollview.ScrollActivity;
import com.haoshi.service.ServiceActivity;
import com.haoshi.sqlite.SqliteActivity;
import com.haoshi.tts.TTSActivity;
import com.haoshi.utils.ScreenUtils;
import com.haoshi.view.MarqueeTextView;
import com.haoshi.view.ViewActivity;
import com.haoshi.webview.JavaJsActivity;

/**
 * @author: HaoShi
 */

public class IndexActivity extends BaseActivity {

    private MarqueeTextView marqueeTextView;

    @Override
    public void initView() {

        marqueeTextView = (MarqueeTextView) findViewById(R.id.marquee);
        marqueeTextView.setText("工作中收集的资源,与同仁共享");
        marqueeTextView.setSpeed(ScreenUtils.getScreenWidth(this) / 300);
        marqueeTextView.setFontColor("#FFFFFF");
        marqueeTextView.init(getWindowManager());
        marqueeTextView.setOnMarqueeCompleteListener(new MarqueeTextView.OnMarqueeCompleteListener() {
            @Override
            public void onMarqueeComplete() {
                //T.showLong(IndexActivity.this, "结束");
            }
        });

        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.button10).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        marqueeTextView.startScroll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        marqueeTextView.stopScroll();
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_index;
    }

    @Override
    public String setTitle() {
        TAG = IndexActivity.class.getSimpleName();
        return "豪〤世";
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, RxJavaActivity.class);
                break;
            case R.id.button1:
                intent = new Intent(this, TTSActivity.class);
                break;
            case R.id.button2:
                intent = new Intent(this, MvpActivity.class);
                break;
            case R.id.button3:
                intent = new Intent(this, ViewActivity.class);
                break;
            case R.id.button4:
                intent = new Intent(this, DialogActivity.class);
                break;
            case R.id.button5:
                intent = new Intent(this, ListViewActivity.class);
                break;
            case R.id.button6:
                intent = new Intent(this, ServiceActivity.class);
                break;
            case R.id.button7:
                intent = new Intent(this, SqliteActivity.class);
                break;
            case R.id.button8:
                intent = new Intent(this, ScrollActivity.class);
                break;
            case R.id.button9:
                intent = new Intent(this, SwipeActivity.class);
                break;
            case R.id.button10:
                intent = new Intent(this, JavaJsActivity.class);
                break;
        }
        startActivity(intent);
    }
}
