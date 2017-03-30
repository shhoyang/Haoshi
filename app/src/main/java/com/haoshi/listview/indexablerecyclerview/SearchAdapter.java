package com.haoshi.listview.indexablerecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;


import com.haoshi.R;
import com.haoshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoshi
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VH> {

    private Context context;
    private List<City> cities = new ArrayList<>();
    private SearchResult searchResult;

    public SearchAdapter(List<City> cities, SearchResult searchResult) {
        this.cities = cities;
        this.searchResult = searchResult;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.city_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.textView.setText(cities.get(position).getName());
        holder.textView.setOnClickListener(v -> {
            int position1 = holder.getAdapterPosition();
            ToastUtils.showShort(context, "选择了" + cities.get(position1).getName());
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<City> list = new ArrayList<>();
                for (City item : cities) {
                    if (item.getPinyin().startsWith(constraint.toString()) || item.getName().contains(constraint)) {
                        list.add(item);
                    }
                }
                FilterResults results = new FilterResults();
                results.count = list.size();
                results.values = list;
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<City> list = (ArrayList<City>) results.values;
                cities.clear();
                cities.addAll(list);
                if (results.count == 0) {
                    searchResult.setResult(true);
                } else {
                    searchResult.setResult(false);
                }
                notifyDataSetChanged();
            }
        };
    }

    public static class VH extends RecyclerView.ViewHolder {
        private TextView textView;

        public VH(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    public interface SearchResult {
        void setResult(boolean isEmpty);
    }
}
