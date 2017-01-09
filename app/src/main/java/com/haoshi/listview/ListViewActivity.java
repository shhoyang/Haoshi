package com.haoshi.listview;

import android.content.Intent;
import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

public class ListViewActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_list_view;
    }

    @Override
    public String setTitle() {
        return TAG = ListViewActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button:
                intent = new Intent(this, ExpandableListViewActivity.class);
                break;
            case R.id.button1:
                intent = new Intent(this, RecyclerViewActivity.class);
                intent.putExtra("type", 1);
                break;
            case R.id.button2:
                intent = new Intent(this, RecyclerViewActivity.class);
                intent.putExtra("type", 2);
                break;
            case R.id.button3:
                intent = new Intent(this, RecyclerViewActivity.class);
                intent.putExtra("type", 3);
                break;
            case R.id.button4:
                intent = new Intent(this, RecyclerViewActivity.class);
                intent.putExtra("type", 4);
                break;
            case R.id.button5:
                intent = new Intent(this, RecyclerViewActivity.class);
                intent.putExtra("type", 5);
                break;
        }
        startActivity(intent);
    }
}
