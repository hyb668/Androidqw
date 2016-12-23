package com.google.androidqw.ui.zone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.google.androidqw.R;
import com.google.androidqw.ui.zone.bean.CircleItem;
import com.google.androidqw.ui.zone.prensenter.CirclePrensent;

/**
 * Created by Administrator on 2016/12/23.
 */
public class CircleZoneHolder extends RecyclerView.ViewHolder {
    private final View mRootView;
    private final Context mContext;
    private final int mType;
    // 返回一个viewholder 给adapter
    public static RecyclerView.ViewHolder create(Context context, int type) {
        return  new CircleZoneHolder(LayoutInflater.from(context).inflate(R.layout.listview_footer, null),context,type);
    }
    //返回的时候初始化一个内容
    public CircleZoneHolder(View itemView, Context context , int type) {
          super(itemView);
        mRootView = itemView;
        mContext = context;
        mType = type;
        initView();
    }

    private void initView() {
        // TODO: 2016/12/23  布局view尚未写
     //   mRootView.findViewById()
    }



    public void setData(CirclePrensent circlePrensent, CircleItem circleItem, int position) {

    }
}
