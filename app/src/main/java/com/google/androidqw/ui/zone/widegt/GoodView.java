package com.google.androidqw.ui.zone.widegt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import utils.LogUtils;

/**
 * 点赞特效
 * Created by lyz on 2017/1/25.
 */
public class GoodView extends PopupWindow implements IGoodView {

    private TextView mGood;
    private float mTextSize = TEXT_SIZE;
    private String mText = TEXT;
    private int mTextColor = TEXT_COLOR;
    private int mDistance = DISTANCE;
    private AnimationSet mAnimationSet;
    private float mFromY = FROM_Y_OFFSET;
    private float mToY = TO_Y_OFFSET;
    private float mToAlpha = TO_ALPH;
    private float mFromAlpha = FROM_ALPH;
    private long mDuration = DURATION;
    private boolean mChengedAttribute = false;


    public GoodView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        setContentView(context);

        setAttribute();

        mAnimationSet = createAnimation();
    }

    public void setImage(Context context, int resId) {
        setImage(context.getResources().getDrawable(resId));
    }

    public void setImage(Drawable drawable) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mGood.setBackground(drawable);
        } else {
            mGood.setBackgroundDrawable(drawable);
        }
        mGood.setText("");
        setWidth(drawable.getIntrinsicWidth());
        setHeight(mDistance + drawable.getIntrinsicHeight());
        mChengedAttribute = true;
    }

    public void show(View v) {
        if (!isShowing()) {
            int xoff = v.getWidth() / 2 - getWidth() / 2;
            int yoff = -v.getHeight() - getHeight();
            LogUtils.logd("GoodView.show"+"v.getWidth() ="+v.getWidth());
            LogUtils.logd("GoodView.show"+"v.getHeight() ="+v.getHeight());
            LogUtils.logd("GoodView.show"+"getWidth() ="+getWidth());
            LogUtils.logd("GoodView.show"+"getHeight() ="+getHeight());
            showAsDropDown(v, xoff, yoff);

            if (mAnimationSet == null || mChengedAttribute) {
                mAnimationSet = createAnimation();
                mChengedAttribute = false;
            }

            mGood.startAnimation(mAnimationSet);
        }
    }

    private AnimationSet createAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation transformation = new TranslateAnimation(0, 0, mFromY, -mToY);
        AlphaAnimation alphaAnimation = new AlphaAnimation(mFromAlpha, mToAlpha);
        animationSet.addAnimation(transformation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(mDuration);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return animationSet;
    }

    private void setAttribute() {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        LogUtils.logd("GoodView.setAttribute" + w + "h=" + h);
        mGood.measure(w, h);
        setWidth(mGood.getMeasuredWidth());
        setHeight(mDistance + mGood.getMeasuredWidth());
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(false);
        setTouchable(false);
        setOutsideTouchable(false);
    }

    private void setContentView(Context context) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        mGood = new TextView(context);
        //去除默认是TextView Padding值
        mGood.setIncludeFontPadding(false);
        mGood.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        mGood.setText(mText);
        mGood.setTextColor(ContextCompat.getColor(context, mTextColor));
        mGood.setLayoutParams(layoutParams);
        relativeLayout.addView(mGood);
        setContentView(relativeLayout);
    }
}
