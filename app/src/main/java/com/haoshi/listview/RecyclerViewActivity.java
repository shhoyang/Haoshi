package com.haoshi.listview;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoShi
 */
public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<String> texts = new ArrayList<>();
    private List<Integer> images = new ArrayList<>();

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
    }

    @Override
    public void setData() {
        for (int i = 0; i < 50; i++) {
            texts.add("条目" + i);
            images.add(R.mipmap.ic_launcher);
        }

        RecyclerAdapter adapter = new RecyclerAdapter(texts, images, position -> ToastUtils.showShort(RecyclerViewActivity.this, "点击了条目" + position));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list_vertical:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                linearLayoutManager.setReverseLayout(false);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;
            case R.id.action_list_horizontal:
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
                linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
                linearLayoutManager1.setReverseLayout(false);
                recyclerView.setLayoutManager(linearLayoutManager1);
                break;
            case R.id.action_grid_vertical:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(gridLayoutManager);
                break;
            case R.id.action_grid_horizontal:
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(gridLayoutManager1);
                break;
            case R.id.action_flow:
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_recycler_view;
    }

    @Override
    public String setTitle() {
        return TAG = RecyclerViewActivity.class.getSimpleName();
    }
}
