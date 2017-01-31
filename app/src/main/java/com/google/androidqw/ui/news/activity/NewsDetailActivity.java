package com.google.androidqw.ui.news.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.androidqw.R;
import com.google.androidqw.bean.NewsDetail;
import com.google.androidqw.ui.news.contract.NewsDetailContract;
import com.google.androidqw.ui.news.model.NewsDetailModel;
import com.google.androidqw.ui.news.prensenter.NewDetailsPrensenter;
import com.google.androidqw.utils.URLImageGetter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import app.AppConstant;
import base.BaseActivity;
import baserx.RxSchedulers;
import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import utils.CollectionUtils;
import utils.LogUtils;
import utils.TimeUtil;
import view.StatusBarCompat;

/**
 * news列表详情
 */
public class NewsDetailActivity extends BaseActivity<NewDetailsPrensenter, NewsDetailModel> implements NewsDetailContract.View {


    @Bind(R.id.img_new_detail_photo)
    ImageView mImgNewDetailPhoto;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.news_detail_from_tv)
    TextView mNewsDetailFromTv;
    @Bind(R.id.news_detail_body_tv)
    TextView mNewsDetailBodyTv;
    @Bind(R.id.mask_view)
    View mMaskView;
    @Bind(R.id.toolbarLayout)
    CollapsingToolbarLayout mToolbarLayout;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void start(Context context, ImageView imageView, String summaryPostid, String summaryImgsrc) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(AppConstant.PARAMS_NAME_NEWDETAIL_IMG_URL, summaryImgsrc);
        intent.putExtra(AppConstant.PARAMS_NAME_NEWDETAIL_POST_ID, summaryPostid);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions opitons = ActivityOptions.makeSceneTransitionAnimation((Activity) context, imageView, AppConstant.TRANSITION_ANIMATION_NEWS_PHOTOS);
            context.startActivity(intent, opitons.toBundle());
        } else {
            //从一小范围扩大到全屏
            ActivityOptions options = ActivityOptions.makeScaleUpAnimation(imageView, imageView.getWidth() / 2, imageView.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) context, intent, options.toBundle());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
//        intent.putExtra(AppConstant.PARAMS_NAME_NEWDETAIL_IMG_URL, summaryImgsrc);
//        intent.putExtra(AppConstant.PARAMS_NAME_NEWDETAIL_POST_ID, summaryImgsrc);
        StatusBarCompat.translucentStatusBar(this);
        String postId = getIntent().getStringExtra(AppConstant.PARAMS_NAME_NEWDETAIL_POST_ID);
        mPresenter.getNewDetailsRequest(postId);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolBarFinish(NewsDetailActivity.this);
            }
        });

    }



    @Override
    public void returnNewsDetailsDatas(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgs = newsDetail.getImg();
        String body = newsDetail.getBody();

        setToolBarLayout(newsDetail.getTitle());
        setBody(body, newsDetail);
        setImg(imgs);


        String newsTime = TimeUtil.formatDate(newsDetail.getPtime());
        String newsSource = newsDetail.getSource();
        LogUtils.logd("NewsDetailActivity.returnNewsDetailsDatas."+newsTime+"====="+newsSource);
        String titleAndTime = getString(com.example.common.R.string.news_from, newsSource, newsTime);
        mNewsDetailFromTv.setText(titleAndTime);


    }

    private void setImg(List<NewsDetail.ImgBean> imgs) {
        String imgUrl="";
        if (!CollectionUtils.isNullOrEmpty(imgs)) {
             imgUrl = imgs.get(0).getSrc();
        } else {

             imgUrl = getIntent().getStringExtra(AppConstant.PARAMS_NAME_NEWDETAIL_IMG_URL);
        }
        Glide.with(mContext).load(imgUrl)
                .fitCenter()
                .crossFade().into(mImgNewDetailPhoto);
    }

    private void setBody(final String body, final NewsDetail newsDetail) {

        Observable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.<Long>io_main())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        setBodyDatas(body, newsDetail);
                    }
                });

    }

    private void setBodyDatas(String body, NewsDetail newsDetail) {
        int imgTotal = newsDetail.getImg().size();
        if (imgTotal > 2 && !TextUtils.isEmpty(body)) {
            Html.ImageGetter imageGetter = new URLImageGetter(mNewsDetailBodyTv, body, imgTotal);
            mNewsDetailBodyTv.setText(Html.fromHtml(body, imageGetter, null));
        } else {
            mNewsDetailBodyTv.setText(Html.fromHtml(body));
        }
    }

    private void setToolBarLayout(String title) {
        mToolbarLayout.setTitle(title);
        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(mContext, R.color.primary_text_white));
        mToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(mContext, R.color.white));

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
