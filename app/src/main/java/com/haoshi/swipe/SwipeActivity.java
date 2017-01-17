package com.haoshi.swipe;

import android.widget.ListView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

/**
 * @author: HaoShi
 */

public class SwipeActivity extends BaseActivity {

    private ListView listView;

    @Override
    public void initView() {
        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    public void setData() {
        SwipeAdapter adapter = new SwipeAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_swipe;
    }

    @Override
    public String setTitle() {
        return TAG = SwipeActivity.class.getSimpleName();
    }
}
