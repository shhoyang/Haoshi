package com.haoshi.cardslidepanel;

import android.view.View;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CardSlidePanelActivity extends BaseActivity {

    private CardSlidePanel cardSlidePanel;

    private int images[] = {R.mipmap.a1, R.mipmap.a2, R.mipmap.a3, R.mipmap.a4, R.mipmap.a5, R.mipmap.a6};
    private String names[] = {"郭富城", "刘德华", "李连杰", "谢霆锋", "周星驰", "周润发",};
    private List<CardDataItem> dataList = new ArrayList<>();

    @Override
    public void initView() {
        cardSlidePanel = (CardSlidePanel) findViewById(R.id.card_slide_panel);
        cardSlidePanel.setCardSwitchListener(new CardSlidePanel.CardSwitchListener() {
            @Override
            public void onShow(int index) {
            }

            @Override
            public void onCardVanish(int index, int type) {

            }

            @Override
            public void onItemClick(View cardView, int index) {

            }
        });
    }

    @Override
    public void setData() {
        int num = images.length;

        for (int i = 0; i < num; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = names[i];
            dataItem.imageId = images[i];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.zanNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            dataList.add(dataItem);
        }
        cardSlidePanel.fillData(dataList);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_card_slide_panel;
    }

    @Override
    public String setTitle() {
        return TAG = CardSlidePanelActivity.class.getSimpleName();
    }
}
