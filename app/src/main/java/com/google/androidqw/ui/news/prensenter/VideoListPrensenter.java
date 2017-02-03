package com.google.androidqw.ui.news.prensenter;

import com.google.androidqw.bean.VideoData;
import com.google.androidqw.ui.news.contract.VideoListContract;

import java.util.List;

import app.AppConstant;
import baserx.RxSubscriber;
import rx.functions.Action1;
import utils.LogUtils;

/**
 * Created by liuyuzhe on 2017/2/1.
 */

public class VideoListPrensenter extends VideoListContract.Persenter {
    @Override
    public void onStart() {
        super.onStart();
        mRxManage.on(AppConstant.NEWLIST_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object o) {
                mView.scrolltoTop();
            }
        });
    }

    @Override
    public void requestVideoDatas(String type, int startPage) {
        mRxManage.add(mModel.loadVideoListDatas(type,startPage).subscribe(new RxSubscriber<List<VideoData>>(mContext) {
            @Override
            public void onCompleted() {
                super.onCompleted();
                mView.stopLoading();
            }

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("onStart");
            }

            @Override
            protected void _onNext(List<VideoData> datas) {
                mView.returnVideoListDatas(datas);
                mView.showLoading("onNext");
            }

            @Override
            protected void _onError(String message) {
                LogUtils.logd("VideoListPrensenter._onError."+message);
                mView.showErrorTip(message);
            }
        }));

    }
}
