package com.haoshi.hao;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoshi.R;
import com.haoshi.utils.L;
import com.haoshi.utils.ScreenUtils;

/**
 * Created by Haoshi on 2017/1/7.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected static String TAG;
    protected ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fram);
        View view = View.inflate(this, setContentViewID(), null);
        frameLayout.addView(view);
        
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(setTitle());

        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);

        initView();
        setData();
    }

    public abstract void initView();

    public abstract void setData();

    public abstract int setContentViewID();

    public abstract String setTitle();

    @Override
    public void onClick(View v) {
    }
}
