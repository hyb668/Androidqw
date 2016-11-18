package com.google.androidqw.ui.news.adapter;

import android.content.Context;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemTypeSupport;
import com.google.androidqw.bean.NewsSummary;

import java.util.List;

/**
 * ============================================================
 * <p/>
 * 版 权 ： 刘宇哲 版权所有 (c) 2016
 * <p/>
 * 作 者 : 刘宇哲
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ：  on 2016/11/18.
 * <p/>
 * 描 述 ：
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class NewsListAdapter extends MultiItemRecycleViewAdapter<NewsSummary> {

    public NewsListAdapter(Context context, List<NewsSummary> datas) {
        super(context, datas, new MultiItemTypeSupport<NewsSummary>() {
            @Override
            public int getLayoutId(int itemType) {
                return 0;
            }

            @Override
            public int getItemViewType(int position, NewsSummary summary) {
                return 0;
            }
        });
    }

    @Override
    public void convert(ViewHolderHelper holder, NewsSummary summary) {

    }
}
