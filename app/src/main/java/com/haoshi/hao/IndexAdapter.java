package com.haoshi.hao;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.XRefreshViewFooter;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.haoshi.R;
import com.haoshi.androidtest.AndroidTestActivity;
import com.haoshi.baidumap.BaiduMapActivity;
import com.haoshi.bluetooth.BluetoothActivity;
import com.haoshi.customview.ViewActivity;
import com.haoshi.dialog.DialogActivity;
import com.haoshi.javajs.JavaJsActivity;
import com.haoshi.listview.easyrecycler.EasyRecyclerActivity;
import com.haoshi.listview.easyrecyclerwithrefresh.EasyRecyclerWithRefreshActivity;
import com.haoshi.listview.expandable.ExpandableListViewActivity;
import com.haoshi.listview.indexablerecyclerview.IndexableRecyclerActivity;
import com.haoshi.listview.recycler.RecyclerViewActivity;
import com.haoshi.lottie.LottieActivity;
import com.haoshi.mvp.activity.MvpActivity;
import com.haoshi.photopicker.PhotoPickerActivity;
import com.haoshi.rxjava.RxJavaActivity;
import com.haoshi.rxjava.cookie.RxJavaCookieActivity;
import com.haoshi.rxjava.mvp.ui.activity.RxJavaMvpActivity;
import com.haoshi.scrollview.ScrollActivity;
import com.haoshi.service.ServiceActivity;
import com.haoshi.shopcart.ShopCartActivity;
import com.haoshi.sms.SmsActivity;
import com.haoshi.audiorecorder.AudioRecorderActivity;
import com.haoshi.sqlite.greendao.GreenDaoActivity;
import com.haoshi.sqlite.ormlite.OrmliteActivity;
import com.haoshi.swipe.SwipeActivity;
import com.haoshi.tinker.TinkerActivity;
import com.haoshi.toast.StyleableToastActivity;
import com.haoshi.toast.ToastyActivity;
import com.haoshi.tts.TTSActivity;
import com.haoshi.videorecorder.VideoRecorderActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haoshi
 */

public class IndexAdapter extends BaseRecyclerAdapter<IndexAdapter.VH> {

    private Context context;
    private List<ActionBean> list = new ArrayList<>();

    public IndexAdapter(Context context) {
        this.context = context;
        list.add(new ActionBean("AndroidTest", AndroidTestActivity.class));
        list.add(new ActionBean("AudioRecorder", AudioRecorderActivity.class));
        list.add(new ActionBean("BaiduMap", BaiduMapActivity.class));
        list.add(new ActionBean("Bluetooth", BluetoothActivity.class));
        list.add(new ActionBean("CustomView", ViewActivity.class));
        list.add(new ActionBean("Dialog", DialogActivity.class));
        list.add(new ActionBean("EasyRecyclerView", EasyRecyclerActivity.class));
        list.add(new ActionBean("EasyRecyclerViewWithRefresh", EasyRecyclerWithRefreshActivity.class));
        list.add(new ActionBean("ExpandableListView", ExpandableListViewActivity.class));
        //list.add(new ActionBean("FFmpeg", FFmpegActivity.class));
        list.add(new ActionBean("GreenDao", GreenDaoActivity.class));
        list.add(new ActionBean("IndexableRecyclerView", IndexableRecyclerActivity.class));
        list.add(new ActionBean("JavaJs", JavaJsActivity.class));
        list.add(new ActionBean("Lottie", LottieActivity.class));
        list.add(new ActionBean("MVP", MvpActivity.class));
        list.add(new ActionBean("Ormlite", OrmliteActivity.class));
        list.add(new ActionBean("PhotoPicker", PhotoPickerActivity.class));
        list.add(new ActionBean("RecyclerView", RecyclerViewActivity.class));
        list.add(new ActionBean("RxJava", RxJavaActivity.class));
        list.add(new ActionBean("RxJavaCookie", RxJavaCookieActivity.class));
        list.add(new ActionBean("RxJavaMvp", RxJavaMvpActivity.class));
        list.add(new ActionBean("Service", ServiceActivity.class));
        list.add(new ActionBean("ShopCart", ShopCartActivity.class));
        list.add(new ActionBean("SMS", SmsActivity.class));
        list.add(new ActionBean("StickScroll", ScrollActivity.class));
        list.add(new ActionBean("StyleableToast", StyleableToastActivity.class));
        list.add(new ActionBean("SwipeLayout", SwipeActivity.class));
        list.add(new ActionBean("Tinker", TinkerActivity.class));
        list.add(new ActionBean("Toasty", ToastyActivity.class));
        list.add(new ActionBean("TTS", TTSActivity.class));
        list.add(new ActionBean("VideoRecorder", VideoRecorderActivity.class));

        setCustomLoadMoreView(new XRefreshViewFooter(context));
    }

    @Override
    public VH getViewHolder(View view) {
        return new VH(view, false);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {

        View view = LayoutInflater.from(context).inflate(R.layout.index_item, parent, false);
        return new VH(view, true);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, boolean isItem) {
        holder.textView.setText(list.get(position).getAction());
        holder.textView.setOnClickListener(view ->
                context.startActivity(new Intent(context, list.get(position).getCls())));
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

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
