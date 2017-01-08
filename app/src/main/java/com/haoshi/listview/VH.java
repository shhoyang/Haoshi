package com.haoshi.listview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoshi.R;

public class VH extends RecyclerView.ViewHolder {

    public ImageView imageView = null;
    public TextView textView = null;

    public VH(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        textView = (TextView) itemView.findViewById(R.id.text);
    }
}
