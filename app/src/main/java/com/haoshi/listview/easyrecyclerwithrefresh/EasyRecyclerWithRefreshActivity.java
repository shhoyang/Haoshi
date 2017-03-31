package com.haoshi.listview.easyrecyclerwithrefresh;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.ToastUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.DividerDecoration;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.adapter.ScaleInAnimatorAdapter;
import it.gmariotti.recyclerview.adapter.SlideInBottomAnimatorAdapter;
import it.gmariotti.recyclerview.adapter.SlideInLeftAnimatorAdapter;
import it.gmariotti.recyclerview.adapter.SlideInRightAnimatorAdapter;

public class EasyRecyclerWithRefreshActivity extends BaseActivity {

    private EasyRecyclerView recyclerView;
    private Handler handler = new Handler();
    private EasyAdapter adapter;
    private int maxIndex = 0;
    private int minIndex = -1;
    private boolean isAddTop = true;

    @Override
    public void initView() {
        recyclerView = (EasyRecyclerView) findViewById(R.id.recycler);
        recyclerView.setRefreshingColorResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, 2);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        adapter = new EasyAdapter(this);
        adapter.setNotifyOnChange(true);
        ScaleInAnimatorAdapter animatorAdapter = new ScaleInAnimatorAdapter(adapter, recyclerView.getRecyclerView());
        recyclerView.setAdapter(animatorAdapter);
    }

    @Override
    public void setListener() {
        adapter.setMore(R.layout.view_more, () -> {
            handler.postDelayed(() -> {
                adapter.pauseMore();
                isAddTop = true;
                setData();
            }, 2000);
        });

        recyclerView.setRefreshListener(() ->
                handler.postDelayed(() -> {
                    isAddTop = false;
                    setData();
                }, 2000));

        adapter.setOnItemClickListener(position -> {
            ToastUtils.showShort(this, "点击了item" + (position + 1 + minIndex));
        });
    }

    @Override
    public void setData() {
        if (isAddTop) {
            for (int i = 0; i < 20; i++) {
                adapter.add("item" + (maxIndex++));
            }
        } else {
            for (int i = 0; i < 20; i++) {
                adapter.insert("item" + (minIndex--), 0);
            }
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_easy_recycler_with_refresh;
    }

    @Override
    public String setTitle() {
        return TAG = EasyRecyclerWithRefreshActivity.class.getSimpleName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_anim, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alpha:
                AlphaAnimatorAdapter alphaAnimatorAdapter = new AlphaAnimatorAdapter(adapter, recyclerView.getRecyclerView());
                recyclerView.setAdapter(alphaAnimatorAdapter);
                break;
            case R.id.action_slide_left:
                SlideInLeftAnimatorAdapter slideInLeftAnimatorAdapter = new SlideInLeftAnimatorAdapter(adapter, recyclerView.getRecyclerView());
                recyclerView.setAdapter(slideInLeftAnimatorAdapter);
                break;
            case R.id.action_slide_right:
                SlideInRightAnimatorAdapter slideInRightAnimatorAdapter = new SlideInRightAnimatorAdapter(adapter, recyclerView.getRecyclerView());
                recyclerView.setAdapter(slideInRightAnimatorAdapter);
                break;
            case R.id.action_slide_bottom:
                SlideInBottomAnimatorAdapter slideInBottomAnimatorAdapter = new SlideInBottomAnimatorAdapter(adapter, recyclerView.getRecyclerView());
                recyclerView.setAdapter(slideInBottomAnimatorAdapter);
                break;
            case R.id.action_scale:
                ScaleInAnimatorAdapter scaleInAnimatorAdapter = new ScaleInAnimatorAdapter(adapter, recyclerView.getRecyclerView());
                recyclerView.setAdapter(scaleInAnimatorAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
