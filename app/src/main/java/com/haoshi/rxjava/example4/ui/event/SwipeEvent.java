package com.haoshi.rxjava.example4.ui.event;

/**
 * @author Haoshi
 *         <p>
 *         滑动事件
 */

public class SwipeEvent {
    private boolean enable;

    public SwipeEvent(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }
}
