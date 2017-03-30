package com.haoshi.listview.indexablerecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haoshi.R;

import java.util.List;


/**
 * @author Haoshi
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView textNoData;
    private SearchAdapter adapter;
    private List<City> cities;

    private String queryText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_city, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        textNoData = (TextView) view.findViewById(R.id.text_no_data);
        return view;
    }

    public void bindDatas(List<City> cities) {
        this.cities = cities;
        adapter = new SearchAdapter(cities, isEmpty -> {
            if(isEmpty){
                textNoData.setVisibility(View.VISIBLE);
            }else {
                textNoData.setVisibility(View.GONE);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        if (queryText != null) {
            adapter.getFilter().filter(queryText);
        }
    }

    /**
     * 根据newText 进行查找, 显示
     */
    public void bindQueryText(String newText) {
        if (cities == null) {
            queryText = newText.toLowerCase();
        } else if (!TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter(newText.toLowerCase());
        }
    }
}
