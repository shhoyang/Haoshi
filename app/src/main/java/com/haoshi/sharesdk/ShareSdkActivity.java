package com.haoshi.sharesdk;

import android.view.Menu;
import android.view.MenuItem;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareSdkActivity extends BaseActivity {

    @Override
    public void initView() {

    }

    @Override
    public void setData() {
        ShareSDK.initSDK(this);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_share_sdk;
    }

    @Override
    public String setTitle() {
        return TAG = ShareSdkActivity.class.getSimpleName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            share();
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("GitHub");
        oks.setTitleUrl("https://github.com/haoshiy/Haoshi");
        oks.setText("欢迎各位同仁star");
        oks.setImageUrl("https://avatars0.githubusercontent.com/u/22501621?v=3&u=3801eec13694e72875faf6aa20e5020ed87047dc&s=400");
        oks.setUrl("https://github.com/haoshiy/Haoshi");
        oks.setComment("欢迎各位同仁star");
        oks.setSite("GitHub");
        oks.setSiteUrl("https://github.com/haoshiy/Haoshi");
        oks.show(this);
    }
}
