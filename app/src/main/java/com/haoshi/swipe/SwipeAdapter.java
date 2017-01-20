package com.haoshi.swipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.haoshi.R;
import com.haoshi.utils.ToastUtils;

/**
 * @author HaoShi
 */
public class SwipeAdapter extends BaseSwipeAdapter {

    private Context context;

    public SwipeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int i, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.swipe_item, null);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(i));
        Button btnUpdate = (Button) v.findViewById(R.id.swipe_update);
        Button btnDelete = (Button) v.findViewById(R.id.swipe_delete);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.close();
                ToastUtils.showShort(context, "点击了修改");
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.close();
                ToastUtils.showShort(context, "点击了删除");
            }
        });
        return v;
    }

    @Override
    public void fillValues(int i, View view) {
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
