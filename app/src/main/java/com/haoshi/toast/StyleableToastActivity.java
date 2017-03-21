package com.haoshi.toast;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class StyleableToastActivity extends BaseActivity {

    private StyleableToast styleableToast;

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_styleable_toast;
    }

    @Override
    public String setTitle() {
        return TAG = StyleableToastActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                styleableToast = new StyleableToast(this, "Profile saved", Toast.LENGTH_LONG);
                styleableToast.setBackgroundColor(Color.parseColor("#3b5998"));
                styleableToast.setMaxAlpha();
                styleableToast.show();
                break;
            case R.id.button1:
                styleableToast = new StyleableToast(this.getApplicationContext(), "PHONE IS OVERHEATING!", Toast.LENGTH_LONG);
                styleableToast.setCornerRadius(5);
                styleableToast.setBackgroundColor(Color.BLACK);
                styleableToast.setTextColor(Color.RED);
                styleableToast.setBoldText();
                styleableToast.show();
                break;
            case R.id.button2:
                styleableToast = new StyleableToast
                        .Builder(this, "Turn off fly mode")
                        .withBackgroundColor(Color.parseColor("#865aff"))
                        .withIcon(R.drawable.ic_airplanemode_inactive_black_24dp)
                        .withMaxAlpha()
                        .build();
                styleableToast.show();
                break;
            case R.id.button3:
                styleableToast = new StyleableToast(this, "Updating profile", Toast.LENGTH_LONG);
                styleableToast.setBackgroundColor(Color.parseColor("#ff5a5f"));
                styleableToast.setTextColor(Color.WHITE);
                styleableToast.setIcon(R.drawable.ic_autorenew_black_24dp);
                styleableToast.spinIcon();
                styleableToast.setMaxAlpha();
                styleableToast.show();
                break;
            case R.id.button4:
                StyleableToast.makeText(this, "Picture saved in gallery", Toast.LENGTH_LONG, R.style.StyleableToast).show();
                break;
        }
    }
}
