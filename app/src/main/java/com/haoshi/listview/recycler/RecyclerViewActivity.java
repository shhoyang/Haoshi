package com.haoshi.listview.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseListActivity;
import com.haoshi.utils.ToastUtils;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

/**
 * @author HaoShi
 */
public class RecyclerViewActivity extends BaseListActivity {

    private RecyclerView recyclerView;

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new SpaceDecoration(16));
    }

    @Override
    public View getListView() {
        return recyclerView;
    }

    @Override
    public void setData() {

        RecyclerAdapter adapter = new RecyclerAdapter(position -> ToastUtils.showShort(RecyclerViewActivity.this, "点击了条目" + position));
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler, menu);
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
