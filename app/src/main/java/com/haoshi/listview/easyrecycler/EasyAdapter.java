package com.haoshi.listview.easyrecycler;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.camnter.easyrecyclerview.adapter.EasyRecyclerViewAdapter;
import com.camnter.easyrecyclerview.holder.EasyRecyclerViewHolder;
import com.haoshi.R;
import com.haoshi.utils.ImageManeger;

/**
 * @author Haoshi
 */

public class EasyAdapter extends EasyRecyclerViewAdapter {

    private Context context;

    public EasyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int[] getItemLayouts() {
        return new int[]{R.layout.list_item_1, R.layout.list_item_1};
    }

    /**
     * 模拟多布局
     */
    @Override
    public void onBindRecycleViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        int type = getRecycleViewItemType(position);
        if (type == 0) {
            ImageView imageView = viewHolder.findViewById(R.id.image);
            TextView textView = viewHolder.findViewById(R.id.text);
            ImageManeger.loadImage(context, R.mipmap.ic_launcher, imageView);
            textView.setText("item" + position);
        } else if (type == 1) {
            ImageView imageView = viewHolder.findViewById(R.id.image);
            TextView textView = viewHolder.findViewById(R.id.text);
            ImageManeger.loadImage(context, R.mipmap.ic_launcher2, imageView);
            textView.setText("item" + position);
        }
    }

    @Override
    public int getItemCount() {
        return 50;
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
