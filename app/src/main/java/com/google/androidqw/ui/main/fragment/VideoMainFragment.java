package com.google.androidqw.ui.main.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.androidqw.R;
import com.google.androidqw.bean.VideoChannelTable;
import com.google.androidqw.db.VideoChannelTableManager;
import com.google.androidqw.ui.news.fragment.VideoFragment;
import com.google.androidqw.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import app.AppConstant;
import base.BaseFragment;
import base.BaseFramentAdapter;
import butterknife.Bind;
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
 * 创建日期 ：  on 2016/11/17.
 * <p/>
 * 描 述 ：
 * <p/>
 * <p/>
 * 修订历史 ： 首页视频界面
 * <p/>
 * ============================================================
 **/
public class VideoMainFragment extends BaseFragment {
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.rloat_button)
    FloatingActionButton mRloatButton;

    @Override
    protected int getLayoutResource() {
        return R.layout.frament_video;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        List<Fragment> fragments=new ArrayList<>();
        List<VideoChannelTable> channelTableList = VideoChannelTableManager.loadVideoChannels();
        List<String> channelNameList=new ArrayList<>();

        for (int i = 0; i < channelTableList.size(); i++) {
            channelNameList.add(channelTableList.get(i).getChannelName());
            fragments.add(getFragment(channelTableList.get(i)));
        }

        BaseFramentAdapter mViewPagerAdapter = new BaseFramentAdapter(getChildFragmentManager(),fragments,channelNameList);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //设置tabLayout超过一屏幕可以滚动
        MyUtils.dynamicSetTabLayoutMode(mTabLayout);
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(pagerAdapter.getTabView(i));
//        }

        mRloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRxManager.post(AppConstant.NEWLIST_TO_TOP,"");
            }
        });
        mRxManager.on(AppConstant.MENU_SHOW_HIDE, new Action1<Boolean>() {
            @Override
            public void call(Boolean isHide) {
                if (isHide && mRloatButton.getAlpha() == 0) {
                    return ;
                }
                if (!isHide && mRloatButton.getAlpha() == 1) {
                    return ;
                }

                if (isHide) {
                    hideButtion(mRloatButton);
                }else{
                    showButton(mRloatButton);
                }
            }
        });
    }

    private Fragment getFragment(VideoChannelTable channelTable) {
        VideoFragment videoFragment = new VideoFragment();
        Bundle args=new Bundle();
        args.putString(AppConstant.VIDEO_TYPE,channelTable.getChannelId());
        videoFragment.setArguments(args);
        return videoFragment;
    }


}
