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

import java.util.List;

/**
 * @author Haoshi
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Data> mDatas;
    private List<Integer> heights;
    private int layoutId;


    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public NewsAdapter(Context context, List<Data> datas, int layoutId) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(inflater.inflate(layoutId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (heights != null && layoutId == R.layout.adapter_news_grid) {
            int height = heights.get(position);
            ViewGroup.LayoutParams params = holder.iv_news.getLayoutParams();
            params.height = height;
            holder.iv_news.setLayoutParams(params);
        }
        Data data = mDatas.get(position);
        holder.tv_title.setText(data.getTitle());
        holder.tv_time.setText(data.getDate());
        Glide.with(context)
                .load(data.getThumbnail_pic_s())
                .crossFade()
                .into(holder.iv_news);
        if (onItemClickListener != null) {
            holder.itemView.setBackgroundResource(R.drawable.item_bg);
            holder.cardView.setOnClickListener(v -> {
                int pos = holder.getAdapterPosition();
                onItemClickListener.onItemClick(holder.itemView, pos);
            });
        }

        if (onItemLongClickListener != null) {
            holder.itemView.setBackgroundResource(R.drawable.item_bg);
            holder.cardView.setOnLongClickListener(v -> {
                int pos = holder.getLayoutPosition();
                onItemLongClickListener.onItemLongClick(holder.itemView, pos);
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(Data data, int position) {
        mDatas.add(position, data);
        notifyItemInserted(position);
    }

    public void setHeights(List<Integer> heights) {
        this.heights = heights;
    }

    public void clearData() {
        notifyItemRangeRemoved(0, mDatas.size());
        if (mDatas.size() > 0) {
            mDatas.clear();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_news;
        TextView tv_title, tv_time;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            iv_news = (ImageView) itemView.findViewById(R.id.iv_news);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }

    }


}
