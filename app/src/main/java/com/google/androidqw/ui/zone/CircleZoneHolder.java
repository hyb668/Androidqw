package com.google.androidqw.ui.zone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.androidqw.R;
import com.google.androidqw.ui.zone.adapter.CircleZoneAdapter;
import com.google.androidqw.ui.zone.adapter.CommentAdapter;
import com.google.androidqw.ui.zone.adapter.FavoriteAdpter;
import com.google.androidqw.ui.zone.bean.CircleItem;
import com.google.androidqw.ui.zone.bean.CommentConfig;
import com.google.androidqw.ui.zone.prensenter.CirclePrensent;
import com.google.androidqw.ui.zone.widegt.CommentLinearLayout;
import com.google.androidqw.ui.zone.widegt.ExpandableTextView;
import com.google.androidqw.ui.zone.widegt.FavoriteTextView;
import com.google.androidqw.ui.zone.widegt.MultiImageView;

import java.util.Date;

import app.AppCache;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.toolsfinal.DateUtils;
import utils.CollectionUtils;
import utils.ImageLoaderUtils;
import utils.LogUtils;
import utils.ToastUitl;

/**
 * Created by Administrator on 2016/12/23.
 */
public class CircleZoneHolder extends RecyclerView.ViewHolder {
    private final View mRootView;
    private final Context mContext;
    private final int mType;
    @Bind(R.id.iv_circle_header)
    ImageView mIvCircleHeader;
    @Bind(R.id.tv_circle_user_name)
    TextView mTvCircleUserName;
    @Bind(R.id.tv_circle_create_time)
    TextView mTvCircleCreateTime;

    @Bind(R.id.tv_circle_favorite)
    TextView mTvCircleFavorite;
    @Bind(R.id.tv_tv_circle_comment)
    TextView mTvTvCircleComment;
    @Bind(R.id.iv_circle_input_content_send)
    ImageView mIvCircleInputContentSend;
    @Bind(R.id.linearLayout_comment)
    LinearLayout mLinearLayoutComment;
    @Bind(R.id.tv_url_content)
    TextView mTvUrlContent;

    private MultiImageView multiImageView;
    private CommentAdapter mCommentAdapter;
    private CommentLinearLayout mCommentlinearlayout;
    private FavoriteAdpter mFavoriteAdpter;
    private FavoriteTextView mFavoritetextview;
    private ExpandableTextView mExpandableTextView;
    private ViewStub mViewStub;


    // 返回一个viewholder 给adapter
    public static RecyclerView.ViewHolder create(Context context, int type) {
        return new CircleZoneHolder(LayoutInflater.from(context).inflate(R.layout.item_circle_list, null), context, type);
    }

    //返回的时候初始化一个内容
    public CircleZoneHolder(View itemView, Context context, int type) {
        super(itemView);
        mRootView = itemView;
        mContext = context;
        mType = type;
        initView();
        ButterKnife.bind(this, itemView);
    }

    private void initView() {
        mViewStub = (ViewStub) mRootView.findViewById(R.id.viewStub_LinkOrImg);
        switch (mType) {
            case CircleZoneAdapter.ITEM_VIEW_TYPE_IMAGE:
                mViewStub.setLayoutResource(R.layout.item_circle_viewstub_urlbody);
                break;
            case CircleZoneAdapter.ITEM_VIEW_TYPE_URL:
            default:
                mViewStub.setLayoutResource(R.layout.item_circle_viewstub_imgbody);
                multiImageView = (MultiImageView) mViewStub.inflate();
                break;
        }
        mCommentAdapter = new CommentAdapter(mContext);
        mFavoriteAdpter = new FavoriteAdpter(mContext);
        mFavoritetextview = (FavoriteTextView) mRootView.findViewById(R.id.favoritetextview);
        mCommentlinearlayout = (CommentLinearLayout) mRootView.findViewById(R.id.commentlinearlayout);
        mExpandableTextView = (ExpandableTextView) mRootView.findViewById(R.id.ExpandTextView_content);

        mFavoritetextview.setAdapter(mFavoriteAdpter);
        mCommentlinearlayout.setAdapter(mCommentAdapter);

    }


