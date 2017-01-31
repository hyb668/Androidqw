package com.google.androidqw.ui.news.contract;

import com.google.androidqw.bean.NewsChannelTable;

import java.util.List;

import base.BaseModel;
import base.BasePresenter;
import base.BaseView;
import rx.Observable;

/**
 * Created by liuyuzhe on 2017/1/30.
 */

public interface NewsChannelContract {

    // 这里应该调用请求服务器的方法
    interface Model extends BaseModel {
        Observable<List<NewsChannelTable>> loadMineNewsChannel();

        Observable<List<NewsChannelTable>> loadMoreNewsChannel();

        Observable<String> swapDb(List<NewsChannelTable> newsChannelTableList, int fromPosition, int toPosition);

        Observable<String> updateDb(List<NewsChannelTable> mineChannelTabList, List<NewsChannelTable> moreChanneTabList);
    }

    /**
     * 真实项目这2个频道的数据是服务器给的
     */
    interface View extends BaseView {
        void returnMineChannelDatas(List<NewsChannelTable> datas);

        void returnMoreChannelDatas(List<NewsChannelTable> datas);
    }

    static abstract class Prensent extends BasePresenter<View, Model> {
        public abstract void requestChannelDatas();

        /**
         * 拖拽
         *
         * @param allMineDatas
         * @param fromPosition
         * @param toPosition
         */
        public abstract void onItemSwap(List<NewsChannelTable> allMineDatas, int fromPosition, int toPosition);

        public abstract void onAddorRemove(List<NewsChannelTable> mineChannelTabList, List<NewsChannelTable> moreChannelTabList);
    }
}
