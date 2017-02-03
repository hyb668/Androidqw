package com.google.androidqw.ui.news.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.google.androidqw.R;
import com.google.androidqw.bean.NewsPhotoDetail;
import com.google.androidqw.databinding.ActivityPhototDetailBinding;

import java.util.List;

import app.AppConstant;
import base.BaseBingActivity;
import uk.co.senab.photoview.PhotoView;
import utils.ImageLoaderUtils;
import utils.LogUtils;

import static base.BaseActivity.toolBarFinish;

/**
 * binding 的泛型传递给父类,父类通过抽象方法强转成泛型,infate一个布局(祖父类) 把我们bingding布局给add进来;
 */
public class PhototDetailActivity extends BaseBingActivity<ActivityPhototDetailBinding> {


    private List<NewsPhotoDetail.Picture> mPictures;
    private int mPosition;

    public static void start(Context context, int position, NewsPhotoDetail newsPhotoDetail) {
        Intent intent = new Intent(context, PhototDetailActivity.class);
        intent.putExtra(AppConstant.PARAMS_NAME_PHOTO_NEWS_DETAIL, newsPhotoDetail);
        intent.putExtra(AppConstant.PARAMS_NAME_PHOTO_NEWS_DETAIL_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photot_detail);


    }

    @Override
    protected void initData() {
        NewsPhotoDetail newsPhotoDetail = (NewsPhotoDetail) getIntent().getSerializableExtra(AppConstant.PARAMS_NAME_PHOTO_NEWS_DETAIL);
        mPosition = getIntent().getIntExtra(AppConstant.PARAMS_NAME_PHOTO_NEWS_DETAIL_POSITION, 0);
        mPictures = newsPhotoDetail.getPictures();
        initToolBar();
        setAdapter();
    }

    private void initToolBar() {
        bindingView.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolBarFinish(PhototDetailActivity.this);
            }
        });
    }

    private void setAdapter() {
        bindingView.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String size = position + 1 + " / " + mPictures.size();
                bindingView.tvPhotoContent.setText(size + mPictures.get(position).title.get());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        PagerAdapter photoAdapter = new PagerAdapter() {
            @Override
            public int getCount() {

                return mPictures.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LogUtils.logd("PhototDetailActivity.instantiateItem." + mPictures.get(position).title.get());
                View view = View.inflate(mContext, R.layout.item_photo_details, null);
                if (view != null) {
                    PhotoView photoView = (PhotoView) view.findViewById(R.id.photoView);
/*

                    int width = DisplayUtil.getScreenWidth(mContext);
                    int height = DisplayUtil.getScreenHeight(mContext) / 4;
*/

                    // int width =FrameLayout.LayoutParams.MATCH_PARENT;
                    //int height = DisplayUtil.getScreenHeight(mContext) / 4;

                    ImageLoaderUtils.displayBigPhoto(mContext, photoView, mPictures.get(position).imgSrc.get());
//                    Glide.with(mContext).load(mPictures.get(position).imgSrc.get())
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .centerCrop()
//                            .error(com.example.common.R.drawable.ic_empty_picture)
//                           // .override(width, height)
////                            //     .placeholder(com.example.common.R.drawable.ic_image_loading)
//                            .crossFade().into(photoView);
                }
                container.addView(view, 0);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        bindingView.viewPager.setAdapter(photoAdapter);
        bindingView.viewPager.setCurrentItem(mPosition);
    }
}
