package com.haoshi.hao;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.haoshi.R;
import com.haoshi.bluetooth.BluetoothActivity;
import com.haoshi.dialog.DialogActivity;
import com.haoshi.listview.ListViewActivity;
import com.haoshi.mvp.activity.MvpActivity;
import com.haoshi.rxjava.RxJavaActivity;
import com.haoshi.scrollview.ScrollActivity;
import com.haoshi.service.ServiceActivity;
import com.haoshi.sqlite.SqliteActivity;
import com.haoshi.swipe.SwipeActivity;
import com.haoshi.tts.TTSActivity;
import com.haoshi.utils.ScreenUtils;
import com.haoshi.view.MarqueeTextView;
import com.haoshi.view.ViewActivity;
import com.haoshi.webview.JavaJsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: HaoShi
 */
public class IndexActivity extends BaseActivity {

    private MarqueeTextView marqueeTextView;
    private RecyclerView recyclerView;
    private List<Class<?>> list = new ArrayList<>();

    @Override
    public void initView() {

        marqueeTextView = (MarqueeTextView) findViewById(R.id.marquee);
        marqueeTextView.setText("工作中总结的Demo,与同仁共享");
        marqueeTextView.setSpeed(ScreenUtils.getScreenWidth(this) / 300);
        marqueeTextView.setFontColor("#FFFFFF");
        marqueeTextView.init(getWindowManager());
        marqueeTextView.setOnMarqueeCompleteListener(new MarqueeTextView.OnMarqueeCompleteListener() {
            @Override
            public void onMarqueeComplete() {
                //T.showLong(IndexActivity.this, "结束");
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
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

    private RecyclerView.Adapter adapter = new RecyclerView.Adapter<VH>() {
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(IndexActivity.this).inflate(R.layout.index_item, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(VH holder, final int position) {
            holder.button.setText(list.get(position).getSimpleName());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(IndexActivity.this, list.get(position)));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    };

    public class VH extends RecyclerView.ViewHolder {

        public Button button = null;

        public VH(View itemView) {
            super(itemView);
            button = (Button) itemView;
        }
    }
}
