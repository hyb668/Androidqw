package com.google.androidqw.ui.news;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.google.androidqw.R;
import com.google.androidqw.bean.NewsSummary;
import com.google.androidqw.ui.news.adapter.NewsListAdapter;
import com.google.androidqw.ui.news.contract.NewsListContract;
import com.google.androidqw.ui.news.model.NewsListModel;
import com.google.androidqw.ui.news.prensenter.NewsListPrensenter;

import java.util.ArrayList;
import java.util.List;

import app.AppConstant;
import base.BaseFragment;
import butterknife.Bind;
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
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class NewsFragment extends BaseFragment<NewsListPrensenter, NewsListModel> implements NewsListContract.View, OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.irecycleView)
    IRecyclerView mIrecycleView;
    private String mNesId;
    private String mNewsType;
    private List<NewsSummary> datas = new ArrayList<>();
    private NewsListAdapter newsListAdapter;
    private int mStartPage;

    @Override
    protected int getLayoutResource() {
        // TODO: 2016/11/18  找不到listview布局
        return R.layout.framents_inner_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    /**
     */
    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        if (null != arguments) {
            mNesId = arguments.getString(AppConstant.PARAMS_NAME_NEWS_ID);
            mNewsType = arguments.getString(AppConstant.PARAMS_NAME_NEWS_TYPE);
        }
        mIrecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsListAdapter = new NewsListAdapter(getContext(), datas);
        mIrecycleView.setAdapter(newsListAdapter);
        mIrecycleView.setOnRefreshListener(this);
        mIrecycleView.setOnLoadMoreListener(this);
        //数据为空才去请求
        if (newsListAdapter.getItemCount() <= 0) {
            mStartPage = 0;
            mPresenter.getNewsListDataRequest(mNewsType, mNesId, mStartPage);
        }
    }

    @Override
    public void returnNewsListData(List<NewsSummary> newsSummaries) {
        LogUtils.logd("datas=" + newsSummaries);
    }

    @Override
    public void scrolltoTop() {

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
    @Override
    public void onRefresh() {
        //上拉刷新
        newsListAdapter.getPageBean().setRefresh(true);
        mIrecycleView.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getNewsListDataRequest(mNewsType, mNesId, mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {

    }
}
