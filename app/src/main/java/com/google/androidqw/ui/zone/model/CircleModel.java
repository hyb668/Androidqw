package com.google.androidqw.ui.zone.model;


import com.google.androidqw.bean.Result;
import com.google.androidqw.ui.zone.DatasUtil;
import com.google.androidqw.ui.zone.contract.CircleContract;

import baserx.RxSchedulers;
import rx.Observable;
import rx.Subscriber;

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
}
