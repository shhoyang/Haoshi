package com.haoshi.view;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoShi
 */
public class ViewActivity extends BaseActivity {

    private AutoViewPager viewPager;
    private List<View> imageList = new ArrayList<>();
    private int count;

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
        return TAG = ViewActivity.class.getSimpleName();
    }

    private void initViewPager() {
        viewPager = (AutoViewPager) findViewById(R.id.viewpager);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_point);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(12, 12);
        pointParams.leftMargin = 8;
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

        count = imageList.size();
        viewPager.setPoints(pointList);
        viewPager.setInterval(3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(count * 1000);
        viewPager.thread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewPager.isContinue = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewPager.isContinue = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.destroy = true;
    }

    private PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return Short.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = imageList.get(position % count);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageList.get(position % count));
        }
    };
}
