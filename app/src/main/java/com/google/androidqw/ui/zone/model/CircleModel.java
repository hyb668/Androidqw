package com.google.androidqw.ui.zone.model;


import com.alibaba.fastjson.JSON;
import com.google.androidqw.bean.Result;
import com.google.androidqw.ui.zone.DatasUtil;
import com.google.androidqw.ui.zone.bean.CircleItem;
import com.google.androidqw.ui.zone.bean.CommentConfig;
import com.google.androidqw.ui.zone.bean.FavortItem;
import com.google.androidqw.ui.zone.contract.CircleContract;

import java.util.List;

import app.AppCache;
import baserx.RxSchedulers;
import rx.Observable;
import rx.Subscriber;
import utils.JsonUtils;

/**
 * ============================================================
 * <p/>
 * 版 权 ： 刘宇哲 版权所有 (c) 2016
 * <p/>
 * 作 者 : 刘宇哲
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ：  on 2016/12/19.
 * <p/>
 * 描 述 ：
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class CircleModel implements CircleContract.Model {
    @Override
    public Observable<Result> getListDatas(String type, String userId, int page, int rows) {
        return Observable.create(new Observable.OnSubscribe<Result>() {
            @Override
            public void call(Subscriber<? super Result> subscriber) {
                final Result datas = DatasUtil.getZoneListDatas();
                subscriber.onNext(datas);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<Result>io_main());
    }

    @Override
    public Observable<CircleItem> addComment(String publishUserId, CommentConfig config, final CircleItem circleItem) {
        return Observable.create(new Observable.OnSubscribe<CircleItem>() {
            @Override
            public void call(Subscriber subscriber) {
                // TODO: 2017/1/11   我需要知道,他评论的是哪个条动态,我给对应的动态添加一个评论 , 这里我们还要区分是普通评论还是对谁回复
                // STOPSHIP: 2017/1/11  这里其实应该是网络请求,给prensent 他成功还是失败就可以
                //config.getCirclePosition()
                final Result datas = DatasUtil.getZoneListDatas();
                List<CircleItem> circleItems = JSON.parseArray(JsonUtils.getValue(datas.getMsg(), "list"), CircleItem.class);
                circleItems.add(circleItem);
                // ACache.get(QwApplication.getAppContext()).put("addComment", new Gson().toJson(circleItems));
                subscriber.onNext(circleItem);
                subscriber.onCompleted();
            }
        }).compose(RxSchedulers.<CircleItem>io_main());
    }

    @Override
    public Observable<FavortItem> updateFavorite(final boolean isAddFavorite, final String userUuid, final String toUserUuid) {
        return Observable.create(new Observable.OnSubscribe<FavortItem>() {
            @Override
            public void call(Subscriber<? super FavortItem> subscriber) {

                FavortItem favortItem = null;
                if (isAddFavorite) {
                    favortItem=new FavortItem(userUuid, AppCache.getInstance().getUserId(),"lyz");
                }

                subscriber.onNext(favortItem);
                subscriber.onCompleted();
            }
        });
    }
}
