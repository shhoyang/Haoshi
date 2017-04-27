package com.haoshi.shopcart.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.shopcart.ShopCartActivity;
import com.haoshi.shopcart.bean.GoodsBean;

/**
 * @author HaoShi
 */

public class ProductAdapter extends BaseAdapter {

    private ShopCartActivity shopCartActivity;
    private GoodsAdapter goodsAdapter;
    private SparseArray<GoodsBean> list;

    public ProductAdapter(ShopCartActivity shopCartActivity, GoodsAdapter goodsAdapter, SparseArray<GoodsBean> list) {
        this.shopCartActivity = shopCartActivity;
        this.goodsAdapter = goodsAdapter;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.valueAt(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_cart_product_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textName = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.textPrice = (TextView) convertView.findViewById(R.id.text_price);
            viewHolder.textCount = (TextView) convertView.findViewById(R.id.text_count);
            viewHolder.imageAdd = (ImageView) convertView.findViewById(R.id.image_add);
            viewHolder.imageReduce = (ImageView) convertView.findViewById(R.id.image_reduce);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.textName.setText(list.valueAt(position).getTitle());
        viewHolder.textPrice.setText("￥" + list.valueAt(position).getPrice());
        viewHolder.textCount.setText(String.valueOf(list.valueAt(position).getNum()));

        viewHolder.imageAdd.setOnClickListener(v -> {
            shopCartActivity.handlerCarNum(1, list.valueAt(position));
            goodsAdapter.notifyDataSetChanged();

        });
        viewHolder.imageReduce.setOnClickListener(v -> {
            shopCartActivity.handlerCarNum(0, list.valueAt(position));
            goodsAdapter.notifyDataSetChanged();
        });

        return convertView;
    }

    class ViewHolder {
        TextView textName, textPrice, textCount;
        ImageView imageAdd, imageReduce;
    }
}