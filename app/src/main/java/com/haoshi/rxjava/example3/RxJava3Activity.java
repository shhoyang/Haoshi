package com.haoshi.rxjava.example3;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.Constant;
import com.haoshi.rxjava.example3.bean.News;
import com.haoshi.rxjava.example3.utils.NetWorks;
import com.haoshi.utils.L;

import rx.Observer;

/**
 * @author HaoShi
 */
public class RxJava3Activity extends BaseActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    @Override
    public void setData() {
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setReverseLayout(false);
        recyclerView.setLayoutManager(manager);

        NetWorks.getNews(Constant.CHANNELS_KEY[0], Constant.API_KEY, new Observer<News>() {

            @Override
            public void onCompleted() {
            
            }

            @Override
            public void onError(Throwable e) {
                L.e(TAG,e.toString());
            }

            @Override
            public void onNext(News news) {
                L.d(TAG,news.toString());
                if (news.getResult().getStat().equals("1"))
                    adapter.setList(news.getResult().getData());
            }
        });
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_rx_java3;
    }

    @Override
    public String setTitle() {
        return TAG = RxJava3Activity.class.getSimpleName();
    }
}
