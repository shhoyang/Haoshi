package com.haoshi.listview.expandable;

import android.widget.ExpandableListView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author HaoShi
 */
public class ExpandableListViewActivity extends BaseActivity {

    private ExpandableListView listView;

    @Override
    public void initView() {
        listView = (ExpandableListView) findViewById(R.id.list);
    }

    @Override
    public void setData() {
        List<String> groupData = new ArrayList<>();
        List<List<String>> childData = new ArrayList<>();
        Collections.addAll(groupData, "item0", "item1", "item2", "item3", "item4");
        Collections.addAll(childData, groupData, groupData, groupData, groupData, groupData);
        ExpandableAdapter adapter = new ExpandableAdapter(this, groupData, childData);
        listView.setAdapter(adapter);

        // 默认展开
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }

        listView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            //返回true,Group点击不闭合
            return true;
        });
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_expandable_list_view;
    }

    @Override
    public String setTitle() {
        return TAG = ExpandableListViewActivity.class.getSimpleName();
    }
}
