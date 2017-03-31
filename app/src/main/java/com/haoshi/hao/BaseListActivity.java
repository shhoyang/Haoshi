package com.haoshi.hao;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.haoshi.R;
import com.haoshi.swipe.SwipeBackActivity;
import com.haoshi.swipe.SwipeBackLayout;

/**
 * @author HaoShi
 */
public abstract class BaseListActivity extends SwipeBackActivity implements View.OnClickListener {

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
        setGesture();
        setData();
        setListener();
    }

    public abstract void initView();

    public abstract View getListView();

    public abstract void setData();

    public abstract int setContentViewID();

    public abstract String setTitle();

    public void setListener() {}

    private float lastX = 0;
    private float newX = 0;
    private float offsetX = 0;
    private float lastY = 0;
    private float newY = 0;
    private float offsetY = 0;

    private void setGesture(){
        View listView = getListView();
        if(listView == null){
            return;
        }
        listView.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                lastX = motionEvent.getRawX();
                lastY = motionEvent.getRawY();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                newX = motionEvent.getRawX();
                newY = motionEvent.getRawY();

                offsetX = Math.abs(newX - lastX);
                lastX = newX;

                offsetY = Math.abs(newY - lastY);
                lastY = newY;

                getSwipeBackLayout().setEnablePullToBack(offsetY < offsetX);
            }

            return false;
        });
    }

    @Override
    public void onClick(View v) {

    }
}
