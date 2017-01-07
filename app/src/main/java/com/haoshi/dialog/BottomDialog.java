package com.haoshi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.haoshi.R;

/**
 * Created by Haoshi on 2017/1/7.
 */

public class BottomDialog extends Dialog {

    private Context mContext;
    private Window mWindow = null;

    public BottomDialog(Context context) {
        super(context, R.style.BottomDialogStyle);
        mContext = context;
    }

    public void showDiaog(int layoutID) {
        //获得dialog的window窗口
        mWindow = getWindow();
        //设置dialog在屏幕底部
        mWindow.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        mWindow.setWindowAnimations(R.style.BottomDialogAnimation);
        //将自定义布局加载到dialog上
        setContentView(layoutID);
        mWindow.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams params = mWindow.getAttributes();
        //设置窗口宽度为充满全屏
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        mWindow.setAttributes(params);
    }
}

