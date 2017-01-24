package com.google.androidqw.ui.zone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.androidqw.ui.zone.CircleZoneHolder;
import com.google.androidqw.ui.zone.bean.CircleItem;
import com.google.androidqw.ui.zone.prensenter.CirclePrensent;

import base.BaseReclyerViewAdapter;


/**
 * 朋友圈的适配器
 * Created by Administrator on 2016/12/23.
 */
public class CircleZoneAdapter extends BaseReclyerViewAdapter<CircleItem> {
    public static final int ITEM_VIEW_TYPE_IMAGE = 1;
    public static final int ITEM_VIEW_TYPE_URL = 2;
    private final Context mContext;
    private final CirclePrensent mPrensent;
    private int mTitltTabHeight;

    public CircleZoneAdapter(Context context, CirclePrensent circlePrensent, int  titltTabHeight) {
        super(context);
        mContext = context;
        mPrensent = circlePrensent;
        mTitltTabHeight = titltTabHeight;
    }


    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CircleZoneHolder.create(mContext, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof CircleZoneHolder) {
            ((CircleZoneHolder) holder).setData(mPrensent, get(position), position,mTitltTabHeight);
        }
    }
}
