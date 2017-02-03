package com.google.androidqw.ui.news.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.google.androidqw.R;
import com.google.androidqw.bean.VideoData;
import com.google.androidqw.ui.news.contract.VideoListContract;
import com.google.androidqw.ui.news.model.VideoListModel;
import com.google.androidqw.ui.news.prensenter.VideoListPrensenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import app.AppConstant;
import base.BaseFragment;
import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import unvieradapter.ViewHolderHelper;
import unvieradapter.recyclerview.CommonRecycleViewAdapter;
import utils.CollectionUtils;
import utils.ImageLoaderUtils;
import utils.LogUtils;

/**
 * Created by liuyuzhe on 2017/1/31.
 */

public class VideoFragment extends BaseFragment<VideoListPrensenter, VideoListModel> implements VideoListContract.View {
    @Bind(R.id.xrecycleView)
    XRecyclerView mXrecycleView;
    private int startPage = 0;
    private CommonRecycleViewAdapter mVideoAdapter;
    private String mTyep;

    @Override
    protected int getLayoutResource() {
        return R.layout.frament_news_video;
    }


    @Override
    //this是当前class类 , mModel 就是 videoModel
    public void initPresenter() {
        mPresenter.setVM(this, mModel);

    }

    @Override
    protected void initView() {
        mTyep = getArguments().getString(AppConstant.VIDEO_TYPE);

        mXrecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mXrecycleView.setPullRefreshEnabled(true);
        mXrecycleView.setLoadingMoreEnabled(true);
        mXrecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                startPage = 0;
                mPresenter.requestVideoDatas(mTyep, startPage);
            }

            @Override
            public void onLoadMore() {
                mPresenter.requestVideoDatas(mTyep, startPage);
            }
        });//http://c.m.163.com/nc/video/list/V9LG4B3A0/n/0-10.html


        mXrecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isDownScroll = true;
                if (dy > 25) {
                    //隐藏
                    mRxManager.post(AppConstant.MENU_SHOW_HIDE, isDownScroll);
                } else if (dy < -25) {
                    isDownScroll = false;
                    mRxManager.post(AppConstant.MENU_SHOW_HIDE, isDownScroll);

                }
            }
        });


        setVideoAdapter();
        if (mVideoAdapter.getAll().size() <= 0) {
            mPresenter.requestVideoDatas(mTyep, startPage);
        }
    }

    private void setVideoAdapter() {
        mVideoAdapter = new CommonRecycleViewAdapter<VideoData>(getContext(), R.layout.item_video) {
            @Override
            public void convert(ViewHolderHelper helper, VideoData videoData) {
                LogUtils.logd("VideoFragment.convert." + videoData + "-----");
                JCVideoPlayerStandard videoPlay = helper.getView(R.id.videoPlay);
                videoPlay.setUp(videoData.getMp4_url(), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        TextUtils.isEmpty(videoData.getDescription()) ? videoData.getTitle() + " " : videoData.getDescription());


                helper.setImageRoundUrl(R.id.iv_video_header, videoData.getTopicImg());
                helper.setText(R.id.tv_video_name, videoData.getTopicName());
                helper.setText(R.id.tv_video_play_count,
                        String.format(getResources().getString(R.string.video_play_times),
                                String.valueOf(videoData.getPlayCount())));

//                Glide.with(context).load(url)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .error(com.example.common.R.drawable.toux2)
//                        .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);

                ImageLoaderUtils.display(mContext, videoPlay.thumbImageView, videoData.getCover(), 0);

            }
        };
        mXrecycleView.setAdapter(mVideoAdapter);
    }


    @Override
    public void returnVideoListDatas(final List<VideoData> videoChannelTables) {
        if (CollectionUtils.isNullOrEmpty(videoChannelTables)) return;
        if (startPage == 0) {
            mVideoAdapter.replaceAll(videoChannelTables);
            mXrecycleView.refreshComplete();
        } else {
            mVideoAdapter.addAll(videoChannelTables);
            mXrecycleView.loadMoreComplete();
        }

        startPage += 1;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != mVideoAdapter && !CollectionUtils.isNullOrEmpty(mVideoAdapter.getAll())) {
            return;
        }
        mPresenter.requestVideoDatas(mTyep, 0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.logd("VideoFragment.onHiddenChanged." + hidden);
    }

    @Override
    public void showLoading(String title) {
        LogUtils.logd("VideoFragment.showLoading." + title);

    }

    @Override
    public void stopLoading() {
        LogUtils.logd("VideoFragment.stopLoading.");
        mXrecycleView.reset();
    }

    @Override
    public void showErrorTip(String msg) {
        LogUtils.logd("VideoFragment.showErrorTip." + msg);

        mXrecycleView.noMoreLoading();
        if (mVideoAdapter == null) {
            setVideoAdapter();
            mPresenter.requestVideoDatas(mTyep, startPage);
            return;
        }
        if (!CollectionUtils.isNullOrEmpty(mVideoAdapter.getAll())) {
            return;
        }

        mPresenter.requestVideoDatas(mTyep, startPage);
    }

    @Override
    public void scrolltoTop() {
        mXrecycleView.smoothScrollToPosition(0);
    }
}
