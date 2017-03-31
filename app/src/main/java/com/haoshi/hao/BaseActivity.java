package com.haoshi.hao;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.haoshi.R;
import com.haoshi.swipe.SwipeBackActivity;
import com.haoshi.swipe.SwipeBackLayout;

/**
 * @author HaoShi
 */
public abstract class BaseActivity extends SwipeBackActivity implements View.OnClickListener {

    protected static String TAG;

    protected ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);
        View view = View.inflate(this, setContentViewID(), null);
        frameLayout.addView(view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(setTitle());

        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);

        initView();
        setData();
        setListener();
    }

    public abstract void initView();

    public abstract void setData();

    public abstract int setContentViewID();

    public abstract String setTitle();

    public void setListener() {

    }

    @Override
    public void onClick(View v) {
    }
}
