package com.google.androidqw.ui.main.presenter;

import com.google.androidqw.bean.NewsChannelTable;
import com.google.androidqw.ui.main.contract.NewsMainContract;

import java.util.List;

import app.AppConstant;
import baserx.RxSubscriber;
import rx.functions.Action1;

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
public class NewsMainPresenter extends NewsMainContract.Presenter {
    @Override
    //继承 BasePresenter 的子类  NewsMainContract.Presenter  ,来重写 祖父类的方法,和使用里面的成员变量来调用小父类的方法
    public void onStart() {
        super.onStart();
        mRxManage.on(AppConstant.MENU_CURRENT_TAB_POSITON, new Action1<List<NewsChannelTable>>() {
            @Override
            public void call(List<NewsChannelTable> newsChannelTables) {
                if (newsChannelTables != null) {
                    mView.returnMineNewsChannels(newsChannelTables);
                }
            }
        });
    }

    @Override
    public void loadMineChannelsRequest() {
        mRxManage.add(mModel.loadMineNewsChannels().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext,false) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTables) {
                //要数据
                mView.returnMineNewsChannels(newsChannelTables);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
