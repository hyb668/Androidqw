package com.google.androidqw.ui.main.fragment;

import com.google.androidqw.R;
import com.google.androidqw.bean.NewsChannelTable;
import com.google.androidqw.ui.main.contract.NewsMainContract;
import com.google.androidqw.ui.main.model.NewsMainModel;
import com.google.androidqw.ui.main.presenter.NewsMainPresenter;

import java.util.List;

import base.BaseFragment;

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
public class NewsMainFrament extends BaseFragment<NewsMainPresenter,NewsMainModel> implements NewsMainContract.View{
    @Override
    protected int getLayoutResource() {
            return R.layout.frament_news;
    }
    //必须 要继承 <T extends BasePresenter, E extends BaseModel>
    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        mPresenter.loadMineChannelsRequest();
    }

    @Override
    public void returnMineNewsChannels(List<NewsChannelTable> newsChannelTables) {

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
}
