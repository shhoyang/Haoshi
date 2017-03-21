package com.haoshi.toast;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

import es.dmoral.toasty.Toasty;

import static android.graphics.Typeface.BOLD_ITALIC;

public class ToastyActivity extends BaseActivity {

    @Override
    public void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_toasty;
    }

    @Override
    public String setTitle() {
        return TAG = ToastyActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button:
                Toasty.error(this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.button1:
                Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.button2:
                Toasty.info(this, "Here is some info for you.", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.button3:
                Toasty.warning(this, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.button4:
                Toasty.normal(this, "Normal toast w/o icon").show();
                break;
            case R.id.button5:
                Drawable icon = getResources().getDrawable(R.mipmap.ic_pets_white_48dp);
                Toasty.normal(this, "Normal toast w/ icon", icon).show();
                break;
            case R.id.button6:
                Toasty.info(this, getFormattedMessage()).show();
                break;
        }


    }

    private CharSequence getFormattedMessage() {
        final String prefix = "Formatted ";
        final String highlight = "bold italic";
        final String suffix = " text";
        SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
        int prefixLen = prefix.length();
        ssb.setSpan(new StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}
