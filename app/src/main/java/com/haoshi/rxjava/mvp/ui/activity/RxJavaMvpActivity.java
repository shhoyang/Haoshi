package com.haoshi.rxjava.mvp.ui.activity;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.haoshi.R;
import com.haoshi.hao.Constant;
import com.haoshi.rxjava.mvp.common.base.BaseActivity;
import com.haoshi.rxjava.mvp.common.baserx.RxBus;
import com.haoshi.rxjava.mvp.common.widget.StatusBarCompat;
import com.haoshi.rxjava.mvp.ui.adapter.NewsFragmentAdapter;
import com.haoshi.rxjava.mvp.ui.event.ChangeEvent;
import com.haoshi.rxjava.mvp.ui.event.TopEvent;
import com.haoshi.rxjava.mvp.ui.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RxJavaMvpActivity extends BaseActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    private NewsFragmentAdapter adapter;
    private List<Fragment> fragments;
    private int currentIndex;
    private boolean isGrid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_java_mvp;
    }

    @Override
    protected void initPM() {

    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fab = (FloatingActionButton) findViewById(R.id.button);

        toolbar.setTitle("RxJavaMvpActivity");
        setSupportActionBar(toolbar);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(Color.WHITE, getResources().getColor(R.color.colorAccent));
        fragments = new ArrayList<>(Constant.CHANNELS.length);
        for (int i = 0; i < Constant.CHANNELS_KEY.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(Constant.CHANNELS[i]));
            NewsFragment fragment = new NewsFragment();
            Bundle b = new Bundle();
            b.putString("type", Constant.CHANNELS_KEY[i]);
            fragment.setArguments(b);
            fragments.add(fragment);
        }
        adapter = new NewsFragmentAdapter(
                getSupportFragmentManager(),
                fragments,
                Arrays.asList(Constant.CHANNELS));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        fab.setOnClickListener(v ->
                RxBus.getInstance()
                        .send(new TopEvent(Constant.CHANNELS_KEY[currentIndex])));
        /**
         * 当SwipeRefreshedLayout子视图包含ViewPager时需要处理滑动冲突
         */
//        viewPager.setOnTouchListener((v, event) -> {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_MOVE:
//                    RxBus.getInstance().send(new SwipeEvent(false));
//                    break;
//                case MotionEvent.ACTION_UP:
//                case MotionEvent.ACTION_CANCEL:
//                    RxBus.getInstance().send(new SwipeEvent(true));
//                    break;
//            }
//            return false;
//        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int pos = position % 4;
                ArgbEvaluator evaluator = new ArgbEvaluator();
                if (0 < pos && pos < 1) {
                    setColor(evaluator, positionOffset, R.color.tab_blue, R.color.tab_green);
                }
                if (1 < pos && pos < 2) {
                    setColor(evaluator, positionOffset, R.color.tab_purple, R.color.tab_blue);
                }
                if (2 < pos && pos < 3) {
                    setColor(evaluator, positionOffset, R.color.tab_pink, R.color.tab_purple);
                }
                if (3 < pos && pos < 4) {
                    setColor(evaluator, positionOffset, R.color.tab_brown, R.color.tab_pink);
                }
                if (pos == 0) {
                    setColor(evaluator, positionOffset, R.color.tab_green, R.color.tab_blue);
                }
                if (pos == 1) {
                    setColor(evaluator, positionOffset, R.color.tab_blue, R.color.tab_purple);
                }
                if (pos == 2) {
                    setColor(evaluator, positionOffset, R.color.tab_purple, R.color.tab_pink);
                }
                if (pos == 3) {
                    setColor(evaluator, positionOffset, R.color.tab_pink, R.color.tab_brown);
                }

            }

            @Override
            public void onPageSelected(int position) {
                changeShowStatus();
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            public void setColor(ArgbEvaluator evaluator, float positionOffset, int color, int nextColor) {
                tabLayout.setBackgroundColor(getResources().getColor(color));
                toolbar.setBackgroundColor(getResources().getColor(color));
                StatusBarCompat.setStatusBarColor(RxJavaMvpActivity.this,getResources().getColor(color));
                int evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(color), getResources().getColor(nextColor));
                tabLayout.setBackgroundColor(evaluate);
                toolbar.setBackgroundColor(evaluate);
                StatusBarCompat.setStatusBarColor(RxJavaMvpActivity.this,evaluate);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(isGrid ? R.menu.menu_list : R.menu.menu_grid, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_grid) {
            isGrid = true;
        } else {
            isGrid = false;
        }
        changeShowStatus();
        return super.onOptionsItemSelected(item);
    }

    private void changeShowStatus() {
        invalidateOptionsMenu();
        RxBus.getInstance().send(new ChangeEvent(isGrid));
    }
}
