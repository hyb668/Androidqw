package com.google.androidqw.ui.zone;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.androidqw.R;
import com.google.androidqw.ui.zone.adapter.CircleZoneAdapter;
import com.google.androidqw.ui.zone.bean.CircleItem;
import com.google.androidqw.ui.zone.contract.CircleContract;
import com.google.androidqw.ui.zone.model.CircleModel;
import com.google.androidqw.ui.zone.prensenter.CirclePrensent;
import com.google.androidqw.view.StateButton;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import app.AppCache;
import base.BaseActivity;
import base.PageBean;
import butterknife.Bind;
import butterknife.OnClick;
import unvieradapter.anim.ScaleInAnimation;
import utils.LogUtils;
import view.NormarlTitle;

public class CircleZoneActivity extends BaseActivity<CirclePrensent, CircleModel> implements CircleContract.View {

    @Bind(R.id.titlt_tab)
    NormarlTitle mTitltTab;
    @Bind(R.id.xrecycleView)
    XRecyclerView mXrecycleView;
    @Bind(R.id.activity_circle_zone)
    RelativeLayout mActivityCircleZone;
    @Bind(R.id.fab1)
    FloatingActionButton mFab1;
    @Bind(R.id.fab2)
    FloatingActionButton mFab2;
    @Bind(R.id.fab3)
    FloatingActionButton mFab3;
    @Bind(R.id.fab4)
    FloatingActionButton mFab4;
    @Bind(R.id.fab5)
    FloatingActionButton mFab5;
    @Bind(R.id.menu_red)
    FloatingActionMenu mMenuRed;
    @Bind(R.id.et_bottom_comment_content)
    EditText mEtBottomCommentContent;
    @Bind(R.id.stateBtn_bottom_send_comment)
    StateButton mStateBtnBottomCommentIcon;
    @Bind(R.id.ll_bottom_comment)
    LinearLayout mLlBottomComment;
    private CircleZoneAdapter mCircleZoneAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CircleZoneActivity.class);
        context.startActivity(intent);
    }

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
        //mMenuRed.setClosedOnTouchOutside(true);
        mTitltTab.setTitleText("朋友圈动态");

        //滑动列表关闭输入框的和计算的他位置的监听


        //设置未读消息的头


        //初始化适配器
        mCircleZoneAdapter = new CircleZoneAdapter(this, mPresenter);
        mCircleZoneAdapter.openLoadAnimation(new ScaleInAnimation());
        mXrecycleView.setLayoutManager(new LinearLayoutManager(this));
        mXrecycleView.setAdapter(mCircleZoneAdapter);
        mXrecycleView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mCircleZoneAdapter.getPageBean().setRefresh(true);
                loadData();
            }

            @Override
            public void onLoadMore() {
                // STOPSHIP: 2016/12/23  dome 做了一个默认网络请求1分钟加载数据的方法
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCircleZoneAdapter.getPageBean().setRefresh(false);
                        loadData();
                    }
                }, 500);
            }
        });
        //列表x的监听是否关闭悬浮按钮
        loadData();
    }

    @Override
    public void returnListDatas(List<CircleItem> circleItems, PageBean pageBean) {
        LogUtils.logd("CircleZoneActivity.setListDatas", circleItems.toString());
    }

    private void loadData() {

        //刷新时才查未读条数
        if (mCircleZoneAdapter.getPageBean().isRefresh()) {
            mPresenter.getNotReadNewsCount();
        }
        mPresenter.getListDatasRequest(
                "0",
                AppCache.getInstance().getUserId(),
                mCircleZoneAdapter.getPageBean().getLoadPage(),
                mCircleZoneAdapter.getPageBean().getRows());

    }

    @OnClick({R.id.tv_back,
            R.id.stateBtn_bottom_send_comment, R.id.fab2, R.id.fab3, R.id.fab4, R.id.fab5, R.id.menu_red})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.stateBtn_bottom_send_comment:

                break;
            case R.id.fab2:
                break;
            case R.id.fab3:
                break;
            case R.id.fab4:
                break;
            case R.id.fab5:
                break;
            case R.id.menu_red:
               // mMenuRed.open(true);
                break;
        }
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
