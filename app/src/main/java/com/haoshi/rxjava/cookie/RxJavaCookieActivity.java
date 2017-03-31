package com.haoshi.rxjava.cookie;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseListActivity;
import com.haoshi.hao.Constant;
import com.haoshi.rxjava.cookie.bean.News;
import com.haoshi.rxjava.cookie.utils.NetWorks;
import com.haoshi.utils.LogUtils;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import rx.Observer;

/**
 * @author HaoShi
 */
public class RxJavaCookieActivity extends BaseListActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    @Override
    public View getListView() {
        return recyclerView;
    }

    @Override
    public void setData() {
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpaceDecoration(16));
        NetWorks.getNews(Constant.CHANNELS_KEY[0], Constant.API_KEY, new Observer<News>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, e.toString());
            }

            @Override
            public void onNext(News news) {
                LogUtils.d(TAG, news.toString());
                if (news.getResult().getStat().equals("1"))
                    adapter.setList(news.getResult().getData());
            }
        });
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_rx_java_cookie;
    }

    @Override
    public String setTitle() {
        return TAG = RxJavaCookieActivity.class.getSimpleName();
    }
}
