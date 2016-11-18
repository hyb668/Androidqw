package com.google.androidqw.ui.news.model;

import com.google.androidqw.api.Api;
import com.google.androidqw.api.ApiConstants;
import com.google.androidqw.api.HostType;
import com.google.androidqw.bean.NewsSummary;
import com.google.androidqw.ui.news.contract.NewsListContract;

import java.util.List;
import java.util.Map;

import baserx.RxSchedulers;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import utils.TimeUtil;

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
 * 描 述 ：* des:新闻列表model
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class NewsListModel implements NewsListContract.Model {
    @Override
    public Observable<List<NewsSummary>> getNewsListData(String type, final String id, int startPage) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO)
                .getNewsList(Api.getCacheControl(), type, id, startPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> map) {
                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
                            // 房产实际上针对地区的它的id与返回key不同
                            return Observable.from(map.get("北京"));
                        }
                        return Observable.from(map.get(id));
                    }
                })
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary summary) {
                        //对象转换成对象
                        summary.setPtime(TimeUtil.formatDate(summary.getPtime()));
                        return summary;
                    }
                })
                .distinct()
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary summary, NewsSummary summary2) {
                        return summary.getPtime().compareTo(summary2.getPtime());
                    }
                }).compose(RxSchedulers.<List<NewsSummary>>io_main());
    }
}
