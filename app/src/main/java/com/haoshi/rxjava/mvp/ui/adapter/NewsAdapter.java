package com.haoshi.rxjava.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haoshi.R;
import com.haoshi.rxjava.mvp.bean.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoshi
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private List<Data> list = new ArrayList<>();


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Data data = list.get(position);
        holder.textView.setText(data.getTitle());
        Glide.with(context)
                .load(data.getThumbnail_pic_s())
                .crossFade()
                .into(holder.imageView);
        if (onItemClickListener != null) {
            holder.cardView.setOnClickListener(v -> {
                int pos = holder.getAdapterPosition();
                onItemClickListener.onItemClick(pos);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void addData(Data data, int position) {
        if (data == null) {
            return;
        }
        list.add(position, data);
        notifyItemInserted(position);
    }

    public void setData(List<Data> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<Data> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
