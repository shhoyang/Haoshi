package com.haoshi.hao;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.andview.refreshview.XRefreshViewHeader;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.haoshi.R;
import com.haoshi.androidtest.AndroidTestActivity;
import com.haoshi.baidumap.BaiduMapActivity;
import com.haoshi.bluetooth.BluetoothActivity;
import com.haoshi.customview.MarqueeTextView;
import com.haoshi.customview.ViewActivity;
import com.haoshi.customview.xrefreshview.SmileyHeaderView;
import com.haoshi.dialog.DialogActivity;
import com.haoshi.javajs.JavaJsActivity;
import com.haoshi.listview.easyrecycler.EasyRecyclerActivity;
import com.haoshi.listview.easyrecyclerwithrefresh.EasyRecyclerWithRefreshActivity;
import com.haoshi.listview.expandable.ExpandableListViewActivity;
import com.haoshi.listview.indexablerecyclerview.IndexableRecyclerActivity;
import com.haoshi.listview.recycler.RecyclerViewActivity;
import com.haoshi.lottie.LottieActivity;
import com.haoshi.mvp.activity.MvpActivity;
import com.haoshi.rxjava.RxJavaActivity;
import com.haoshi.rxjava.cookie.RxJavaCookieActivity;
import com.haoshi.rxjava.mvp.ui.activity.RxJavaMvpActivity;
import com.haoshi.scrollview.ScrollActivity;
import com.haoshi.service.ServiceActivity;
import com.haoshi.sharesdk.ShareSdkActivity;
import com.haoshi.sms.SmsActivity;
import com.haoshi.sqlite.greendao.GreenDaoActivity;
import com.haoshi.sqlite.ormlite.OrmliteActivity;
import com.haoshi.swipe.SwipeActivity;
import com.haoshi.tinker.TinkerActivity;
import com.haoshi.toast.StyleableToastActivity;
import com.haoshi.toast.ToastyActivity;
import com.haoshi.tts.TTSActivity;
import com.haoshi.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoShi
 */
public class IndexActivity extends BaseActivity implements XRefreshView.XRefreshViewListener {

    private MarqueeTextView marqueeTextView;
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private List<ActionBean> list = new ArrayList<>();
    private Handler handler = new Handler();

    @Override
    public void initView() {

        marqueeTextView = (MarqueeTextView) findViewById(R.id.marquee);
        marqueeTextView.setText("天行健,君子以自强不息;地势坤,君子以厚德载物");
        marqueeTextView.setSpeed(ScreenUtils.getScreenWidth(this) / 300);
        marqueeTextView.setFontColor("#FFFFFF");
        marqueeTextView.init(getWindowManager());

        xRefreshView = (XRefreshView) findViewById(R.id.refreshview);
        xRefreshView.setPullRefreshEnable(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setPinnedTime(500);
        xRefreshView.setCustomHeaderView(new SmileyHeaderView(this));
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        xRefreshView.setXRefreshViewListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.button_up).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        marqueeTextView.startScroll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        marqueeTextView.stopScroll();
    }

    @Override
    public void setData() {

        list.add(new ActionBean("AndroidTest", AndroidTestActivity.class));
        list.add(new ActionBean("BaiduMap", BaiduMapActivity.class));
        list.add(new ActionBean("Bluetooth", BluetoothActivity.class));
        list.add(new ActionBean("CustomView", ViewActivity.class));
        list.add(new ActionBean("Dialog", DialogActivity.class));
        list.add(new ActionBean("EasyRecyclerView", EasyRecyclerActivity.class));
        list.add(new ActionBean("EasyRecyclerViewWithRefresh", EasyRecyclerWithRefreshActivity.class));
        list.add(new ActionBean("ExpandableListView", ExpandableListViewActivity.class));
        list.add(new ActionBean("IndexableRecyclerView", IndexableRecyclerActivity.class));
        list.add(new ActionBean("GreenDao", GreenDaoActivity.class));
        list.add(new ActionBean("JavaJs", JavaJsActivity.class));
        list.add(new ActionBean("Lottie", LottieActivity.class));
        list.add(new ActionBean("MVP", MvpActivity.class));
        list.add(new ActionBean("Ormlite", OrmliteActivity.class));
        list.add(new ActionBean("RecyclerView", RecyclerViewActivity.class));
        list.add(new ActionBean("RxJava", RxJavaActivity.class));
        list.add(new ActionBean("RxJavaCookie", RxJavaCookieActivity.class));
        list.add(new ActionBean("RxJavaMvp", RxJavaMvpActivity.class));
        list.add(new ActionBean("Service", ServiceActivity.class));
        list.add(new ActionBean("ShareSdk", ShareSdkActivity.class));
        list.add(new ActionBean("SMS", SmsActivity.class));
        list.add(new ActionBean("StickScroll", ScrollActivity.class));
        list.add(new ActionBean("StyleableToast", StyleableToastActivity.class));
        list.add(new ActionBean("SwipeLayout", SwipeActivity.class));
        list.add(new ActionBean("Tinker", TinkerActivity.class));
        list.add(new ActionBean("Toasty", ToastyActivity.class));
        list.add(new ActionBean("TTS", TTSActivity.class));

        recyclerView.setAdapter(adapter);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_index;
    }

    @Override
    public String setTitle() {
        TAG = IndexActivity.class.getSimpleName();
        return "豪〤世";
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_up:
                recyclerView.scrollToPosition(0);
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    /**
     * XRefreshView
     */
    @Override
    public void onRefresh(boolean isPullDown) {
        handler.postDelayed(() -> xRefreshView.stopRefresh(), 2000);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        handler.postDelayed(() -> {
            //无更多数据
            //xRefreshView.setLoadComplete(true);
            //成功/失败
            xRefreshView.stopLoadMore();
        }, 2000);
    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    private BaseRecyclerAdapter adapter = new BaseRecyclerAdapter<VH>() {
        @Override
        public VH getViewHolder(View view) {
            return new VH(view, false);
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
            View view = LayoutInflater.from(IndexActivity.this).inflate(R.layout.index_item, parent, false);
            return new VH(view, true);
        }

        @Override
        public void onBindViewHolder(VH holder, int position, boolean isItem) {
            holder.textView.setText(list.get(position).getAction());
            holder.textView.setOnClickListener(view ->
                    startActivity(new Intent(IndexActivity.this, list.get(position).getCls())));
        }

        @Override
        public int getAdapterItemCount() {
            return list.size();
        }
    };

    public static class VH extends RecyclerView.ViewHolder {

        public TextView textView = null;

        public VH(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                textView = (TextView) itemView;
            }
        }
    }
}
