package com.google.androidqw.ui.main.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.androidqw.R;

import base.BaseActivity;
import butterknife.Bind;

public class SplashActivity extends BaseActivity {


    @Bind(R.id.img_logo)
    ImageView mImgLogo;
    @Bind(R.id.tv_name)
    TextView mTvName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        setTranslanterBar();
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f);
        ObjectAnimator objectText = ObjectAnimator.ofPropertyValuesHolder(mTvName,alpha,scaleX,scaleY);
        ObjectAnimator objectImg = ObjectAnimator.ofPropertyValuesHolder(mImgLogo,alpha,scaleX,scaleY);

        AnimatorSet animatorSet =new AnimatorSet();
        animatorSet.playTogether(objectImg,objectText);
        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                MainActivity.start(SplashActivity.this);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }


}
