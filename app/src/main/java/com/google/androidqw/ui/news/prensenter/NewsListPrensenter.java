package com.google.androidqw.ui.news.prensenter;

import com.google.androidqw.bean.NewsSummary;
import com.google.androidqw.ui.news.contract.NewsListContract;

import java.util.List;

import app.AppConstant;
import baserx.RxSubscriber;
import rx.functions.Action1;

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
 * 描 述 ：
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class NewsListPrensenter extends NewsListContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
        mRxManage.on(AppConstant.NEWLIST_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object s) {
                mView.scrolltoTop();
            }
        });
    }

    @Override
    public void getNewsListDataRequest(String type, String id, int startPage) {
      mRxManage.add(mModel.getNewsListData(type,id,startPage).subscribe(new RxSubscriber<List<NewsSummary>>(mContext, false) {
          @Override
          protected void _onNext(List<NewsSummary> summaries) {
              mView.returnNewsListData(summaries);
          }

          @Override
          protected void _onError(String message) {
              mView.showErrorTip(message);
          }

          @Override
          public void onCompleted() {
              super.onCompleted();
              mView.stopLoading();
          }
      }));

    }
}
