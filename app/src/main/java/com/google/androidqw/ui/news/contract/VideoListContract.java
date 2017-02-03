package com.google.androidqw.ui.news.contract;

import com.google.androidqw.bean.VideoData;

import java.util.List;

import base.BaseModel;
import base.BasePresenter;
import base.BaseView;
import rx.Observable;

/**
 * Created by liuyuzhe on 2017/2/1.
 */

public interface VideoListContract {


    interface Model extends BaseModel {
        Observable<List<VideoData>> loadVideoListDatas(String type , int startPage);
    }

    interface View extends BaseView {
        void returnVideoListDatas(List<VideoData> videoChannelTables);
        void scrolltoTop();
    }

    abstract class Persenter extends BasePresenter<View, Model> {
        public abstract void requestVideoDatas(String type, int startPage);
    }


}
