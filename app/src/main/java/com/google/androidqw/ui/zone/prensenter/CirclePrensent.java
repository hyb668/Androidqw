package com.google.androidqw.ui.zone.prensenter;

import com.alibaba.fastjson.JSON;
import com.google.androidqw.bean.Result;
import com.google.androidqw.ui.zone.DatasUtil;
import com.google.androidqw.ui.zone.bean.CircleItem;
import com.google.androidqw.ui.zone.bean.CommentConfig;
import com.google.androidqw.ui.zone.bean.CommentItem;
import com.google.androidqw.ui.zone.contract.CircleContract;

import java.util.List;
import java.util.Random;

import app.AppCache;
import base.PageBean;
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
public class CirclePrensent extends CircleContract.presenter {


    @Override
    public void getListDatasRequest(String type, String userId, int page, int rows) {
        mRxManage.add(mModel.getListDatas(type, userId, page, rows).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                mView.refreshFinish();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                List<CircleItem> circleItems = JSON.parseArray(JsonUtils.getValue(result.getMsg(), "list"), CircleItem.class);
                for (int i = 0; i < circleItems.size(); i++) {
                    circleItems.get(i).setPictures(DatasUtil.getRandomPhotoUrlString(new Random().nextInt(9)));
                    circleItems.get(i).setIcon(DatasUtil.getRandomPhotoUrl());
                    circleItems.get(i).setContent(circleItems.get(new  Random().nextInt(circleItems.size()) ).getContent()+"" +
                            "谁离开多久发来看打瞌睡的福利金卡塑料袋口附近阿拉山口的积分来看撒娇的疯了快接啊塑料袋口附近爱上了宽带缴费拉克丝京东方拉时间段福利卡几点睡的了看风景阿斯利康的房间卡拉斯剪短发了卡萨大家疯狂老师点击谁离开多久发来看打瞌睡的福利金卡塑料袋口附近阿拉山口的积分来看撒娇的疯了快接啊塑料袋口附近爱上了宽带缴费拉克丝京东方拉时间段福利卡几点睡的了看风景阿斯利康的房间卡拉斯剪短发了卡萨大家疯狂老师点击谁离开多久发来看打瞌睡的福利金卡塑料袋口附近阿拉山口的积分来看撒娇的疯了快接啊塑料袋口附近爱上了宽带缴费拉克丝京东方拉时间段福利卡几点睡的了看风景阿斯利康的房间卡拉斯剪短发了卡萨大家疯狂老师点击谁离开多久发来看打瞌睡的福利金卡塑料袋口附近阿拉山口的积分来看撒娇的疯了快接啊塑料袋口附近爱上了宽带缴费拉克丝京东方拉时间段福利卡几");
                }
                PageBean pageBean = JSON.parseObject(JsonUtils.getValue(result.getMsg(), "page"), PageBean.class);
                mView.returnListDatas(circleItems, pageBean);
            }
        }));

    }

    @Override
    public void addCommentContent(final String content, final CommentConfig config) {
        // TODO: 2017/1/10  这里应该调用服务器接口,这里我们本地存下;
        mRxManage.add(mModel.addComment(config.getPublishUserId(),config,new CircleItem()).subscribe(new Subscriber<CircleItem>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(CircleItem item) {
                mView.update2AddComment(config.getCirclePosition(),new CommentItem(config.getName(),config.getId(),content,config.getPublishId()
                , AppCache.getInstance().getUserId(),"刘"));
            }
        }));
    }

    public void getNotReadNewsCount() {

    }
    //评论输入框
    public void updateInputFrameVisibiliy(int Visibiliy, CommentConfig commentConfig) {
        mView.updateInputFrameVisibiliy(Visibiliy, commentConfig);
    }

    public void excuteSmoothScrollToPosition(int position) {
        mView.excuteSmoothScrollToPosition(position);
    }
}
