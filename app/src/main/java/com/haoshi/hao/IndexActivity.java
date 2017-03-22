package com.haoshi.hao;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.haoshi.R;
import com.haoshi.androidtest.AndroidTestActivity;
import com.haoshi.bluetooth.BluetoothActivity;
import com.haoshi.dialog.DialogActivity;
import com.haoshi.hotfix.HotFixActivity;
import com.haoshi.listview.ListViewActivity;
import com.haoshi.lottie.LottieActivity;
import com.haoshi.mvp.activity.MvpActivity;
import com.haoshi.rxjava.RxJavaActivity;
import com.haoshi.scrollview.ScrollActivity;
import com.haoshi.service.ServiceActivity;
import com.haoshi.sqlite.SqliteActivity;
import com.haoshi.swipe.SwipeActivity;
import com.haoshi.toast.ToastActivity;
import com.haoshi.tts.TTSActivity;
import com.haoshi.utils.ScreenUtils;
import com.haoshi.view.MarqueeTextView;
import com.haoshi.view.ViewActivity;
import com.haoshi.view.xrefreshview.SmileyHeaderView;
import com.haoshi.webview.JavaJsActivity;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * @author HaoShi
 */
public class IndexActivity extends BaseActivity implements XRefreshView.XRefreshViewListener {

    private MarqueeTextView marqueeTextView;
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private List<Class<?>> list = new ArrayList<>();
    private Handler handler = new Handler();
    private long lastTime = 0;

    @Override
    public void initView() {

        marqueeTextView = (MarqueeTextView) findViewById(R.id.marquee);
        marqueeTextView.setText("天行健,君子以自强不息;地势坤,君子以厚德载物");
        marqueeTextView.setSpeed(ScreenUtils.getScreenWidth(this) / 300);
        marqueeTextView.setFontColor("#FFFFFF");
        marqueeTextView.init(getWindowManager());

        xRefreshView = (XRefreshView) findViewById(R.id.refreshview);
        xRefreshView.setPullRefreshEnable(true);
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setPinnedTime(500);
        //xRefreshView.setHideFooterWhenComplete(true);
        xRefreshView.setAutoRefresh(false);
        //xRefreshView.setPreLoadCount(4);
        xRefreshView.setCustomHeaderView(new SmileyHeaderView(this));
        xRefreshView.setCustomFooterView(new XRefreshViewFooter(this));
        xRefreshView.setXRefreshViewListener(this);
        lastTime = System.currentTimeMillis();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.getItemAnimator().setAddDuration(800);
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
        list.add(RxJavaActivity.class);
        list.add(MvpActivity.class);
        list.add(DialogActivity.class);
        list.add(ScrollActivity.class);
        list.add(SwipeActivity.class);
        list.add(ListViewActivity.class);
        list.add(JavaJsActivity.class);
        list.add(ViewActivity.class);
        list.add(SqliteActivity.class);
        list.add(ServiceActivity.class);
        list.add(TTSActivity.class);
        list.add(BluetoothActivity.class);
        list.add(AndroidTestActivity.class);
        list.add(HotFixActivity.class);
        list.add(ToastActivity.class);
        list.add(LottieActivity.class);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setReverseLayout(false);
        recyclerView.setLayoutManager(manager);
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
        switch (v.getId()){
            case R.id.button_up:
                recyclerView.scrollToPosition(0);
                break;
        }
    }

    /**
     * XRefreshView
     */
    @Override
    public void onRefresh() {
        xRefreshView.restoreLastRefreshTime(lastTime);
        handler.postDelayed(() -> {
            xRefreshView.stopRefresh();
            lastTime = xRefreshView.getLastRefreshTime();
        }, 2000);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        xRefreshView.restoreLastRefreshTime(lastTime);

        handler.postDelayed(() -> {
            xRefreshView.stopLoadMore();
            lastTime = xRefreshView.getLastRefreshTime();
        }, 2000);
    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    /**
     * RecyclerView
     */
    private String name;
    private BaseRecyclerAdapter adapter = new BaseRecyclerAdapter<VH>() {

        @Override
        public int getAdapterItemCount() {
            return list.size();
        }

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
        public void onBindViewHolder(VH holder, final int position, boolean isItem) {
            name = list.get(position).getSimpleName();
            holder.button.setText(name.substring(0, name.lastIndexOf("Activity")));
            holder.button.setOnClickListener(view -> startActivity(new Intent(IndexActivity.this, list.get(position))));
        }
    };

    public class VH extends RecyclerView.ViewHolder {

        public Button button = null;

        public VH(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                button = (Button) itemView;
            }
        }
    }
}
