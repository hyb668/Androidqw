package com.google.androidqw.ui.zone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.androidqw.R;
import com.google.androidqw.ui.zone.adapter.CircleZoneAdapter;
import com.google.androidqw.ui.zone.bean.CircleItem;
import com.google.androidqw.ui.zone.bean.CommentConfig;
import com.google.androidqw.ui.zone.bean.CommentItem;
import com.google.androidqw.ui.zone.bean.FavortItem;
import com.google.androidqw.ui.zone.contract.CircleContract;
import com.google.androidqw.ui.zone.model.CircleModel;
import com.google.androidqw.ui.zone.prensenter.CirclePrensent;
import com.google.androidqw.view.StateButton;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import app.AppCache;
import app.AppConstant;
import base.BaseActivity;
import base.PageBean;
import butterknife.Bind;
import butterknife.OnClick;
import cn.finalteam.toolsfinal.StringUtils;
import unvieradapter.anim.ScaleInAnimation;
import utils.DisplayUtil;
import utils.KeyBordUtil;
import utils.LogUtils;
import utils.ToastUitl;
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
    @Bind(R.id.ll_bottom_comment)
    LinearLayout mLlBottomComment;
    @Bind(R.id.iv_comment_pic)
    ImageView mIvCommentPic;
    @Bind(R.id.iv_comment_emoji)
    ImageView mIvCommentEmoji;
    private CircleZoneAdapter mCircleZoneAdapter;
    private StateButton mBtnSendComment;
    private LinearLayout linearLayoutCommentBottom;

    private LinearLayoutManager mLinearLayoutManager;
    private int scrollPositionOffset;
    private CommentConfig mConfig;
    private int mChooseCircleH;
    private int mCircleH;
    private double mCurrentKeyBoradH;
    private int mTitltTabHeight;


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
        mBtnSendComment = (StateButton) findViewById(R.id.stateBtn_bottom_send_comment);
        linearLayoutCommentBottom = (LinearLayout) findViewById(R.id.linearLayout_comment_bottom_circle);
        initListener();

        mTitltTab.setTitleText("朋友圈动态");
        mTitltTabHeight = mTitltTab.getMeasuredHeight();
        //设置未读消息的头
        //滑动列表关闭输入框的和计算的他位置的监听
        setTreeViewObserver();
        //初始化适配器
        mCircleZoneAdapter = new CircleZoneAdapter(this, mPresenter, mTitltTabHeight, mRxManager);
        mCircleZoneAdapter.openLoadAnimation(new ScaleInAnimation());
        mLinearLayoutManager = new LinearLayoutManager(this);
        mXrecycleView.setLayoutManager(mLinearLayoutManager);
        mXrecycleView.setAdapter(mCircleZoneAdapter);
        //列表x的监听是否关闭悬浮按钮
        loadData();
    }

    private void setTreeViewObserver() {
        mXrecycleView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                //一般用完之后会立即移除监听器
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                    mXrecycleView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                } else {
//                    mXrecycleView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                }

                Rect outRect = new Rect();
                int statusBarH = getStatusBarHeight(mContext);

                mXrecycleView.getWindowVisibleDisplayFrame(outRect);
                //屏幕高度
                int screenH = mXrecycleView.getRootView().getHeight();

                if (outRect.top != statusBarH) {
                    outRect.top = statusBarH;
                }

                //屏幕bottom 正常情况和screenH是一样高的
                int heightDiff = screenH - (outRect.bottom - outRect.top);

                if (mCurrentKeyBoradH == heightDiff) {
                    return;
                }
                mCurrentKeyBoradH = heightDiff;
                LogUtils.logd("screenH = " + screenH + " &heightDiff = " + heightDiff
                        + " &r.bottom =" + outRect.bottom + " &top=" + outRect.top + " &statusBarH=" + statusBarH);
                if (heightDiff > statusBarH) {                      // 3 - 4 = -1
                    int position = mConfig.getCirclePosition() + 1;//-776 - 144 =-920
                    int mEditTextBodyHeight = linearLayoutCommentBottom.getHeight();
                    //屏幕减去 - 键盘 - 动态item - 评论输入框 - 标题  -920 =  1920 - 900 - 1586 - 210 - 144

                    int offset = screenH - heightDiff - mCircleH - mEditTextBodyHeight - mTitltTabHeight;
                    mLinearLayoutManager.scrollToPositionWithOffset(position, offset);
                    LogUtils.logd("CircleZoneActivity.getOffset" + "//键盘已经弹出" + "&offset=" + offset + "&mEditTextBodyHeight= " + mEditTextBodyHeight);
                } else {
                    // TODO: 2017/1/11  如果editText输入框还是显示状态,那么我就移动动态和评论框在一起后期扩展
                    LogUtils.logd("CircleZoneActivity.getOffset" + "//关闭键盘");
                }
            }
        });
    }


    private void initListener() {
        mEtBottomCommentContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int normalBackgroundColor = 0;
                int normalTextColor = 0;
                if (StringUtils.isEmpty(s.toString())) {
                    normalBackgroundColor = getColor(R.color.white);
                    normalTextColor = getColor(R.color.color_f1f4f6);
                } else {
                    normalBackgroundColor = getColor(R.color.main_color);
                    normalTextColor = getColor(R.color.white);
                }
                mBtnSendComment.setNormalBackgroundColor(normalBackgroundColor);
                mBtnSendComment.setNormalTextColor(normalTextColor);
            }
        });
        mXrecycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (linearLayoutCommentBottom.getVisibility() == View.VISIBLE) {
                    updateInputFrameVisibiliy(View.GONE, null);
                }

                return false;
            }
        });
        mMenuRed.setClosedOnTouchOutside(true);
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
    }


    @Override
    public void returnListDatas(List<CircleItem> circleItems, PageBean pageBean) {
        LogUtils.logd("CircleZoneActivity.setListDatas", new Gson().toJson(circleItems));
        if (mCircleZoneAdapter.getPageBean().isRefresh()) {
            //上拉刷新获取数据
            mCircleZoneAdapter.replaceAll(circleItems);
        } else {
            mCircleZoneAdapter.addAll(circleItems);

        }

        mRxManager.post(AppConstant.CICLEZONE_EXPAND_FINISH, "");
    }


    @Override
    public void refreshFinish() {
        mXrecycleView.reset();
    }

    @Override
    /** 控制它的显示位置
     *
     *
     *  //回复评论的情况 : 需要显示在用户点击的那个评论条的下面;
     *   非回复评论的情况: 我直接显示在所有评论的下面;
     * */
    public void updateInputFrameVisibiliy(int visibiliy, CommentConfig config) {
        mConfig = config;
        if (visibiliy == View.VISIBLE) {
            showCommentHandle(visibiliy);
        } else if (visibiliy == View.GONE) {
            hideCommentHandle(visibiliy);
        }

    }

    @Override
    public void update2AddComment(int circlePos, CommentItem commentItem) {
        if (null != commentItem) {
            //给item添加一个评论数据
            mCircleZoneAdapter.getData().get(circlePos).getReplys().add(commentItem);
            //比如:我评论的是第0个list数据,但是我们要刷新的是第一个item,因为有个下拉刷新头
            mCircleZoneAdapter.notifyItemChanged(circlePos + 1);
        }
        //清空输入框
        mEtBottomCommentContent.setText("");
    }

    @Override
    public void excuteSmoothScrollToPosition(int position) {
        mXrecycleView.smoothScrollToPosition(position);
    }


    //继续循环会出现,数据并发异常,我改变集合的数据后直接return 方法;
    @Override
    public void cancelFavorite(int circlePos, String userId) {
        if (!TextUtils.isEmpty(userId)) {
            List<FavortItem> goodjobs = mCircleZoneAdapter.getData().get(circlePos).getGoodjobs();
            for (int i = 0; i < goodjobs.size(); i++) {
                if (goodjobs.get(i).getUserId().equals(userId)) {
                    goodjobs.remove(goodjobs.get(i));
                    mCircleZoneAdapter.notifyItemChanged(circlePos + 1);
                    return;
                }
            }
        }
    }

    @Override
    public void addFavorite(int circlePos, FavortItem favortItem) {
        if (null != favortItem) {
            mCircleZoneAdapter.getData().get(circlePos).getGoodjobs().add(favortItem);
            mCircleZoneAdapter.notifyItemChanged(circlePos + 1);
        }
    }

    private void hideCommentHandle(int visibiliy) {
        linearLayoutCommentBottom.setVisibility(visibiliy);
        KeyBordUtil.hideSoftKeyboard(mEtBottomCommentContent);
        mMenuRed.showMenuButton(true);
    }

    //判断他是回复给别人还是自己评论动态

    /**
     * 如果item动态的高度特别长,offset就需要给负的, else 如果特别短就需要给正的数让他往下撑撑;
     * item的高度是知道的,用他减去一个数,得出正数或是负数;
     * <p/>
     * 用item减少 正好弹出键盘时候空白的地方(除去标题头和键盘和输入文本框的地方)
     */
    //显示评论操作
    private void showCommentHandle(int visibiliy) {
        linearLayoutCommentBottom.setVisibility(visibiliy);
        KeyBordUtil.showSoftKeyboard(mEtBottomCommentContent);
        //就因为这个getChildAt 获取第二个item的时候就报空指针
        //mChooseCircleH = mXrecycleView.getChildAt(mConfig.getCirclePosition() + 1).getMeasuredHeight() - DisplayUtil.dip2px(48);
        mCircleH = mLinearLayoutManager.findViewByPosition(mConfig.getCirclePosition() + 1).getHeight() - DisplayUtil.dip2px(48);
        mMenuRed.hideMenuButton(true);

    }


    //Get status bar height
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
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
            R.id.stateBtn_bottom_send_comment, R.id.fab1, R.id.fab2, R.id.fab3, R.id.fab4, R.id.fab5, R.id.menu_red
            , R.id.iv_comment_pic, R.id.iv_comment_emoji})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_comment_pic:
                break;
            case R.id.iv_comment_emoji:
                break;
            case R.id.stateBtn_bottom_send_comment:
                if (mPresenter != null && mConfig != null) {
                    //发布评论
                    String content = mEtBottomCommentContent.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUitl.showToastWithImg("评论内容不能为空", R.drawable.ic_warm);
                        return;
                    }
                    mPresenter.addCommentContent(content, mConfig);
                }
                updateInputFrameVisibiliy(View.GONE, null);
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
                //   isShowFabButton(mMenuRed.isOpened());
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
