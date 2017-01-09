package com.haoshi.listview;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.IndexActivity;
import com.haoshi.utils.T;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<String> texts = new ArrayList<>();
    private List<Integer> images = new ArrayList<>();

    private int type;

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    @Override
    public void setData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 1);

        for (int i = 0; i < 60; i++) {
            texts.add("条目" + i);
        }

        for (int i = 0; i < 10; i++) {
            images.add(R.mipmap.a);
            images.add(R.mipmap.b);
            images.add(R.mipmap.c);
            images.add(R.mipmap.d);
            images.add(R.mipmap.e);
            images.add(R.mipmap.f);
        }

        RecyclerAdapter adapter = new RecyclerAdapter(texts, images, new RecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                T.showShort(RecyclerViewActivity.this, "点击了条目" + position);
            }
        });
        recyclerView.setAdapter(adapter);
        if (type == 1) {
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            manager.setReverseLayout(false);
            recyclerView.setLayoutManager(manager);
        } else if (type == 2) {
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            manager.setReverseLayout(false);
            recyclerView.setLayoutManager(manager);
        } else if (type == 3) {
            GridLayoutManager manager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
        } else if (type == 4) {
            GridLayoutManager manager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(manager);
        } else if (type == 5) {
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
        }
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_recycler_view;
    }

    @Override
    public String setTitle() {
        return TAG = IndexActivity.class.getSimpleName();
    }
}
