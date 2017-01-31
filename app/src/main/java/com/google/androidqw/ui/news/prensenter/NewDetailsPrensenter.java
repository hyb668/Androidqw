package com.google.androidqw.ui.news.prensenter;

import com.google.androidqw.bean.NewsDetail;
import com.google.androidqw.ui.news.contract.NewsDetailContract;

import baserx.RxSubscriber;

/**
 * Created by lyz on 2017/1/26.
 */
public class NewDetailsPrensenter extends NewsDetailContract.Presenter {


    @Override
    public void getNewDetailsRequest(String postId) {
        mRxManage.add(mModel.getNewDetailsDatas(postId).subscribe(new RxSubscriber<NewsDetail>(mContext) {
            @Override
            protected void _onNext(NewsDetail detail) {
                mView.returnNewsDetailsDatas(detail);
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
