package com.haoshi.photopicker;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.haoshi.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoshi
 */

public class PhotoAdapter extends BaseAdapter {
    private int columnWidth;
    private List<String> list = new ArrayList<>();

    public PhotoAdapter(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.photo_picker_grid_item, null);
            imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(imageView);
            // 重置ImageView宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(columnWidth, columnWidth);
            imageView.setLayoutParams(params);
        } else {
            imageView = (ImageView) convertView.getTag();
        }
        Glide.with(parent.getContext())
                .load(new File(list.get(position)))
                .placeholder(R.mipmap.default_error)
                .error(R.mipmap.default_error)
                .centerCrop()
                .crossFade()
                .into(imageView);
        return convertView;
    }
}
