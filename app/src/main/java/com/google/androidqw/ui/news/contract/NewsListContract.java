package com.google.androidqw.ui.news.contract;

import com.google.androidqw.bean.NewsSummary;

import java.util.List;

import base.BaseModel;
import base.BasePresenter;
import base.BaseView;
import rx.Observable;

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
 * 描 述 ： 新闻详情列表contract
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public interface NewsListContract {

    interface Model extends BaseModel {
        Observable<List<NewsSummary>> getNewsListData(String type, final String id, int startPage);
    }

    //当前newsListFrament 是子类实现类
    interface View extends BaseView {
        //返回新闻列表
        void returnNewsListData(List<NewsSummary> newsSummaries);
        //返回顶部
        void scrolltoTop();
    }

    abstract static class Presenter extends BasePresenter<View, Model> {
            //发起请求
        public abstract void getNewsListDataRequest(String type,String id ,int startPage);
    }
}
