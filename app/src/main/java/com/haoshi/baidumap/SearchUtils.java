package com.haoshi.baidumap;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.haoshi.baidumap.overlayutil.PoiOverlay;
import com.haoshi.utils.KeyBoardUtils;
import com.haoshi.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoshi
 */

public class SearchUtils {

    private BaiduMapActivity activity;
    private BaiduMap baiduMap;
    private AutoCompleteTextView textSearch;
    private PoiSearch poiSearch;
    private SuggestionSearch suggestionSearch;
    private ArrayAdapter<String> suggestionAdapter;
    private List<String> suggestionList = new ArrayList<>();
    private AlertDialog.Builder dialog;

    public SearchUtils(BaiduMapActivity activity, BaiduMap baiduMap, AutoCompleteTextView autoCompleteTextView) {
        this.activity = activity;
        this.baiduMap = baiduMap;
        textSearch = autoCompleteTextView;
        textSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        textSearch.setOnEditorActionListener(onEditorActionListener);
        textSearch.addTextChangedListener(textWatcher);
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
        suggestionSearch = SuggestionSearch.newInstance();
        suggestionSearch.setOnGetSuggestionResultListener(onGetSuggestionResultListener);
        suggestionAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line);
        textSearch.setAdapter(suggestionAdapter);
    }

    public void destroy() {
        poiSearch.destroy();
        suggestionSearch.destroy();
    }

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_SEARCH) {//修改回车键功能
                String keyword = textSearch.getText().toString();
                if (TextUtils.isEmpty(keyword)) {
                    return true;
                }
                poiSearch.searchInCity((new PoiCitySearchOption()).city(activity.getCity()).keyword(keyword).pageNum(0));
                KeyBoardUtils.closeKeybord(textSearch, activity);
                ToastUtils.showShort(activity, "开始搜索");
                textSearch.setText("");
                textSearch.setVisibility(View.GONE);
                return false;
            }
            return true;
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() > 0) {
                suggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(charSequence.toString()).city(activity.getCity()));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                //ToastUtils.showShort(activity, "未找到结果");
                return;
            }
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                baiduMap.clear();
                HaoPoiOverlay poiOverlay = new HaoPoiOverlay(baiduMap);
                baiduMap.setOnMarkerClickListener(poiOverlay);
                poiOverlay.setData(poiResult);
                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();
                return;
            }
            if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
                String strInfo = "在";
                for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                    strInfo += cityInfo.city;
                    strInfo += ",";
                }
                strInfo += "找到结果";
                //ToastUtils.showShort(activity, strInfo);
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                ToastUtils.showShort(activity, "抱歉，未找到结果");
            } else {
                dialog = new AlertDialog.Builder(activity).setTitle("到这里去").setMessage(poiDetailResult.getName() + ": " + poiDetailResult.getAddress())
                        .setPositiveButton("确定", (dialogInterface, i) -> {
                            activity.startNavi(poiDetailResult);
                            dialogInterface.dismiss();

                        }).setNegativeButton("取消", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        });
                dialog.create().show();
            }
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    private OnGetSuggestionResultListener onGetSuggestionResultListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
                return;
            }
            suggestionList.clear();
            for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
                if (info.key != null) {
                    suggestionList.add(info.key);
                }
            }
            suggestionAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_dropdown_item_1line, suggestionList);
            textSearch.setAdapter(suggestionAdapter);
            suggestionAdapter.notifyDataSetChanged();
        }
    };


    public class HaoPoiOverlay extends PoiOverlay {

        public HaoPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            PoiInfo poi = getPoiResult().getAllPoi().get(i);
            poiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
            return true;
        }
    }
}
