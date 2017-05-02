package com.haoshi.cardslidepanel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.haoshi.R;

/**
 * @author Haoshi
 *         <p>
 *         卡片View项
 */
public class CardItemView extends FrameLayout {

    private Context context;
    private Spring springX, springY;
    private ImageView imageShow;
    private TextView textPicNum, textName, textZanNum, textLikeNum;
    private View topLayout, bottomLayout;
    public View maskView;
    private CardSlidePanel parentView;

    public CardItemView(Context context) {
        this(context, null);
    }

    public CardItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        inflate(context, R.layout.card_slide_panel_item, this);
        imageShow = (ImageView) findViewById(R.id.image_show);
        maskView = findViewById(R.id.view_mask);
        textName = (TextView) findViewById(R.id.text_name);
        textPicNum = (TextView) findViewById(R.id.text_pic_num);
        textZanNum = (TextView) findViewById(R.id.text_zan_num);
        textLikeNum = (TextView) findViewById(R.id.text_like_num);
        topLayout = findViewById(R.id.top);
        bottomLayout = findViewById(R.id.bottom);
        initSpring();
    }

    private void initSpring() {
        SpringConfig springConfig = SpringConfig.fromBouncinessAndSpeed(15, 20);
        SpringSystem mSpringSystem = SpringSystem.create();
        springX = mSpringSystem.createSpring().setSpringConfig(springConfig);
        springY = mSpringSystem.createSpring().setSpringConfig(springConfig);

        springX.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int xPos = (int) spring.getCurrentValue();
                setScreenX(xPos);
                parentView.onViewPosChanged(CardItemView.this);
            }
        });

        springY.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int yPos = (int) spring.getCurrentValue();
                setScreenY(yPos);
                parentView.onViewPosChanged(CardItemView.this);
            }
        });
    }

    public void fillData(CardDataItem itemData) {
        Glide.with(context).load(itemData.imageId).into(imageShow);
        textName.setText(itemData.userName);
        textPicNum.setText(itemData.imageNum + "");
        textZanNum.setText(itemData.zanNum + "");
        textLikeNum.setText(itemData.likeNum + "");
    }


    /**
     * 动画移动到某个位置
     */
    public void animTo(int xPos, int yPos) {
        setCurrentSpringPos(getLeft(), getTop());
        springX.setEndValue(xPos);
        springY.setEndValue(yPos);
    }

    /**
     * 设置当前spring位置
     */
    private void setCurrentSpringPos(int xPos, int yPos) {
        springX.setCurrentValue(xPos);
        springY.setCurrentValue(yPos);
    }

    public void setScreenX(int screenX) {
        this.offsetLeftAndRight(screenX - getLeft());
    }

    public void setScreenY(int screenY) {
        this.offsetTopAndBottom(screenY - getTop());
    }

    public void setParentView(CardSlidePanel parentView) {
        this.parentView = parentView;
    }

    public void onStartDragging() {
        springX.setAtRest();
        springY.setAtRest();
    }

    /**
     * 判断(x, y)是否在可滑动的矩形区域内
     * 这个函数也被CardSlidePanel调用
     *
     * @param x 按下时的x坐标
     * @param y 按下时的y坐标
     * @return 是否在可滑动的矩形区域
     */
    public boolean shouldCapture(int x, int y) {
        int captureLeft = getLeft() + topLayout.getPaddingLeft();
        int captureTop = getTop() + topLayout.getTop() + topLayout.getPaddingTop();
        int captureRight = getRight() - bottomLayout.getPaddingRight();
        int captureBottom = getBottom() - getPaddingBottom() - bottomLayout.getPaddingBottom();

        if (x > captureLeft && x < captureRight && y > captureTop && y < captureBottom) {
            return true;
        }
        return false;
    }
}
