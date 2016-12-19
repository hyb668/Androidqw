package com.google.androidqw.ui.zone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.androidqw.R;
import com.google.androidqw.ui.zone.model.CircleModel;
import com.google.androidqw.ui.zone.prensenter.CirclePrensent;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import view.NormarlTitle;

public class CircleZoneActivity extends BaseActivity<CirclePrensent, CircleModel> {

    @Bind(R.id.titlt_tab)
    NormarlTitle mTitltTab;
    @Bind(R.id.xrecycleView)
    XRecyclerView mXrecycleView;
    @Bind(R.id.activity_circle_zone)
    RelativeLayout mActivityCircleZone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_zone;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

    }


    public static void start(Context context) {
        Intent intent = new Intent(context, CircleZoneActivity.class);
        context.startActivity(intent);
    }


}
