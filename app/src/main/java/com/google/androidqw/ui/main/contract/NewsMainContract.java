package com.google.androidqw.ui.main.contract;

import com.google.androidqw.bean.NewsChannelTable;

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
 * 创建日期 ：  on 2016/11/17.
 * <p/>
 * 描 述 ：
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public interface NewsMainContract {

    //加载网络数据 : 被下面的Presenter的子类调用
    interface Model extends BaseModel{
        Observable<List<NewsChannelTable>> loadMineNewsChannels();
    }

    //返回结果
    interface  View extends BaseView {
        void returnMineNewsChannels(List<NewsChannelTable> newsChannelTables);
    }

    abstract  static  class Presenter extends BasePresenter<View,Model>{
        public abstract void loadMineChannelsRequest();

    }
}
