package com.haoshi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Haoshi on 2017/1/8.
 */

public class HorizontalProgressBar extends ProgressBar {

    private String strProgress;
    private Paint paint;

    public HorizontalProgressBar(Context context) {
        super(context);
        initPaint();
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    @Override
    public void setProgress(int progress) {
        super.setProgress(progress);
        setText(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        paint.getTextBounds(strProgress, 0, strProgress.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(strProgress, x, y, paint);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
    }

    private void setText(int progress) {
        strProgress = (int) ((progress * 1.0f / this.getMax()) * 100) + "%";
    }
}
