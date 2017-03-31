package com.haoshi.rxjava.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoshi
 */
public class NewsFragment extends BaseFragment<NewsContract.Presenter, NewsContract.Model>
        implements NewsContract.View, NewsAdapter.OnItemClickListener {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<Data> list = new ArrayList<>();
    private NewsAdapter adapter;
    private String channelName;
    private boolean isGrid;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initPM() {
        model = new NewsModel();
        presenter = new NewsPresenter(this, model);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            channelName = getArguments().getString("type");
        }

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        refreshLayout.setColorSchemeResources(new int[]{R.color.tab_blue, R.color.tab_purple, R.color.tab_pink});
        refreshLayout.setOnRefreshListener(() -> presenter.getChannelList(channelName));
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new SpaceDecoration(32));
        recyclerView.setHasFixedSize(false);
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        if (list.size() <= 0) {
            presenter.getChannelList(channelName);
            startLoading();
        }
    }

    /**
     * 处理RxBus发来的消息
     */
    @Override
    protected void handleEvent(Object o) {
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
            isGrid = event.isGrid();
            recyclerView.setLayoutManager(isGrid ?
                    new GridLayoutManager(activity, 2, LinearLayoutManager.VERTICAL, false)
                    : new LinearLayoutManager(activity));
        }
    }

    @Override
    public void onItemClick(int position) {
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
        list = dataList;
        adapter.setData(list);
    }
}
