package com.google.androidqw.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import utils.LogUtils;

/**
 * Created by liuyuzhe on 2017/1/30.
 */

public class ItemDragHelpterCallBack extends ItemTouchHelper.Callback {

    public ItemDragHelpterCallBack() {
    }

    private boolean mLongPressEnabled;

    //    public void setMoveListener(OnItemMoveListener moveListener) {
//        mMoveListener = moveListener;
//    }
    //我们定义一个有参的构造方法，顺便让外界吧需要的回调给传递进来 ，要一个接口可以个实现类
    public ItemDragHelpterCallBack(OnItemMoveListener moveListener) {
        mMoveListener = moveListener;
    }


    public interface OnItemMoveListener {
        boolean onItemMove(int fromPosition, int toPosition);
    }


    private OnItemMoveListener mMoveListener;

    public OnItemMoveListener getMoveListener() {
        return mMoveListener;
    }

    //让外界选择是否可以长按
    @Override
    public boolean isLongPressDragEnabled() {
        return mLongPressEnabled;
    }

    public void setLongPressEnable(boolean longPressEnabled) {
        mLongPressEnabled = longPressEnabled;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP ;
        int swipeFlags = 0;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager || recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            dragFlags =    ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        boolean itemMove = mMoveListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        boolean sameItemViewType = isSameItemViewType(viewHolder, target);
        return itemMove && sameItemViewType;
    }

    //说明是移动到同一个类型的item上
    private boolean isSameItemViewType(RecyclerView.ViewHolder holder, RecyclerView.ViewHolder target) {
        return holder.getItemViewType() == target.getItemViewType();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        LogUtils.logd("ItemDragHelpterCallBack.onSwiped." + direction);

    }
}
