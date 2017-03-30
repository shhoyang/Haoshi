package com.haoshi.listview.easyrecyclerwithrefresh;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoshi.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * @author Haoshi
 */

public class EasyAdapter extends RecyclerArrayAdapter<String> {

    public EasyAdapter(Context context) {
        super(context);
    }

    @Override
    public void setNoMore(int res, OnNoMoreListener listener) {
        super.setNoMore(R.layout.view_nomore, new OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {

            }

            @Override
            public void onNoMoreClick() {
                resumeMore();
            }
        });
    }

    @Override
    public void setError(int res, OnErrorListener listener) {
        super.setError(R.layout.view_error, new OnErrorListener() {
            @Override
            public void onErrorShow() {

            }

            @Override
            public void onErrorClick() {
                resumeMore();
            }
        });
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(parent);
    }

    public static class VH extends BaseViewHolder<String> {

        private ImageView imageView;
        private TextView textView;

        public VH(ViewGroup itemView) {
            super(itemView, R.layout.easy_recycler_item_single);
            imageView = $(R.id.image);
            textView = $(R.id.text);
        }

        @Override
        public void setData(String data) {
            super.setData(data);
            imageView.setImageResource(R.mipmap.ic_launcher);
            textView.setText(data);
        }
    }
}
