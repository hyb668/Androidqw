package com.google.androidqw.ui.zone.widegt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.google.androidqw.ui.zone.adapter.FavoriteAdpter;
import com.google.androidqw.ui.zone.spannable.ISpanClick;

/**
 *
 *  点赞列表
 * Created by lyz on 2016/12/26.
 */
public class FavoriteTextView  extends TextView {


    private ISpanClick mSpanClickListener;

    public void setSpanClickListener(ISpanClick spanClickListener) {
        mSpanClickListener = spanClickListener;
    }

    public ISpanClick getSpanClickListener() {
        return mSpanClickListener;
    }
    public FavoriteTextView(Context context) {
        super(context);
    }

    public FavoriteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FavoriteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setAdapter(FavoriteAdpter adapter){

        adapter.bindListView(this);
    }

}
