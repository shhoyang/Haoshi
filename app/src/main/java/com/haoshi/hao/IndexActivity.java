package com.haoshi.hao;

import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.haoshi.R;
import com.haoshi.customview.MarqueeTextView;
import com.haoshi.customview.xrefreshview.SmileyHeaderView;
import com.haoshi.utils.ScreenUtils;
import com.haoshi.utils.ToastUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author HaoShi
 */
public class IndexActivity extends BaseActivity implements XRefreshView.XRefreshViewListener, PlatformActionListener {

    private MarqueeTextView marqueeTextView;
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private ShareDialog shareDialog = null;
    private ShareDialog loginDialog = null;
    private Handler handler = new Handler();

    private boolean isExit = false;

    @Override
    public void initView() {
        ShareSDK.initSDK(this);
        setEnableSwipe(false);
        marqueeTextView = (MarqueeTextView) findViewById(R.id.marquee);
        marqueeTextView.setText("天行健,君子以自强不息;地势坤,君子以厚德载物");
        marqueeTextView.setSpeed(ScreenUtils.getScreenWidth(this) / 300);
        marqueeTextView.setFontColor("#FFFFFF");
        marqueeTextView.init(getWindowManager());

        xRefreshView = (XRefreshView) findViewById(R.id.refreshview);
        xRefreshView.setPullRefreshEnable(true);
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setPinnedTime(500);
        xRefreshView.setCustomHeaderView(new SmileyHeaderView(this));
        xRefreshView.setXRefreshViewListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new IndexAdapter(this));

        findViewById(R.id.button_up).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        marqueeTextView.startScroll();
    }

    @Override
    protected void onStop() {
        super.onStop();
        marqueeTextView.stopScroll();
    }

    @Override
    public void setData() {
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_index;
    }

    @Override
    public String setTitle() {
        TAG = IndexActivity.class.getSimpleName();
        return "豪〤世";
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_up:
                recyclerView.scrollToPosition(0);
                break;
            case R.id.share_qq:
                share(QQ.NAME);
                break;
            case R.id.share_qzone:
                share(QZone.NAME);
                break;
            case R.id.share_wechat:
                share(Wechat.NAME);
                break;
            case R.id.share_moments:
                share(WechatMoments.NAME);
                break;
            case R.id.login_qq:
                login();
                break;
            case R.id.login_wechat:
                login();
                break;
            case R.id.login_message:
                login();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                showShare();
                break;
            case R.id.action_login:
                showLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(() -> xRefreshView.stopRefresh(), 1500);
    }

    @Override
    public void onRefresh(boolean isPullDown) {

    }

    @Override
    public void onLoadMore(boolean isSilence) {
        handler.postDelayed(() -> {
            //xRefreshView.setLoadComplete(true); //无更多数据
            xRefreshView.stopLoadMore(); //成功/失败
        }, 1000);
    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    private void showShare() {
        if (shareDialog == null) {
            View view = View.inflate(this, R.layout.dialog_share, null);
            view.findViewById(R.id.share_qq).setOnClickListener(this);
            view.findViewById(R.id.share_qzone).setOnClickListener(this);
            view.findViewById(R.id.share_wechat).setOnClickListener(this);
            view.findViewById(R.id.share_moments).setOnClickListener(this);
            shareDialog = new ShareDialog(this, view);
        }

        shareDialog.show();
    }

    private void share(String name) {
        shareDialog.dismiss();
        Platform.ShareParams params = new Platform.ShareParams();
        params.setShareType(Platform.SHARE_TEXT);
        params.setShareType(Platform.SHARE_WEBPAGE);
        params.setTitle(getString(R.string.app_name));
        params.setTitleUrl(getString(R.string.github_url));
        params.setText(getString(R.string.text));
        params.setUrl(getString(R.string.github_url));
        params.setSite(getString(R.string.github));
        params.setSiteUrl(getString(R.string.github_url));
        params.setImageUrl("https://avatars0.githubusercontent.com/u/22501621?v=3&u=3801eec13694e72875faf6aa20e5020ed87047dc&s=400");
        Platform platform = ShareSDK.getPlatform(name);
        platform.setPlatformActionListener(this);
        platform.share(params);
    }

    private void showLogin() {
        if (loginDialog == null) {
            View view = View.inflate(this, R.layout.dialog_login, null);
            view.findViewById(R.id.login_qq).setOnClickListener(this);
            view.findViewById(R.id.login_wechat).setOnClickListener(this);
            view.findViewById(R.id.login_message).setOnClickListener(this);
            loginDialog = new ShareDialog(this, view);
        }

        loginDialog.show();
    }

    private void login() {
        loginDialog.dismiss();
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ToastUtils.showShort(this, "分享成功!");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtils.showShort(this, "分享成功!");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtils.showShort(this, "分享取消!");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (isExit) {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                } else {
                    ToastUtils.showShort(this, "再按一次退出");
                    isExit = true;
                    handler.postDelayed(() -> isExit = false, 3000);
                }
                break;
        }
        return false;
    }
}
