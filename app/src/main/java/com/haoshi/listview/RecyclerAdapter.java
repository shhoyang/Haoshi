package com.haoshi.listview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.haoshi.R;

import java.util.List;

/**
 * @author: HaoShi
 */
public class RecyclerAdapter extends RecyclerView.Adapter<VH> {

    private List<String> texts;
    private List<Integer> images;
    private OnRecyclerItemClickListener onItemClick;

    public RecyclerAdapter(List<String> texts, List<Integer> images, OnRecyclerItemClickListener onItemClick) {
        this.texts = texts;
        this.images = images;
        this.onItemClick = onItemClick;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.recycler_item, null);
        VH vh = new VH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.textView.setText(texts.get(position));
        holder.imageView.setImageResource(images.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position);
    }
}
