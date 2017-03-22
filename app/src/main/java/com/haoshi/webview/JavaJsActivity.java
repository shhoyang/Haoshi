package com.haoshi.webview;

import android.app.AlertDialog;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.ToastUtils;

/**
 * @author HaoShi
 */
public class JavaJsActivity extends BaseActivity {

    private WebView webView;

    @Override
    public void initView() {
        webView = (WebView) findViewById(R.id.webview);
        // 启用javascript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/hybrid_demo.html");
        webView.addJavascriptInterface(JavaJsActivity.this, "android");
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_java_js;
    }

    @Override
    public String setTitle() {
        return TAG = JavaJsActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                webView.loadUrl("javascript:javacalljs()");
                break;
            case R.id.button1:
                webView.loadUrl("javascript:javacalljswith('JAVA有参调用JS')");
                break;
        }
    }

    //由于安全原因 需要加 @JavascriptInterface
    @JavascriptInterface
    public void startFunction() {
        runOnUiThread(() -> ToastUtils.showShort(JavaJsActivity.this, "js调用了java方法"));
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        runOnUiThread(() -> new AlertDialog.Builder(JavaJsActivity.this).setMessage(text).show());
    }
}
