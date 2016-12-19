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
import utils.CollectionUtils;
import utils.DisplayUtil;

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
            ContentItemHolder contentItemHolder = (ContentItemHolder) holder;
            contentItemHolder.setItemDatas(datas.get(position));
        } else if (holder instanceof PhotoItemHolder) {
            PhotoItemHolder contentItemHolder = (PhotoItemHolder) holder;
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
            mNewsSummaryPtimeTv.setText(summary.getPtime());
            setImageUrl(summary.getImgsrc(), mNewsSummaryPhotoIv);
        }
    }

    class PhotoItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.news_summary_title_tv)
        TextView mNewsSummaryTitleTv;
        @Bind(R.id.news_summary_photo_iv_left)
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
            ViewGroup.LayoutParams params = mLlRoot.getLayoutParams();
            params.width=DisplayUtil.getScreenWidth(itemView.getContext());
            mLlRoot.setLayoutParams(params);
        }

        public void setItemDatas(NewsSummary summary) {
            mNewsSummaryTitleTv.setText(summary.getTitle());
            mNewsSummaryPtimeTv.setText(summary.getPtime());
            //setImageUrl(summary.getImgsrc(), mNewsSummaryPhotoIvMiddle);
            setImagDatas(summary);
        }


        private void setImagDatas(NewsSummary summary) {
            int PhotoThreeHeight = DisplayUtil.dip2px(90);
            int PhotoTwoHeight = DisplayUtil.dip2px(120);
            int PhotoOneHeight = DisplayUtil.dip2px(150);
            if (!CollectionUtils.isNullOrEmpty(summary.getAds())) {
                setPicVisibleAndDatas("_01", summary.getAds());
            } else if (!CollectionUtils.isNullOrEmpty(summary.getImgextra())) {
                setPicVisibleAndDatas("_02", summary.getImgextra());
            } else {
                //无数据
                String imgsrc = summary.getImgsrc();
                setVisible(mNewsSummaryPhotoIvLeft, true);
                setImageUrl(imgsrc, mNewsSummaryPhotoIvLeft);
                ViewGroup.LayoutParams params = mNewsSummaryPhotoIvGroup.getLayoutParams();
                params.height=PhotoOneHeight;
                mNewsSummaryPhotoIvGroup.setLayoutParams(params);
            }
        }

        private void setPicVisibleAndDatas(String type, List list) {
            int PhotoThreeHeight = DisplayUtil.dip2px(90);
            int PhotoTwoHeight = DisplayUtil.dip2px(120);
            int PhotoOneHeight = DisplayUtil.dip2px(150);
            ViewGroup.LayoutParams params = mNewsSummaryPhotoIvGroup.getLayoutParams();

            //无数据,肯定不会走这里
            setVisible(mNewsSummaryPhotoIvLeft, false) ;
            setVisible(mNewsSummaryPhotoIvMiddle, false);
            setVisible(mNewsSummaryPhotoIvRight, false);
            switch (list.size()) {
                case 1:
                    setVisible(mNewsSummaryPhotoIvLeft, true);
                    setImageUrl(getPicStr(type, list, 0), mNewsSummaryPhotoIvLeft);
                    params.height=PhotoOneHeight;
                    break;
                case 2:
                    setVisible(mNewsSummaryPhotoIvLeft, true);
                    setVisible(mNewsSummaryPhotoIvMiddle, true);
                    setImageUrl(getPicStr(type, list, 0), mNewsSummaryPhotoIvLeft);
                    setImageUrl(getPicStr(type, list, 1), mNewsSummaryPhotoIvMiddle);
                    params.height=PhotoTwoHeight;
                    break;
                default:
                    setMuitImage(type, list);
                    params.height=PhotoThreeHeight;
                    break;
            }


            mNewsSummaryPhotoIvGroup.setLayoutParams(params);
        }

        private void setMuitImage(String type, List list) {
            setVisible(mNewsSummaryPhotoIvLeft, true);
            setVisible(mNewsSummaryPhotoIvMiddle, true);
            setVisible(mNewsSummaryPhotoIvRight, true);
            setImageUrl(getPicStr(type, list, 0), mNewsSummaryPhotoIvLeft);
            setImageUrl(getPicStr(type, list, 1), mNewsSummaryPhotoIvMiddle);
            setImageUrl(getPicStr(type, list, 2), mNewsSummaryPhotoIvRight);
        }

        private String getPicStr(String type, List list, int position) {
            if ("_01".equals(type)) {
                NewsSummary.AdsBean adsBean = (NewsSummary.AdsBean) list.get(position);
                return adsBean.getImgsrc();
            } else if ("_02".equals(type)) {
                NewsSummary.ImgextraBean imgextraBean = (NewsSummary.ImgextraBean) list.get(position);
                return imgextraBean.getImgsrc();
            }
            return "";
        }
    }


    @Override
    public void replaceAll(List<NewsSummary> elements) {
        if (datas.size() > 0) {
            datas.clear();
        }
        datas.addAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(List<NewsSummary> elements) {
        datas.addAll(elements);
        notifyDataSetChanged();
    }
}
