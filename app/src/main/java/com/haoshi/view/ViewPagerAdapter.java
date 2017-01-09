package com.haoshi.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yugu on 2017/1/9.
 */

public class ViewPagerAdapter extends PagerAdapter {

    public List<View> dataList = new ArrayList<>();

    public ViewPagerAdapter(List<View> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = dataList.get(position % dataList.size());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(dataList.get(position % dataList.size()));
    }
}
