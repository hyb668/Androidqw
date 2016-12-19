package com.google.androidqw.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.androidqw.R;
import com.google.androidqw.ui.zone.CircleZoneActivity;

import base.BaseFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.NormarlTitle;

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
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class MomentsMainFragment extends BaseFragment {
    @Bind(R.id.ll_moments)
    LinearLayout mLlMoments;
    @Bind(R.id.titlt_tab)
    NormarlTitle mTitltTab;

    @Override
    protected int getLayoutResource() {
        return R.layout.frament_moments;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mTitltTab.setTitleText("朋友圈");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.ll_moments)
    public void start() {
        CircleZoneActivity.start(getContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
