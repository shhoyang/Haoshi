package com.haoshi.scrollview;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.hao.BaseListActivity;
import com.haoshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoShi
 */
public class ScrollActivity extends BaseListActivity {

    private ListView listView;

    @Override
    public void initView() {
        final TextView textPlace = (TextView) findViewById(R.id.text_place);
        final TextView textTitle = (TextView) findViewById(R.id.text_title);
        StickyScrollView scrollView = (StickyScrollView) findViewById(R.id.activity_scroll);
        scrollView.setOnScrollListener(scrollY -> {
            int top = Math.max(scrollY, textPlace.getTop());
            textTitle.layout(0, top, textTitle.getWidth(), top + textTitle.getHeight());
        });

        listView = (ListView) findViewById(R.id.list);
        listView.setFocusable(false);
        listView.setOnItemClickListener((adapterView, view, i, l) -> ToastUtils.showShort(ScrollActivity.this, "点击了条目" + i));
    }

    @Override
    public View getListView() {
        return listView;
    }

    @Override
    public void setData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item" + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_scroll;
    }

    @Override
    public String setTitle() {
        return TAG = ScrollActivity.class.getSimpleName();
    }
}
