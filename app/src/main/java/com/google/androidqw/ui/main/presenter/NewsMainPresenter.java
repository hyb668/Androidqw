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
    //新闻频道进行从新选择了，我们要更新新闻首页
    public void onStart() {
        super.onStart();
        mRxManage.on(AppConstant.NEWS_CHANNEL_CHANGED, new Action1<List<NewsChannelTable>>() {
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
