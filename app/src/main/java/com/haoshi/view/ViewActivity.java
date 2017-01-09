package com.haoshi.view;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.ImageUtils;

public class ViewActivity extends BaseActivity {

    @Override
    public void initView() {
        CircleImageView circleImage = (CircleImageView) findViewById(R.id.circle_image);
        circleImage.setImageBitmap(ImageUtils.createImageThumbnail(getResources(), R.mipmap.lamborghini));
        CircleImageView clickImage = (CircleImageView) findViewById(R.id.click_image);
        clickImage.setImageBitmap(ImageUtils.createImageThumbnail(getResources(), R.mipmap.lamborghini));
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_view;
    }

    @Override
    public void setData() {
        TAG = ViewActivity.class.getSimpleName();
        setTitle(TAG);
    }
}
