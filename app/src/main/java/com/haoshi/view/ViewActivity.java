package com.haoshi.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.IndexActivity;
import com.haoshi.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends BaseActivity {

    @Override
    public void initView() {

        initViewPager();
        
        CircleImageView clickImage = (CircleImageView) findViewById(R.id.click_image);
        clickImage.setImageBitmap(ImageUtils.createImageThumbnail(getResources(), R.mipmap.lamborghini));
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_view;
    }

    @Override
    public String setTitle() {
        return TAG = IndexActivity.class.getSimpleName();
    }

    private void initViewPager() {
        AutoViewPager viewPager = (AutoViewPager) findViewById(R.id.viewpager);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_point);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(12, 12);
        pointParams.leftMargin = 8;
        List<View> imageList = new ArrayList<>();
        List<ImageView> pointList = new ArrayList<>();
        ImageView image;
        for (int i = 0; i < 6; i++) {
            image = new ImageView(this);
            image.setLayoutParams(imageParams);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setImageBitmap(ImageUtils.createImageThumbnail(getResources(), R.mipmap.lamborghini));
            imageList.add(image);

            image = new ImageView(this);
            image.setLayoutParams(pointParams);
            image.setBackgroundResource(R.mipmap.normal);
            pointList.add(image);
            linearLayout.addView(image);
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(imageList);
        viewPager.setPoints(pointList);
        viewPager.setInterval(3);
        viewPager.setAdapter(adapter);
        viewPager.thread.start();
    }
}
