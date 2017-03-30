package com.haoshi.rxjava.cookie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.haoshi.R;
import com.haoshi.rxjava.cookie.bean.News;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoShi
 */
public class RecyclerAdapter extends RecyclerView.Adapter<VH> {

    private Context context;
    private List<News.ResultBean.DataBean> list = new ArrayList<>();


    public void setList(List<News.ResultBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rxjava_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.textView.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getThumbnail_pic_s()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
