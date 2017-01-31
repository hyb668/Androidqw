package com.google.androidqw.ui.news.model;

import com.google.androidqw.QwApplication;
import com.google.androidqw.R;
import com.google.androidqw.api.ApiConstants;
import com.google.androidqw.bean.NewsChannelTable;
import com.google.androidqw.db.NewsChannelTableManager;
import com.google.androidqw.ui.news.contract.NewsChannelContract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.AppConstant;
import rx.Observable;
import rx.Subscriber;
import utils.ACache;

/**
 * Created by liuyuzhe on 2017/1/30.
 */

public class NewsChannelModel implements NewsChannelContract.Model {
    @Override
    public Observable<List<NewsChannelTable>> loadMineNewsChannel() {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {

                List<NewsChannelTable> newsMineChannelTables = (List<NewsChannelTable>)
                        ACache.get(QwApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MINE);
                if (newsMineChannelTables == null) {
                    newsMineChannelTables = NewsChannelTableManager.loadNewsChannelsMine();
                }
                subscriber.onNext(newsMineChannelTables);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<NewsChannelTable>> loadMoreNewsChannel() {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                List<NewsChannelTable> channelTabMorelList = (List<NewsChannelTable>) ACache.get(QwApplication.getAppContext()).getAsObject(AppConstant.CHANNEL_MORE);
                if (null == channelTabMorelList) {
                    channelTabMorelList = new ArrayList<NewsChannelTable>();
                    List<String> channelNames = Arrays.asList(QwApplication.getAppContext().getResources().getStringArray(R.array.news_channel_name));
                    List<String> channleIds = Arrays.asList(QwApplication.getAppContext().getResources().getStringArray(R.array.news_channel_id));
                    for (int i = 0; i < channelNames.size(); i++) {
                        NewsChannelTable table = new NewsChannelTable(channelNames.get(i), channleIds.get(i), ApiConstants.getType(channleIds.get(i)), i <= 5, i, false);
                        channelTabMorelList.add(table);
                    }
                }
                subscriber.onNext(channelTabMorelList);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<String> swapDb(final List<NewsChannelTable> newsChannelTableList, int fromPosition, int toPosition) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ACache.get(QwApplication.getAppContext())
                        .put(AppConstant.CHANNEL_MINE, (Serializable) newsChannelTableList);
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<String> updateDb(final List<NewsChannelTable> mineChannelTabList, final List<NewsChannelTable> moreChanneTabList) {

          return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ACache.get(QwApplication.getAppContext())
                        .put(AppConstant.CHANNEL_MINE, (Serializable) mineChannelTabList);
                ACache.get(QwApplication.getAppContext())
                        .put(AppConstant.CHANNEL_MORE, (Serializable) moreChanneTabList);
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        });
    }
}
