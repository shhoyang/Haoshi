package com.haoshi.shopcart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * @author Haoshi
 *         解决ScrollView中嵌套ListView 显示不正常的问题
 */
public class HaoListView extends ListView {

    public HaoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HaoListView(Context context) {
        super(context);
    }

    public HaoListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}