package com.google.androidqw.ui.zone.prensenter;

import com.alibaba.fastjson.JSON;
import com.google.androidqw.bean.Result;
import com.google.androidqw.ui.zone.DatasUtil;
import com.google.androidqw.ui.zone.bean.CircleItem;
import com.google.androidqw.ui.zone.contract.CircleContract;

import java.util.List;
import java.util.Random;

import base.PageBean;
import rx.Subscriber;
import utils.JsonUtils;

/**
 * ============================================================
 * <p>
 * 版 权 ： 刘宇哲 版权所有 (c) 2016
 * <p>
 * 作 者 : 刘宇哲
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ：  on 2016/12/19.
 * <p>
 * 描 述 ：
 * <p>
 * <p>
 * 修订历史 ：
 * <p>
 * ============================================================
 **/
public class CirclePrensent extends CircleContract.presenter {


    @Override
    public void getListDatasRequest(String type, String userId, int page, int rows) {
        mRxManage.add(mModel.getListDatas(type,userId,page,rows).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                List<CircleItem> circleItems = JSON.parseArray(JsonUtils.getValue(result.getMsg(), "list"), CircleItem.class);
                for (int i = 0; i < circleItems.size(); i++) {
                    circleItems.get(i).setPictures(DatasUtil.getRandomPhotoUrlString(new Random().nextInt(9)));
                }
                PageBean pageBean = JSON.parseObject(JsonUtils.getValue(result.getMsg(), "page"), PageBean.class);
                mView.returnListDatas(circleItems,pageBean);
            }
        }));

    }

    public void getNotReadNewsCount() {

    }
}
