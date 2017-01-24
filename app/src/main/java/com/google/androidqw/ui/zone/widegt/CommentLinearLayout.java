package com.google.androidqw.ui.zone.widegt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.google.androidqw.ui.zone.adapter.CommentAdapter;

/**
 *  评论的列表
 * Created by lyz on 2016/12/26.
 */
public class CommentLinearLayout extends LinearLayout {

    private  OnItemClickListener mClickListener;
    private  OnItemLongClickListener mLongClickListener;

    public OnItemClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    public OnItemLongClickListener getLongClickListener() {
        return mLongClickListener;
    }

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }
    public interface  OnItemLongClickListener{
        void  onLongItemClick(int position);
    }



    public CommentLinearLayout(Context context) {
        super(context);
    }

    public CommentLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // STOPSHIP: 2016/12/27 容器给adapter
    public void setAdapter(CommentAdapter adapter){
        adapter.bindListView(this);
    }

    
}
