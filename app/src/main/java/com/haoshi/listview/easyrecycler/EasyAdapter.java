package com.haoshi.listview.easyrecycler;

import android.widget.ImageView;
import android.widget.TextView;

import com.camnter.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.haoshi.R;

/**
 * @author Haoshi
 */

public class EasyAdapter extends EasyRecyclerViewAdapter {
    @Override
    public int[] getItemLayouts() {
        return new int[]{R.layout.easy_recycler_item_single, R.layout.easy_recycler_item_double};
    }

    @Override
    public void onBindRecycleViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        int type = getRecycleViewItemType(position);
        if (type == 0) {
            String s = (String) getList().get(position);
            ImageView imageView = viewHolder.findViewById(R.id.image);
            TextView textView = viewHolder.findViewById(R.id.text);
            imageView.setImageResource(R.mipmap.ic_launcher);
            textView.setText(s);
        } else if (type == 1) {
            String s = (String) getList().get(position);
            ImageView imageView = viewHolder.findViewById(R.id.image);
            TextView textView = viewHolder.findViewById(R.id.text);
            imageView.setImageResource(R.mipmap.ic_launcher);
            textView.setText(s);
        }

    }

    @Override
    public int getRecycleViewItemType(int position) {
        if (position % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
