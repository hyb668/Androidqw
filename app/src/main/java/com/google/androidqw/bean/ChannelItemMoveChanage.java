package com.google.androidqw.bean;

/**
 * Created by liuyuzhe on 2017/1/31.
 */

public class ChannelItemMoveChanage {
    private int fromPosition;
    private int toPosition;

    public int getFromPosition() {
        return fromPosition;
    }

    public int getToPosition() {
        return toPosition;
    }

    public ChannelItemMoveChanage(int fromPosition, int toPosition) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }
}
