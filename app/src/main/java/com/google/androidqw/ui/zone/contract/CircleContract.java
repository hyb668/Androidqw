package com.google.androidqw.ui.zone.contract;


import com.google.androidqw.bean.Result;
import com.google.androidqw.ui.zone.bean.CircleItem;
import com.google.androidqw.ui.zone.bean.CommentConfig;
import com.google.androidqw.ui.zone.bean.CommentItem;

import java.util.List;

import base.BaseModel;
import base.BasePresenter;
import base.BaseView;
import base.PageBean;

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
 * 描 述 ： 契约类
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public interface CircleContract {
    //请求数据
    interface Model extends BaseModel {
        rx.Observable<Result> getListDatas(String type, String userId, final int page, int rows);

        rx.Observable<CircleItem> addComment(String publishUserId, CommentConfig config, CircleItem circleItem);
    }

    //拿到数据设置给界面
    interface View extends BaseView {
        //返回数据
        void returnListDatas(List<CircleItem> circleItems, PageBean pageBean);

        void refreshFinish();

        //是否显示输入框
        void updateInputFrameVisibiliy(int visibiliy, CommentConfig config);

        void update2AddComment(int circlePos, CommentItem commentItem);

        void excuteSmoothScrollToPosition(int position);
    }

    //实现接口返回数据
    abstract class presenter extends BasePresenter<View, Model> {
        public abstract void getListDatasRequest(String type, String userId, int page, int rows);

        public abstract void addCommentContent(String content, CommentConfig config);
    }
}
