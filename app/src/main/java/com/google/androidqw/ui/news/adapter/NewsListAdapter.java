package com.google.androidqw.ui.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.androidqw.R;
import com.google.androidqw.bean.NewsSummary;

import java.util.List;

import base.BaseRecycleView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.google.androidqw.R.id.news_summary_photo_iv_left;

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
 * 描 述 ： 2个布局一个
 * 1. 上面文本下面3个图片 ,
 * 2. 左边一个图片, 右边3个文本
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class NewsListAdapter extends BaseRecycleView<NewsSummary> {
    private static final int PARAMS_VALUE_CONTENT = 0;
    private static final int PARAMS_VALUE_PHOTO = 1;


    public NewsListAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(datas.get(position).getDigest())) {
            return PARAMS_VALUE_CONTENT;
        }
        return PARAMS_VALUE_PHOTO;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentItemHolder) {
            ContentItemHolder contentItemHolder= (ContentItemHolder) holder;
            contentItemHolder.setItemDatas(datas.get(position));
        } else if (holder instanceof PhotoItemHolder) {
            PhotoItemHolder contentItemHolder= (PhotoItemHolder) holder;
            contentItemHolder.setItemDatas(datas.get(position));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PARAMS_VALUE_CONTENT) {
            return new ContentItemHolder(View.inflate(parent.getContext(), R.layout.item_news, null));
        } else {
            return new PhotoItemHolder(View.inflate(parent.getContext(), R.layout.item_news_photo, null));
        }
    }

    class ContentItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.news_summary_photo_iv)
        ImageView mNewsSummaryPhotoIv;
        @Bind(R.id.news_summary_title_tv)
        TextView mNewsSummaryTitleTv;
        @Bind(R.id.news_summary_digest_tv)
        TextView mNewsSummaryDigestTv;
        @Bind(R.id.news_summary_ptime_tv)
        TextView mNewsSummaryPtimeTv;
        @Bind(R.id.rl_root)
        RelativeLayout mRlRoot;

        public ContentItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setItemDatas(NewsSummary summary) {
            mNewsSummaryTitleTv.setText(summary.getTitle());
            mNewsSummaryDigestTv.setText(summary.getDigest());
            setImageUrl(summary.getImgsrc(),mNewsSummaryPhotoIv);
        }
    }

    class PhotoItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.news_summary_title_tv)
        TextView mNewsSummaryTitleTv;
        @Bind(news_summary_photo_iv_left)
        ImageView mNewsSummaryPhotoIvLeft;
        @Bind(R.id.news_summary_photo_iv_middle)
        ImageView mNewsSummaryPhotoIvMiddle;
        @Bind(R.id.news_summary_photo_iv_right)
        ImageView mNewsSummaryPhotoIvRight;
        @Bind(R.id.news_summary_photo_iv_group)
        LinearLayout mNewsSummaryPhotoIvGroup;
        @Bind(R.id.news_summary_ptime_tv)
        TextView mNewsSummaryPtimeTv;
        @Bind(R.id.ll_root)
        LinearLayout mLlRoot;

        public PhotoItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItemDatas(NewsSummary summary) {
            mNewsSummaryTitleTv.setText(summary.getTitle());
            mNewsSummaryPtimeTv.setText(summary.getPtime());
            setImageUrl(summary.getImgsrc(),mNewsSummaryPhotoIvMiddle);
        }
    }
}
