package com.google.androidqw.ui.zone.adapter;

import android.content.Context;

import com.google.androidqw.ui.zone.bean.FavortItem;
import com.google.androidqw.ui.zone.widegt.FavoriteTextView;

import java.util.List;

/**
 * Created by lyz on 2016/12/27.
 */
public class FavoriteAdpter {

    private FavoriteTextView favoriteTextView;
    private Context mContext;
    List<FavortItem> mDatas;
    public FavoriteAdpter(Context context) {
        mContext = context;
    }

    public void bindListView(FavoriteTextView favoriteTextView) {
        if (null == favoriteTextView) {
            throw new IllegalArgumentException("FavoriteTextView ........ is null ");
        }
        this.favoriteTextView = favoriteTextView;
    }

    public void setDatas(List list) {
        mDatas=list;
        notifyDataSetChanged();
    }

    public void resetDatas() {
        mDatas = null;
        notifyDataSetChanged();
    }



    public void notifyDataSetChanged() {

    }
}
