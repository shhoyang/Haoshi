package com.haoshi.listview.easyrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.camnter.easyrecyclerview.widget.EasyRecyclerView;
import com.camnter.easyrecyclerview.widget.decorator.EasyBorderDividerItemDecoration;
import com.camnter.easyrecyclerview.widget.decorator.EasyDividerItemDecoration;
import com.haoshi.R;
import com.haoshi.hao.BaseListActivity;

import java.util.ArrayList;
import java.util.List;

public class EasyRecyclerActivity extends BaseListActivity {

    private EasyRecyclerView recyclerView;
    private List<RecyclerView.ItemDecoration> dividers = new ArrayList<>();
    private int selectDivider = -1;
    private int currentDivider = -1;

    @Override
    public void initView() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(new EasyAdapter());
    }

    @Override
    public View getListView() {
        return recyclerView;
    }

    @Override
    public void setData() {
        dividers.add(new EasyBorderDividerItemDecoration(
                getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin),
                getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin)));
        dividers.add(new EasyDividerItemDecoration(this, EasyDividerItemDecoration.VERTICAL_LIST));
        dividers.add(new EasyDividerItemDecoration(this, EasyDividerItemDecoration.VERTICAL_LIST,
                R.drawable.easy_recycler_view_divider));
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_easy_recycler;
    }

    @Override
    public String setTitle() {
        return TAG = EasyRecyclerActivity.class.getSimpleName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.easy_recycler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                selectDivider = -1;
                break;
            case R.id.action_margin:
                selectDivider = 0;
                break;
            case R.id.action_default_divider:
                selectDivider = 1;
                break;
            case R.id.action_custom_divider:
                selectDivider = 2;
                break;
        }

        if (currentDivider != -1) {
            recyclerView.removeItemDecoration(dividers.get(currentDivider));
        }
        if (selectDivider != -1) {
            recyclerView.addItemDecoration(dividers.get(selectDivider));
        }
        currentDivider = selectDivider;
        return super.onOptionsItemSelected(item);
    }
}
