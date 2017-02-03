package com.google.androidqw.ui.news;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.androidqw.R;
import com.google.androidqw.bean.NewsSummary;
import com.google.androidqw.ui.news.adapter.NewsListAdapter;
import com.google.androidqw.ui.news.contract.NewsListContract;
import com.google.androidqw.ui.news.model.NewsListModel;
import com.google.androidqw.ui.news.prensenter.NewsListPrensenter;
import com.google.androidqw.utils.SpaceItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.AppConstant;
import base.BaseFragment;
import butterknife.Bind;
import utils.CollectionUtils;
import utils.DisplayUtil;
import utils.LogUtils;

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
 * 修订历史 : 2016/12/27 修复卡片间距 , 一个图片显示不全的bug ;  剩余bug 上拉加载更多,无数据一直显示进度条;
 * <p/>
 * <p/>
 * ============================================================
 **/

public class NewsFragment extends BaseFragment<NewsListPrensenter, NewsListModel> implements NewsListContract.View {
    @Bind(R.id.xrecycleView)
    XRecyclerView mXrecycleView;
    private String mNesId;
    private String mNewsType;
    private List<NewsSummary> datas = new ArrayList<>();
    private NewsListAdapter newsListAdapter;
    private int mStartPage;

    @Override
    protected int getLayoutResource() {
        return R.layout.framents_inner_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }


    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        if (null != arguments) {
            mNesId = arguments.getString(AppConstant.PARAMS_NAME_NEWS_ID);
            mNewsType = arguments.getString(AppConstant.PARAMS_NAME_NEWS_TYPE);
        }
        mXrecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsListAdapter = new NewsListAdapter(getContext(), datas);
        mXrecycleView.setAdapter(newsListAdapter);
        mXrecycleView.setPullRefreshEnabled(true);
        mXrecycleView.setLoadingMoreEnabled(true);
        mXrecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mStartPage = 0;
                //发起请求 // TODO: 2016/12/28  在网络请求失败后403 我们要让进度条消失显示没有更多数据了;
                //mXrecycleView.setPullRefreshEnabled(true);
                mPresenter.getNewsListDataRequest(mNewsType, mNesId, mStartPage);
            }

            @Override
            public void onLoadMore() {
                //发起请求
                //mXrecycleView.setLoadingMoreEnabled(true);
                mPresenter.getNewsListDataRequest(mNewsType, mNesId, mStartPage);
            }
        });
        if (newsListAdapter.getItemCount() <= 0) {
            //在这了调用防止切换界面的时候多次add,导致间距过大; 在这里只会调用一次
            mXrecycleView.addItemDecoration(new SpaceItemDecoration(DisplayUtil.px2dip(30)));
            mStartPage = 0;
            mPresenter.getNewsListDataRequest(mNewsType, mNesId, mStartPage);
        }

        mXrecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtils.logd("NewsFragment.onScrolled." + dy);
                boolean isDownScroll = true;
                if (dy < -25) {
                    isDownScroll = false;
                    mRxManager.post(AppConstant.MENU_SHOW_HIDE, isDownScroll);
                } else if (dy > 25) {
                    isDownScroll = true;
                    mRxManager.post(AppConstant.MENU_SHOW_HIDE, isDownScroll);
                }
            }
        });
    }


    @Override
    public void returnNewsListData(List<NewsSummary> newsSummaries) {
        LogUtils.logd("datas=" + newsSummaries);
        if (!CollectionUtils.isNullOrEmpty(newsSummaries)) {
            if (mStartPage == 0) {
                newsListAdapter.replaceAll(newsSummaries);
                mXrecycleView.refreshComplete();
            } else {
                newsListAdapter.addAll(newsSummaries);
                mXrecycleView.loadMoreComplete();
            }

            mStartPage += 20;
        }
    }

    @Override
    public void scrolltoTop() {
        mXrecycleView.smoothScrollToPosition(0);
    }

    @Override
    public void showLoading(String title) {
        //加载进度条,

    }

    @Override
    public void stopLoading() {
        mXrecycleView.reset();
        //关闭进度条
    }

    @Override
    public void showErrorTip(String msg) {
        //加载网络错误
        mXrecycleView.noMoreLoading();

    }
}
