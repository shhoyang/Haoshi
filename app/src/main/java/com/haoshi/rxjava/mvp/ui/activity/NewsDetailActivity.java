package com.haoshi.rxjava.mvp.ui.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.haoshi.R;
import com.haoshi.rxjava.mvp.bean.Data;
import com.haoshi.rxjava.mvp.common.base.BaseActivity;
import com.haoshi.rxjava.mvp.ui.contract.NewsDetailContract;
import com.haoshi.rxjava.mvp.ui.model.NewsDetailModel;
import com.haoshi.rxjava.mvp.ui.presenter.NewsDetailPresenter;

public class NewsDetailActivity extends BaseActivity<NewsDetailContract.Presenter, NewsDetailContract.Model>
        implements NewsDetailContract.View, View.OnTouchListener {
    private ImageView iv_header;
    private WebView webView;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton fab;
    private CoordinatorLayout rootLayout;

    private Data data;
    private float startX, startY;
    private boolean isBack;
    private long time;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initPM() {
        model = new NewsDetailModel();
        presenter = new NewsDetailPresenter(this, model);
    }

    @Override
    protected void initView() {
        //StatusBarCompat.translucentStatusBar(this);
        iv_header = (ImageView) findViewById(R.id.iv_header);
        webView = (WebView) findViewById(R.id.wv_content);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolBarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        rootLayout = (CoordinatorLayout) findViewById(R.id.root);

        data = (Data) getIntent().getSerializableExtra("data");
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = toolbar.getNavigationIcon();
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        toolbarLayout.setExpandedTitleColor(Color.WHITE);
        toolbarLayout.setTitle(data.getTitle().trim());

        refreshLayout.setEnabled(false);
        refreshLayout.setColorSchemeColors(new int[]{R.color.tab_blue, R.color.tab_purple, R.color.tab_pink});

        fab.setOnClickListener(e -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, data.getUrl());
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "分享到"));
        });

        Glide.with(this)
                .load(data.getThumbnail_pic_s())
                .crossFade()
                .into(iv_header);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        webView.loadUrl(data.getUrl());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                refreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    protected void setBackGesture() {
        rootLayout.setOnTouchListener(this);
        webView.setOnTouchListener(this);
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void finishLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                time = System.currentTimeMillis();
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX();
                float deltaY = event.getY();
                float qHalfWidth = rootLayout.getWidth() / 5;
                if (Math.abs(deltaX - startX) > Math.abs(deltaY - startY)
                        && (Math.abs(deltaX - startX) > qHalfWidth)) {
                    isBack = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isBack && System.currentTimeMillis() - time < 1000) {
                    finish();
                } else {
                    isBack = false;
                }
                break;
        }
        return false;
    }
}