    public void setData(final CirclePrensent circlePrensent, CircleItem circleItem, int titltTabHeight, final int position) {
        initListener(circlePrensent, circleItem, position);
        switch (mType) {
            case CircleZoneAdapter.ITEM_VIEW_TYPE_IMAGE:
                mViewStub.setVisibility(View.GONE);
                break;
            case CircleZoneAdapter.ITEM_VIEW_TYPE_URL:
            default:
                if (!CollectionUtils.isNullOrEmpty(circleItem.getPictureList())) {
                    multiImageView.setPicPathList(circleItem.getPictureList());
                    multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            ToastUitl.showShort("onclick=" + position);
                        }
                    });
                    multiImageView.setVisibility(View.VISIBLE);
                } else {
                    multiImageView.setVisibility(View.GONE);
                }
                break;
        }
        commentAndPrise(circleItem);

        LogUtils.logd("CircleZoneHolder.setData" + "content==" + circleItem.getContent());
        mExpandableTextView.setVerticalGravity(TextUtils.isEmpty(circleItem.getContent()) ? View.GONE : View.VISIBLE);
        mExpandableTextView.setText(circleItem.getContent(), position,titltTabHeight);
        mExpandableTextView.setExpandedChangeLiener(new ExpandableTextView.ExpandedChangeLiener() {
            @Override
            public void onExpandedChange(boolean isExpand, int p) {
                if (mExpandableTextView.isTextViewOverScreen()) {
                    LogUtils.logd("CircleZoneHolder.onExpandedChange"+isExpand+"position"+p+"&position="+position);
                    circlePrensent.excuteSmoothScrollToPosition(p);
                }
            }
        });
        //头像
        //ImageLoaderUtils.displayRound(mContext, mIvCircleHeader, DatasUtil.getRandomPhotoUrl());
        ImageLoaderUtils.display(mContext, mIvCircleHeader, circleItem.getIcon());
        mTvCircleUserName.setText(circleItem.getNickName());
        mTvCircleCreateTime.setText(DateUtils.formatDate(new Date(circleItem.getCreateTime())));
        mTvUrlContent.setText(circleItem.getContent());


    }

    private void commentAndPrise(CircleItem circleItem) {
        boolean hasComment = !CollectionUtils.isNullOrEmpty(circleItem.getReplys()) ? true : false;
        boolean hasPraise = !CollectionUtils.isNullOrEmpty(circleItem.getGoodjobs()) ? true : false;
        // 处理评论和点赞
        mLinearLayoutComment.setVisibility(View.VISIBLE);
        //赞
        mFavoritetextview.setVisibility(CollectionUtils.isNullOrEmpty(circleItem.getGoodjobs()) ? View.GONE : View.VISIBLE);
        if (hasComment) {
            mCommentAdapter.setDatas(circleItem.getReplys());
            mCommentlinearlayout.setVisibility(View.VISIBLE);
        } else {
            mCommentAdapter.resetDatas();
            mCommentlinearlayout.setVisibility(View.GONE);
        }

        if (hasPraise) {
            mFavoritetextview.setVisibility(View.VISIBLE);
            mFavoriteAdpter.setDatas(circleItem.getGoodjobs());
        } else {
            mFavoritetextview.setVisibility(View.GONE);
        }
    }

    public class updateInputFrameVisibiliy implements View.OnClickListener {

        private final CirclePrensent mPrensent;
        private final int mPosition;
        private int mVisibility;

        public updateInputFrameVisibiliy(CirclePrensent prensent, int position, int visibility) {
            mPrensent = prensent;
            mPosition = position;
            mVisibility = visibility;
        }

        @Override
        public void onClick(View v) {
            mPrensent.updateInputFrameVisibiliy(mVisibility, new CommentConfig(mPosition));
        }
    }

    private void initListener(final CirclePrensent circlePrensent, final CircleItem circleItem, final int circlePos) {
        mLinearLayoutComment.setOnClickListener(new updateInputFrameVisibiliy(circlePrensent, circlePos, View.VISIBLE));
        mTvTvCircleComment.setOnClickListener(new updateInputFrameVisibiliy(circlePrensent, circlePos, View.VISIBLE));

        mCommentlinearlayout.setClickListener(new CommentLinearLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int commentPos) {
                //点击 *  1.  如果是点击自己就显示删除和复制 : 如果点击对方就显示输入框,并默认文本为回复谁谁谁
                if (AppCache.getInstance().getUserId().equals(circleItem.getReplys().get(commentPos).getUserId())) {
                    //自己
                    ToastUitl.show("点击自己");

                } else {
                    //回复别人
                    ToastUitl.show("回复别人");
                    CommentConfig config = new CommentConfig();
                    config.setCirclePosition(circlePos);
                    config.commentType = CommentConfig.Type.REPLY;
                    config.setCommentPosition(commentPos);
                    config.setPublishId(circleItem.getId());
                    config.setPublishUserId(circleItem.getUserId());//动态人userid

                    config.setId(circleItem.getReplys().get(commentPos).getUserId());
                    config.setName(circleItem.getReplys().get(commentPos).getUserNickname());
                    circlePrensent.updateInputFrameVisibiliy(View.VISIBLE, config);
                }
            }
        });
        mCommentlinearlayout.setLongClickListener(new CommentLinearLayout.OnItemLongClickListener() {
            @Override
            public void onLongItemClick(int position) {
                //     *  2 . 长按如果是对方就是显示复制,如果是点击自己就显示删除和复制
                //点击 *  1.  如果是点击自己就显示删除和复制 : 如果点击对方就显示输入框,并默认文本为回复谁谁谁
                if (AppCache.getInstance().getUserId().equals(circleItem.getUserId())) {
                    //自己
                    ToastUitl.show("long pressed onclick myself");
                } else {
                    //回复别人
                    ToastUitl.show("long pressed reply his");
                }
            }
        });
    }


}
