package com.google.androidqw.ui.news.prensenter;

import com.google.androidqw.bean.NewsDetail;
import com.google.androidqw.ui.news.contract.NewsDetailContract;

import rx.Subscriber;

/**
 * Created by lyz on 2017/1/26.
 */
public class NewDetailsPrensenter extends NewsDetailContract.Presenter {


    @Override
    public  void getNewDetailsRequest(String postId) {
        mRxManage.add(mModel.getNewDetailsDatas(postId).subscribe(new Subscriber<NewsDetail>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NewsDetail newsDetail) {
                    mView.returnNewsDetailsDatas(newsDetail);
            }
        }));
    }
}
