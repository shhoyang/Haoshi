package com.haoshi.shopcart.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class HaoScrollView extends ScrollView {

    private HaoScrollView scrollViewListener = null;

    public HaoScrollView(Context context) {
        super(context);
    }

    public HaoScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HaoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(HaoScrollView scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }
}  