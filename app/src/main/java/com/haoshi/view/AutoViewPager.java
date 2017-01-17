package com.haoshi.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.haoshi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: HaoShi
 */
public class AutoViewPager extends ViewPager {

    private List<ImageView> points = new ArrayList<>();
    private int interval = 3000;
    private int currentPage;
    private int previousPoint;
    private boolean isContinue = true;

    public AutoViewPager(Context context) {
        super(context);
        addOnPageChangeListener(new ViewPagerChange());
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(new ViewPagerChange());
        setOnTouchListener(new ViewPagerTouch());
    }

    public void setPoints(List<ImageView> points) {
        this.points = points;
    }

    public void setInterval(int second) {
        interval = 1000 * second;
    }

    class ViewPagerTouch implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                isContinue = true;
            } else {
                isContinue = false;
            }
            return false;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurrentItem(currentPage + 1);
        }
    };

    public Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (isContinue) {
                    handler.sendEmptyMessage(0);
                    SystemClock.sleep(interval);
                }
            }
        }
    });

    class ViewPagerChange implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            points.get(position % points.size()).setBackgroundResource(R.mipmap.select);
            points.get(previousPoint).setBackgroundResource(R.mipmap.normal);
            previousPoint = position % points.size();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
