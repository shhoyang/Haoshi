package com.haoshi.listview.indexablerecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoshi.R;

import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * @author Haoshi
 */
public class CityAdapter extends IndexableAdapter<City> {
    private LayoutInflater mInflater;

    public CityAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.city_index_item, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.city_item, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.textView.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, City city) {
        ContentVH vh = (ContentVH) holder;
        vh.textView.setText(city.getName());
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView textView;

        public IndexVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView textView;

        public ContentVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
