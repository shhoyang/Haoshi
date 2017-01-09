package com.haoshi.view;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.IndexActivity;
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
}
