package com.google.androidqw.ui.news.contract;

import com.google.androidqw.bean.NewsDetail;

import base.BaseModel;
import base.BasePresenter;
import base.BaseView;

/**
 * 新闻详情contract
 * Created by lyz on 2017/1/26.
 */
public interface NewsDetailContract {

    interface Model extends BaseModel {
        rx.Observable<NewsDetail> getNewDetailsDatas(String postId);
    }


    interface View extends BaseView {
        void returnNewsDetailsDatas(NewsDetail newsDetail);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public  abstract void getNewDetailsRequest(String postId);
    }

}
