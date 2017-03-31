package com.haoshi.hao;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.haoshi.R;

/**
 * @author Haoshi
 */

public class ShareDialog extends Dialog {

    private Context context;

    public ShareDialog(Context context, View view) {
        super(context, R.style.BottomDialogStyle);
        this.context = context;
        createDialog(view);
    }

    private void createDialog(View view) {
        //获得dialog的window窗口
        Window window = getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.BottomDialogAnimation);
        //将自定义布局加载到dialog上
        setContentView(view);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams params = window.getAttributes();
        //设置窗口宽度为充满全屏
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(params);
        //按比例设置
        /*WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        params.width = (int) (display.getWidth() * 0.6);
        params.height = (int) (display.getHeight() * 0.7);
        window.setAttributes(params);*/
    }
}
