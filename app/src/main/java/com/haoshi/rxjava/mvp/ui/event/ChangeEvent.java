package com.haoshi.rxjava.mvp.ui.event;

/**
 * @author Haoshi
 */

public class ChangeEvent {
    private boolean isGrid;

    public ChangeEvent(boolean isGrid) {
        this.isGrid = isGrid;
    }

    public boolean isGrid() {
        return isGrid;
    }
}
