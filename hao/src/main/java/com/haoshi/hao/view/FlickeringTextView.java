package com.haoshi.hao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.haoshi.hao.R;

/**
 * @author Haoshi
 *         闪烁的TextView
 */
public class FlickeringTextView extends TextView {

    private int width;
    private TextPaint paint;
    private LinearGradient linearGradient;
    private Matrix matrix;
    private int translate;

    private int textColor = 0xFFFFFFFF;//文字颜色
    private int flickeringColor = 0xFF000000;//闪烁颜色

    private static final int[] ATTRS = new int[]{
            android.R.attr.textColor,
    };

    public FlickeringTextView(Context context) {
        this(context, null);
    }

    public FlickeringTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlickeringTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //先获取系统属性，文字颜色
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        textColor = a.getColor(0, textColor);
        a.recycle();
        //再获取自定义属性，闪烁颜色
        a = context.obtainStyledAttributes(attrs, R.styleable.FlickeringTextView);
        flickeringColor = a.getColor(R.styleable.FlickeringTextView_flickeringColor, flickeringColor);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //文字边框
      /*  Paint paint1 = new Paint();
        paint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        paint1.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        paint2.setColor(Color.YELLOW);
        paint2.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint1);
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, paint2);
        canvas.save();
        canvas.translate(10,0);
        canvas.restore();*/

        super.onDraw(canvas);
        if (matrix != null) {
            translate += width / 5;
            if (translate > width * 2) {
                translate = -width;
            }
            matrix.setTranslate(translate, 0);
            linearGradient.setLocalMatrix(matrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (width == 0) {
            width = getMeasuredWidth();
            if (width > 0) {
                paint = getPaint();
                linearGradient = new LinearGradient(
                        0,//渐变起初点坐标x位置
                        0,//渐变起初点坐标y位置
                        width,//渐变终点
                        0,//渐变终点
                        new int[]{textColor, flickeringColor, textColor},//参与渐变效果的颜色集合
                        null,//每个颜色处于的渐变相对位置
                        Shader.TileMode.CLAMP//平铺方式
                );
                paint.setShader(linearGradient);
                matrix = new Matrix();
            }
        }
    }
}
