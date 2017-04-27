package com.haoshi.shopcart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.shopcart.bean.CategoryBean;

import java.util.List;


/**
 * @author HaoShi
 */

public class CategoryAdapter extends BaseAdapter {
    private List<CategoryBean> list;
    int selection = 0;

    public CategoryAdapter(List<CategoryBean> list) {
        this.list = list;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_cart_left_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textCategory = (TextView) convertView.findViewById(R.id.text_category);
            viewHolder.textCount = (TextView) convertView.findViewById(R.id.text_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textCategory.setText(list.get(position).getKind());
        int count = 0;
        for (int i = 0; i < list.get(position).getList().size(); i++) {
            count += list.get(position).getList().get(i).getNum();
        }
        list.get(position).setCount(count);

        if (count <= 0) {
            viewHolder.textCount.setVisibility(View.GONE);
        } else {

            viewHolder.textCount.setVisibility(View.VISIBLE);
            viewHolder.textCount.setText(list.get(position).getCount() + "");
        }

        if (position == selection) {
            viewHolder.textCategory.setTextColor(Color.BLACK);
        } else {
            viewHolder.textCategory.setTextColor(Color.GRAY);
        }
        return convertView;
    }

    class ViewHolder {
        TextView textCategory;
        TextView textCount;
    }
}
