package com.google.androidqw.ui.main.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.androidqw.R;
import com.google.androidqw.bean.NewsChannelTable;
import com.google.androidqw.ui.main.contract.NewsMainContract;
import com.google.androidqw.ui.main.model.NewsMainModel;
import com.google.androidqw.ui.main.presenter.NewsMainPresenter;
import com.google.androidqw.ui.news.NewsFragment;
import com.google.androidqw.ui.news.activity.NewsChannelActivity;
import com.google.androidqw.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import app.AppConstant;
import base.BaseFragment;
import base.BaseFramentAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ============================================================
 * <p/>
 * 版 权 ： 刘宇哲 版权所有 (c) 2016
 * <p/>
 * 作 者 : 刘宇哲
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ：  on 2016/11/17.
 * <p/>
 * 描 述 ：
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class NewsMainFrament extends BaseFragment<NewsMainPresenter, NewsMainModel> implements NewsMainContract.View {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tabs)
    TabLayout mTabs;
    @Bind(R.id.add_channel_iv)
    ImageView mAddChannelIv;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.floatbtn)
    FloatingActionButton mFloatbtn;

    @Override
    protected int getLayoutResource() {
        return R.layout.frament_news;
    }

    //必须 要继承 <T extends BasePresenter, E extends BaseModel>
    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        mPresenter.loadMineChannelsRequest();
        mFloatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRxManager.post(AppConstant.NEWLIST_TO_TOP, "");
            }
        });

        mAddChannelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsChannelActivity.start(getContext());
            }
        });
    }

    @Override
    // NewsMainContract.View 实现这个接口,接口会在数据拿到的时候,反给我们; 给他个接口对象,在合适的时候拿到参数
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelTables) {
        final List<String> channelName = new ArrayList<>();
        final List<Fragment> mNewsFramentList = new ArrayList<>();
        for (NewsChannelTable table : newsChannelTables
                ) {
            channelName.add(table.getNewsChannelName());
            mNewsFramentList.add(createListFrament(table));
        }
        BaseFramentAdapter framentAdapter = new BaseFramentAdapter(getChildFragmentManager(), mNewsFramentList, channelName);
        mViewPager.setAdapter(framentAdapter);
        mTabs.setupWithViewPager(mViewPager);
        MyUtils.dynamicSetTabLayoutMode(mTabs);
    }

    //初始化新闻界面fragment
    private NewsFragment createListFrament(NewsChannelTable table) {
        Bundle bundle = new Bundle();
        NewsFragment fragment = new NewsFragment();
        bundle.putString(AppConstant.PARAMS_NAME_NEWS_ID, table.getNewsChannelId());
        bundle.putString(AppConstant.PARAMS_NAME_NEWS_TYPE, table.getNewsChannelType());
        bundle.putInt(AppConstant.PARAMS_VALUE_CHANNEL_POSITION, table.getNewsChannelIndex());
        fragment.setArguments(bundle);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
