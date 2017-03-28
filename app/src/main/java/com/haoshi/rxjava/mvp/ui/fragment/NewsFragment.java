package com.haoshi.rxjava.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.haoshi.R;
import com.haoshi.rxjava.mvp.bean.Data;
import com.haoshi.rxjava.mvp.common.base.BaseFragment;
import com.haoshi.rxjava.mvp.ui.activity.NewsDetailActivity;
import com.haoshi.rxjava.mvp.ui.adapter.NewsAdapter;
import com.haoshi.rxjava.mvp.ui.contract.NewsContract;
import com.haoshi.rxjava.mvp.ui.event.ChangeEvent;
import com.haoshi.rxjava.mvp.ui.event.SwipeEvent;
import com.haoshi.rxjava.mvp.ui.event.TopEvent;
import com.haoshi.rxjava.mvp.ui.model.NewsModel;
import com.haoshi.rxjava.mvp.ui.presenter.NewsPresenter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * @author Haoshi
 */
public class NewsFragment extends BaseFragment<NewsContract.Presenter, NewsContract.Model>
        implements NewsContract.View, NewsAdapter.OnItemClickListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private List<Data> list;
    private NewsAdapter adapter, adapter_grid;
    private String channelName;
    private boolean isGrid;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initPM() {
        mModel = new NewsModel();
        mPresenter = new NewsPresenter(this, mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            channelName = getArguments().getString("type");
        }

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);
        refreshLayout.setColorSchemeResources(new int[]{R.color.tab_blue, R.color.tab_purple, R.color.tab_pink});
        refreshLayout.setOnRefreshListener(() -> mPresenter.getChannelList(channelName));
        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter = new NewsAdapter(getContext(), list, R.layout.adapter_news_list));
        adapter_grid = new NewsAdapter(getContext(), list, R.layout.adapter_news_grid);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.getItemAnimator().setAddDuration(800);

        adapter.setOnItemClickListener(this);
        adapter_grid.setOnItemClickListener(this);
        if (list.size() <= 0) {
            mPresenter.getChannelList(channelName);
            startLoading();
        }
    }

    /**
     * 处理RxBus发来的消息
     */
    @Override
    protected void handleEvent(Object o) {
        //回到顶部事件
        if (o instanceof TopEvent) {
            TopEvent event = (TopEvent) o;
            if (event.getType().equals(channelName)) {
                recyclerView.scrollToPosition(0);
            }
            //滑动冲突拦截事件
        } else if (o instanceof SwipeEvent) {
            SwipeEvent event = (SwipeEvent) o;
            refreshLayout.setEnabled(event.isEnable());
        } else if (o instanceof ChangeEvent) {
            ChangeEvent event = (ChangeEvent) o;
            List<Data> dataList = new ArrayList<>(list);
            isGrid = event.isGrid();
            recyclerView.setLayoutManager(isGrid ?
                    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    : new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(isGrid ? adapter_grid : adapter);
            returnChannelList(dataList);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle b = new Bundle();
        b.putSerializable("data", list.get(position));
        startActivity(NewsDetailActivity.class, b);
    }


    @Override
    public void startLoading() {
        if (!refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(true);
    }

    @Override
    public void finishLoading() {
        if (refreshLayout.isRefreshing())
            refreshLayout.setRefreshing(false);
    }

    @Override
    public void cancelLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void returnChannelList(List<Data> dataList) {
        if (isGrid) {
            List<Integer> heights = new ArrayList<>(dataList.size());
            for (int i = 0; i < dataList.size(); i++) {
                heights.add((int) (Math.random() * 400 + 300));
            }
            adapter_grid.setHeights(heights);
        }
        NewsAdapter mAdapter = isGrid ? adapter_grid : adapter;
        mAdapter.clearData();
        for (int i = 0; i < dataList.size(); i++) {
            mAdapter.addData(dataList.get(i), i);
        }
    }
}
