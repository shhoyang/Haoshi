package com.haoshi.listview.indexablerecyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;

public class IndexableRecyclerActivity extends BaseActivity {

    private SearchView searchView;
    private IndexableLayout indexableLayout;
    private SearchFragment searchFragment;
    private CityAdapter cityAdapter;

    @Override
    public void initView() {
        dialog.setMessage("正在加载...");
        dialog.show();
        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_search);
        searchView = (SearchView) findViewById(R.id.searchview);
        indexableLayout = (IndexableLayout) findViewById(R.id.indexable);

        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        indexableLayout.setOverlayStyle_Center();

        initFragment();
    }

    @Override
    public void setData() {
        cityAdapter = new CityAdapter(this);
        indexableLayout.setAdapter(cityAdapter);
        List<City> cities = getCities();
        cityAdapter.setDatas(cities, datas -> {
            dialog.dismiss();
            searchFragment.bindDatas(cities);
        });
        indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter<>(cityAdapter, "热", "热门城市", getHotCities()));
        final List<City> gpsCity = getGPSCities();
        final SimpleHeaderAdapter gpsCitysAdapter = new SimpleHeaderAdapter<>(cityAdapter, "定", "当前城市", gpsCity);
        indexableLayout.addHeaderAdapter(gpsCitysAdapter);

        //模拟定位
        indexableLayout.postDelayed(() -> {
            gpsCity.get(0).setName("洛阳市");
            gpsCitysAdapter.notifyDataSetChanged();
        }, 2000);
    }

    @Override
    public void setListener() {
        cityAdapter.setOnItemContentClickListener((v, originalPosition, currentPosition, entity) -> {
            if (originalPosition >= 0) {
                ToastUtils.showShort(IndexableRecyclerActivity.this, "选中:" + entity.getName() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
            } else {
                ToastUtils.showShort(IndexableRecyclerActivity.this, "选中Header:" + entity.getName() + "  当前位置:" + currentPosition);
            }
        });

        cityAdapter.setOnItemTitleClickListener((v, currentPosition, indexTitle) -> ToastUtils.showShort(IndexableRecyclerActivity.this, "选中:" + indexTitle + "  当前位置:" + currentPosition));
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_indexable_recycler;
    }

    @Override
    public String setTitle() {
        return TAG = IndexableRecyclerActivity.class.getSimpleName();
    }

    @Override
    public void onBackPressed() {
        if (!searchFragment.isHidden()) {
            searchView.setQuery(null, false);
            getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
            return;
        }
        super.onBackPressed();
    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    if (searchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().show(searchFragment).commit();
                    }
                } else {
                    if (!searchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().hide(searchFragment).commit();
                    }
                }

                searchFragment.bindQueryText(newText);
                return false;
            }
        });
    }

    private List<City> getCities() {
        List<City> list = new ArrayList<>();
        List<String> strings = Arrays.asList(getResources().getStringArray(R.array.city_array));
        City city;
        for (String item : strings) {
            city = new City();
            city.setName(item);
            list.add(city);
        }
        return list;
    }

    private List<City> getHotCities() {
        List<City> list = new ArrayList<>();
        list.add(new City("北京市"));
        list.add(new City("上海市"));
        list.add(new City("广州市"));
        list.add(new City("杭州市"));
        return list;
    }

    private List<City> getGPSCities() {
        List<City> list = new ArrayList<>();
        list.add(new City("定位中..."));
        return list;
    }
}
