package com.haoshi.shopcart.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haoshi.R;
import com.haoshi.shopcart.ShopCartActivity;
import com.haoshi.shopcart.bean.GoodsBean;

import java.util.List;


/**
 * @author HaoShi
 */

public class GoodsAdapter extends BaseAdapter {

    private ShopCartActivity shopCartActivity;
    private List<GoodsBean> list;
    private CategoryAdapter categoryAdapter;

    public GoodsAdapter(ShopCartActivity shopCartActivity, List<GoodsBean> list, CategoryAdapter categoryAdapter) {
        this.shopCartActivity = shopCartActivity;
        this.list = list;
        this.categoryAdapter = categoryAdapter;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(shopCartActivity).inflate(R.layout.shop_cart_right_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textName = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.textOriginalPrice = (TextView) convertView.findViewById(R.id.text_original_price);
            viewHolder.textPrice = (TextView) convertView.findViewById(R.id.text_price);
            viewHolder.imageAdd = (ImageView) convertView.findViewById(R.id.image_add);
            viewHolder.imageReduce = (ImageView) convertView.findViewById(R.id.image_reduce);
            viewHolder.textCount = (TextView) convertView.findViewById(R.id.text_count);
            viewHolder.imagePic = (ImageView) convertView.findViewById(R.id.image_pic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.textName.setText(list.get(position).getTitle());
        viewHolder.textOriginalPrice.setText("￥" + list.get(position).getOriginalPrice());
        viewHolder.textOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        viewHolder.textPrice.setText("￥" + list.get(position).getPrice());

        if (list.get(position) != null) {
            //默认进来数量
            if (list.get(position).getNum() < 1) {
                viewHolder.textCount.setVisibility(View.INVISIBLE);
                viewHolder.imageReduce.setVisibility(View.INVISIBLE);
                categoryAdapter.notifyDataSetChanged();
            } else {
                viewHolder.textCount.setVisibility(View.VISIBLE);
                viewHolder.imageReduce.setVisibility(View.VISIBLE);
                viewHolder.textCount.setText(String.valueOf(list.get(position).getNum()));
                categoryAdapter.notifyDataSetChanged();
            }
        } else {
            viewHolder.textCount.setVisibility(View.INVISIBLE);
            viewHolder.imageReduce.setVisibility(View.INVISIBLE);
        }

        if (list.get(position).getIcon() != null) {
            Glide.with(shopCartActivity).load(list.get(position).getIcon()).into(viewHolder.imagePic);
        }

        viewHolder.imageAdd.setOnClickListener(v -> {
            int count = shopCartActivity.getSelectedItemCountById(list.get(position).getProductId());
            if (count < 1) {
                viewHolder.imageReduce.setAnimation(getShowAnimation());
                viewHolder.imageReduce.setVisibility(View.VISIBLE);
                viewHolder.textCount.setVisibility(View.VISIBLE);
            }

            shopCartActivity.handlerCarNum(1, list.get(position));
            categoryAdapter.notifyDataSetChanged();

            int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
            v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）

            shopCartActivity.setAnim(startLocation);// 开始执行动画

        });

        viewHolder.imageReduce.setOnClickListener(v -> {
            int count = shopCartActivity.getSelectedItemCountById(list.get(position).getProductId());
            if (count < 2) {
                viewHolder.imageReduce.setAnimation(getHiddenAnimation());
                viewHolder.imageReduce.setVisibility(View.GONE);
                viewHolder.textCount.setVisibility(View.GONE);
            }
            shopCartActivity.handlerCarNum(0, list.get(position));
            categoryAdapter.notifyDataSetChanged();
        });
        return convertView;
    }

    /**
     * 显示减号的动画
     */
    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    /**
     * 隐藏减号的动画
     */
    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    class ViewHolder {
        TextView textName, textOriginalPrice, textPrice, textCount;
        ImageView imagePic, imageAdd, imageReduce;
    }
}
