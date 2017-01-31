package com.google.androidqw.ui.main.model;

import com.google.androidqw.bean.NewsChannelTable;
import com.google.androidqw.db.NewsChannelTableManager;
import com.google.androidqw.ui.main.contract.NewsMainContract;

import java.util.ArrayList;
import java.util.List;

import app.AppConstant;
import app.BaseApplication;
import rx.Observable;
import rx.Subscriber;
import utils.ACache;
import utils.CollectionUtils;

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
public class NewsMainModel implements NewsMainContract.Model {


    @Override
    public Observable<List<NewsChannelTable>> loadMineNewsChannels() {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                ArrayList<NewsChannelTable> newsChannelTables = (ArrayList<NewsChannelTable>)
                        ACache.get(BaseApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MINE);
                if (CollectionUtils.isNullOrEmpty(newsChannelTables)) {
                    newsChannelTables = (ArrayList<NewsChannelTable>) NewsChannelTableManager.loadNewsChannelsStatic();
                    ACache.get(BaseApplication.getAppContext()).put(AppConstant.CHANNEL_MINE,newsChannelTables);
                }
                subscriber.onNext(newsChannelTables);
                subscriber.onCompleted();
            }
        });
    }
}
