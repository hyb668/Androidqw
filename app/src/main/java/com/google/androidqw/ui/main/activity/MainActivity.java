package com.google.androidqw.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.androidqw.R;
import com.google.androidqw.bean.TabEntity;
import com.google.androidqw.ui.main.fragment.MomentsMainFragment;
import com.google.androidqw.ui.main.fragment.NewsMainFrament;
import com.google.androidqw.ui.main.fragment.VideoMainFragment;

import java.util.ArrayList;

import app.AppConstant;
import base.BaseActivity;
import butterknife.Bind;
import rx.functions.Action1;
import utils.LogUtils;

public class MainActivity extends BaseActivity {

    @Bind(R.id.rl_container)
    FrameLayout mRlContainer;
    @Bind(R.id.tab_layout)
    CommonTabLayout mTabLayout;
    private String[] mTitles = {"首页", "视频", "朋友圈"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_video_normal, R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_video_selected, R.mipmap.ic_care_selected};


    private NewsMainFrament newsMainFrament;
    private VideoMainFragment videoMainFragment;
    private MomentsMainFragment momentsMainFragment;

    private int mTabLayoutHeight;
    private ArrayList<CustomTabEntity> mTabEntityList=new ArrayList<>();

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
        mTabLayout.measure(0, 0);
        mTabLayoutHeight = mTabLayout.getMeasuredHeight();
        //监听底部菜单的显示和隐藏
        mRxManager.on(AppConstant.MENU_SHOW_HIDE, new Action1<Boolean>() {
            @Override
            public void call(Boolean hideOrShow) {
                startAnim(hideOrShow);
            }
        });
    }

    /**
     * 底部菜单的显示和隐藏
     *
     * @param hideOrShow
     */
    private void startAnim(Boolean hideOrShow) {
        // TODO: 2016/11/17
    }


    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            newsMainFrament = (NewsMainFrament) getSupportFragmentManager().findFragmentByTag("newsMainFragment");
            videoMainFragment = (VideoMainFragment) getSupportFragmentManager().findFragmentByTag("videoMainFragment");
            momentsMainFragment = (MomentsMainFragment) getSupportFragmentManager().findFragmentByTag("MomentsMainFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.MENU_CURRENT_TAB_POSITON);
        } else {
            newsMainFrament = new NewsMainFrament();
            videoMainFragment = new VideoMainFragment();
            momentsMainFragment = new MomentsMainFragment();
            //错误 :
            // Caused by: java.lang.NullPointerException: Attempt to write to field
            //          'int android.support.v4.app.Fragment.mNextAnim' on a null object reference
            // 所以为了能找到创建的Fragment，推荐加上Tag。
            // 原因: 因为我没有加上tag, 导致找不到就报错了;
            transaction.add(R.id.rl_container, newsMainFrament,"newsMainFragment");
            transaction.add(R.id.rl_container, videoMainFragment,"videoMainFragment");
            transaction.add(R.id.rl_container, momentsMainFragment,"MomentsMainFragment");
        }
        transaction.commit();
        switchTo(currentTabPosition);
        mTabLayout.setCurrentTab(currentTabPosition);
    }

    //切换底部面板
    private void switchTo(int position) {
        LogUtils.logd("主页菜单=" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(videoMainFragment);
        transaction.hide(momentsMainFragment);
        transaction.hide(newsMainFrament);
        switch (position) {
            case 0:
                transaction.show(newsMainFrament);
                break;
            case 1:
                transaction.show(videoMainFragment);
                break;
            case 2:
                transaction.show(momentsMainFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initTab();
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mTabLayout != null) {
            outState.putInt(AppConstant.MENU_CURRENT_TAB_POSITON,mTabLayout.getCurrentTab());
        }
    }
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntityList.add(new TabEntity(mTitles[i],mIconSelectIds[i],mIconUnselectIds[i]));
        }
        mTabLayout.setTabData(mTabEntityList);

        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                    switchTo(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }


}
