package com.haoshi.listview.recycler;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoshi.R;

import java.util.List;

/**
 * @author HaoShi
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VH> {

    private OnRecyclerItemClickListener onItemClick;

    public RecyclerAdapter(OnRecyclerItemClickListener onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_1, parent, false);
        VH vh = new VH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.textView.setText("item" + position);
        holder.cardView.setOnClickListener(v -> onItemClick.onItemClick(holder.getLayoutPosition()));
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public interface OnRecyclerItemClickListener {
        void onItemClick(int position);
    }

    public static class VH extends RecyclerView.ViewHolder {

        public CardView cardView = null;
        public TextView textView = null;

        public VH(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
