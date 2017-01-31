package com.google.androidqw.ui.news.prensenter;

import com.google.androidqw.bean.NewsChannelTable;
import com.google.androidqw.ui.news.contract.NewsChannelContract;

import java.util.List;

import app.AppConstant;
import baserx.RxSubscriber;

/**
 * Created by liuyuzhe on 2017/1/30.
 */

public class NewsChannelPrensent extends NewsChannelContract.Prensent {
    @Override
    public void requestChannelDatas() {
        mRxManage.add(mModel.loadMineNewsChannel().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTableList) {
                mView.returnMineChannelDatas(newsChannelTableList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));

        mRxManage.add(mModel.loadMoreNewsChannel().subscribe(new RxSubscriber<List<NewsChannelTable>>(mContext) {
            @Override
            protected void _onNext(List<NewsChannelTable> newsChannelTableList) {
                mView.returnMoreChannelDatas(newsChannelTableList);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void onItemSwap(final List<NewsChannelTable> allMineDatas, int fromPosition, int toPosition) {
        mRxManage.add(mModel.swapDb(allMineDatas, fromPosition, toPosition).subscribe(new RxSubscriber<String>(mContext) {
            @Override
            protected void _onNext(String s) {
                //告诉首页更新数据
                noticeHomeRefresh(allMineDatas);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void onAddorRemove(final List<NewsChannelTable> mineChannelTabList, List<NewsChannelTable> moreChannelTabList) {
        mRxManage.add(mModel.updateDb(mineChannelTabList, moreChannelTabList).subscribe(new RxSubscriber<String>(mContext) {
            @Override
            protected void _onNext(String s) {
                noticeHomeRefresh(mineChannelTabList);

            }

            @Override
            protected void _onError(String message) {

            }
        }));

    }

    private void noticeHomeRefresh(List<NewsChannelTable> mineChannelTabList) {
        //告诉首页更新数据 ,吧频道的文本对象给首页
        mRxManage.post(AppConstant.NEWS_CHANNEL_CHANGED, mineChannelTabList);
    }
}
