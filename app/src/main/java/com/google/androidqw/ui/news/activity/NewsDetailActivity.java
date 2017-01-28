package com.google.androidqw.ui.news.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.androidqw.R;
import com.google.androidqw.bean.NewsDetail;
import com.google.androidqw.ui.news.contract.NewsDetailContract;
import com.google.androidqw.ui.news.model.NewsDetailModel;
import com.google.androidqw.ui.news.prensenter.NewDetailsPrensenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import app.AppConstant;
import base.BaseActivity;
import baserx.RxSchedulers;
import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import utils.TimeUtil;
import utils.ToastUitl;

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
        intent.putExtra(AppConstant.PARAMS_NAME_NEWDETAIL_POST_ID, summaryImgsrc);
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
        String postId = getIntent().getStringExtra(AppConstant.PARAMS_NAME_NEWDETAIL_POST_ID);
        String imgUrl = getIntent().getStringExtra(AppConstant.PARAMS_NAME_NEWDETAIL_IMG_URL);
        mPresenter.getNewDetailsRequest(postId);

        String s = "http://dingyue.nosdn.127.net/K6c8upwEzUUep5IXEB9dqNUTvBTYtsMh53SEyAe9R2Bx91471518604553compressflag.jpg";
        Glide.with(mContext).load(imgUrl)
                .fitCenter()
                .crossFade().into(mImgNewDetailPhoto);

        Observable.timer(10000, TimeUnit.MILLISECONDS)
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
                        ToastUitl.show("延迟这么多秒哦=" + aLong);
                    }
                });


        String title = "长沙 强拆致农妇死亡案 8人获刑 其中4人被判1年";
        mToolbarLayout.setTitle(title);
        mToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(mContext, R.color.white));
        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(mContext, R.color.primary_text_white));
        mToolbarLayout.setContentScrim(new ColorDrawable(ContextCompat.getColor(mContext, R.color.alpha_30_black)));

        String newsBody = "<p><!--VIDEO#0--></p><p>　　新华社长沙1月26日电26日，湖南长沙市岳麓区人民法院对观沙岭街道非法拆除房屋致人死亡案件作出一审判决，对观沙岭街道原工委书记、办事处主任等8人分别以滥用职权罪、玩忽职守罪和重大责任事故罪判处有期徒刑。</p><p>　　其中，原观沙岭街道城管爱卫办副主任兼拆违办主任朱建群被以玩忽职守罪判处有期徒刑一年；原观沙岭街道工委书记厉军、街道办事处主任陈建新、原茶子山村支部书记万智、茶子山村主任段庆，被以滥用职权罪分别判处有期徒刑一年、宣告缓刑一年，有期徒刑一年、宣告缓刑一年，有期徒刑十个月、宣告缓刑一年，有期徒刑十个月、宣告缓刑一年；原观沙岭街道工委副书记任亿文、街道办事处副主任潘多，被以玩忽职守罪分别判处有期徒刑一年、宣告缓刑一年，有期徒刑十个月、宣告缓刑一年；现场工作人员樊梓祥被以重大责任事故罪判处有期徒刑十个月、宣告缓刑一年。</p><p>　　经法院审理查明，观沙岭街道办事处为在所辖茶子山村集体建设用地上建设村民安置房，在多次协商未果后，于2016年6月16日，非法对该集体土地上的杨君、杨全户的房屋进行拆除。在对房屋进行人员和物品清场过程中，未对房屋进行认真清查，最终导致留在屋内的被害人龚某某死亡。案发后，上述8名被告人主动配合到案并接受办案机关调查，被害人龚某某的近亲属出具了书面谅解意见。法院经公开审理，认为公诉机关指控8名被告人的罪名成立，根据8名被告人犯罪的事实、性质、情节以及其行为对社会的危害程度，依法作出上述判决。（完）</p><!--SPINFO#0--></p><!--SPINFO#1--></p><p>　　作者：阳建</p><p>原标题：长沙非法拆除房屋致人死亡案宣判：８人以滥用职权罪、玩忽职守罪和重大责任事故罪被判刑</p>";
        mNewsDetailBodyTv.setText(Html.fromHtml(newsBody));
    }

    @Override
    public void returnNewsDetailsDatas(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgs = newsDetail.getImg();
        String body = newsDetail.getBody();

        setToolBarLayout(newsDetail.getTitle());

        String newsTime = TimeUtil.formatDate(newsDetail.getPtime());
        String newsSource = newsDetail.getSource();
        mNewsDetailFromTv.setText(getString(com.example.common.R.string.news_from, newsSource, newsTime));

    }

    private void setToolBarLayout(String title) {
        mToolbarLayout.setTitle(title);
        mToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(mContext, R.color.white));
        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(mContext, R.color.primary_text_white));
        mToolbarLayout.setContentScrim(new ColorDrawable(ContextCompat.getColor(mContext, R.color.alpha_30_black)));

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
