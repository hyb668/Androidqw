package com.google.androidqw.ui.zone.widegt;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.androidqw.R;

import app.AppConstant;
import baserx.RxManager;
import rx.functions.Action1;
import utils.DisplayUtil;
import utils.LogUtils;

/**
 * 　朋友圈折叠文本 TODO 条目的展开和收起是状态 错乱了;
 * <p/>
 * 1.21 23.18
 * <p/>
 * <p/>
 * <p/>
 * 有空研究一下,为什么我在点击收起的时候改变高度后,刷新列表后 getLayoutCount的数量不超过5个了?/
 * 我手动设置一下他的maxLines为无穷大(反正他能点击后收起肯定是多行的了)
 * <p/>
 * Created by lyz on 2016/12/26.
 */
public class ExpandableTextView extends LinearLayout {

    private SparseBooleanArray mCollapsSatusMap;
    private Drawable mCollapseDrawable;
    private Drawable mExspandDrawable;
    private TextView mTextViewContent;
    private TextView mExpandOrCollapse;
    //收起展开or本文
    private float mTextViewSize;
    private int mTextViewExpandColor;
    private int mTextViewContentColor;
    //是否关闭status
    private boolean showExpandCollapseDrawable = true;
    //content max Lines
    private int textContentLines = 5;
    //是否refresh 绘制
    private boolean isRefeshMeasure;
    //最大Lines height
    private int mMinHeight;
    //文字全部显示是高度
    private int mMaxHeight;
    // whether content
    private boolean isHasTextContent;
    //默认折叠状态
    private boolean isExpand = true;
    //列表索引
    private int mPosition;
    private int mTitltTabHeight;
    //true 超出屏幕
    private boolean isOverScreen;
    private Context mContext;

    public interface ExpandedChangeLiener {
        /**
         * @param isExpand true 就是展开
         */
        void onExpandedChange(boolean isExpand, int position);
    }

    ExpandedChangeLiener mExpandedChangeLiener;

    public ExpandedChangeLiener getExpandedChangeLiener() {
        return mExpandedChangeLiener;
    }

    public void setExpandedChangeLiener(ExpandedChangeLiener expandedChangeLiener) {
        mExpandedChangeLiener = expandedChangeLiener;
    }

    public ExpandableTextView(Context context) {
        this(context, null);

    }


    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    @Override
    public void setVerticalGravity(int verticalGravity) {
        if (LinearLayout.HORIZONTAL == verticalGravity) {
            new IllegalArgumentException("ExpandableTextView only support Vertical");
        }
        super.setVerticalGravity(verticalGravity);
    }

    private void init(AttributeSet attrs, Context context) {
        mContext = context;
        mCollapsSatusMap = new SparseBooleanArray();
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        //[ke'læps]
        mCollapseDrawable = a.getDrawable(R.styleable.ExpandableTextView_collapseDrawable);
        //[ɪkˈspænd]
        mExspandDrawable = a.getDrawable(R.styleable.ExpandableTextView_expandDrawable);
        showExpandCollapseDrawable = a.getBoolean(R.styleable.ExpandableTextView_showExpandCollapseDrawable, true);
        mTextViewSize = a.getDimension(R.styleable.ExpandableTextView_contentTextSize, DisplayUtil.sp2px(14));
        mTextViewContentColor = a.getColor(R.styleable.ExpandableTextView_contentTextColor, ContextCompat.getColor(getContext(), R.color.black));
        mTextViewExpandColor = a.getColor(R.styleable.ExpandableTextView_collapseExpandTextColor, ContextCompat.getColor(getContext(), R.color.main_color));
        //pic
        if (showExpandCollapseDrawable) {
            if (mCollapseDrawable == null) {
                mCollapseDrawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_green_arrow_up);
            }
            if (mExspandDrawable == null) {
                mExspandDrawable = ContextCompat.getDrawable(getContext(), R.drawable.icon_green_arrow_down);
            }
        }
        a.recycle();
        //textContent
        //textColor
        //textSize
        //gravity
        //set default orientation
        setVerticalGravity(LinearLayout.VERTICAL);
        // When textView have content , set view VISIBLE
        setVisibility(GONE);
        //  setView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setView();

    }

    /**
     * 获取布局
     */
    private void setView() {
        addView(((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_expand_chind, null, false));
        mTextViewContent = (TextView) findViewById(R.id.tv_expandable_content);
        mExpandOrCollapse = (TextView) findViewById(R.id.tv_expand_collapse);

        mTextViewContent.getPaint().setTextSize(mTextViewSize);
        mTextViewContent.setTextColor(mTextViewContentColor);

        mExpandOrCollapse.getPaint().setTextSize(mTextViewSize);
        mExpandOrCollapse.setTextColor(mTextViewExpandColor);

        mExpandOrCollapse.setText(showExpandCollapseDrawable ? getResources().getString(R.string.expand) : getResources().getString(R.string.shink));

        mExpandOrCollapse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                executeExpand();
            }
        });

        mExpandOrCollapse.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //只处理展开的操作
                if (event.getAction() == MotionEvent.EDGE_TOP && !isExpand) {
                    LogUtils.logd("ExpandableTextView.onTouch" + "event.getRawY=" + event.getRawY() +
                            "&event.getY" + event.getY() + "&getHeight()=" + getHeight() + "mExpandOrCollapse.getTop=" + mExpandOrCollapse.getTop() +
                            "&mTextViewContent.getTop=" + mTextViewContent.getTop() + "&mMinHeight=" + mMinHeight);
//                   文本的高度-距离标题栏的高度=5行的高度就是正好返回true需要滚动
//                   文本的高度-距离标题栏的高度>5行的高度就是正好返回true需要滚动
//                   文本的高度-距离标题栏的高度<5行的高度就是正好返回false不需要滚动

                    int stateTitleH = getStatusBarHeight(mContext);
                    int TitlteH = mTitltTabHeight;
                    isOverScreen = mExpandOrCollapse.getTop() - (event.getRawY() - (stateTitleH + TitlteH)) >= mMinHeight;

                    LogUtils.logd("ExpandableTextView.onTouch" + "-----" + isOverScreen);
                    LogUtils.logd("ExpandableTextView.onTouch" + "" + (event.getRawY() - 204));
                }
                return false;
            }
        });
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

    private void executeExpand() {
        ValueAnimator valueAnimator = null;
        // FIXME: 2017/1/22  这里的mMinHeight 和 getHeight 高度是一样的;
        LogUtils.logd("ExpandableTextView.onClick" + isExpand + "&mMaxHeight=" + mMaxHeight + "&mMinHeight" + mMinHeight + "&TextView is height=" + mTextViewContent.getHeight()
                + "& getHeight()=" + getHeight());
        if (isExpand) {
            //展开
            valueAnimator = new ValueAnimator().ofInt(getHeight(), mMaxHeight);
        } else {
            valueAnimator = new ValueAnimator().ofInt(getHeight(), mMinHeight);
        }


        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer animatedValue = (Integer) animation.getAnimatedValue();
                mTextViewContent.setMaxHeight(animatedValue);
                requestLayout();

            }
        });
        isExpand = !isExpand;
        mCollapsSatusMap.put(mPosition, isExpand);
        mExpandOrCollapse.setText(isExpand ? getResources().getString(R.string.expand) : getResources().getString(R.string.shink));
        valueAnimator.setDuration(300);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                LogUtils.logd("ExpandableTextView.onAnimationEnd" + "end" + "mPosition=" + mPosition);
                if (mExpandedChangeLiener != null) {
                    mExpandedChangeLiener.onExpandedChange(isExpand, mPosition);
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                LogUtils.logd("ExpandableTextView.onAnimationStart" + "start");
            }
        });
        valueAnimator.start();
    }


    /**
     * 获取内容tv真实高度（含padding）
     *
     * @param textView
     * @return
     */
    private static int getRealTextViewHeight(TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isHasTextContent || getVisibility() == GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        LogUtils.logd("ExpandableTextView.onMeasure" + "进来了;");
        isHasTextContent = false;

        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        mExpandOrCollapse.setVisibility(View.GONE);
        mTextViewContent.setMaxLines(Integer.MAX_VALUE);

        // Measure : 通过测量获取最大高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //如果内容真实行数小于等于最大行数，不处理
        if (mTextViewContent.getLineCount() <= textContentLines) {
            return;
        }
        // 获取内容tv真实高度（含padding）
        mMaxHeight = getRealTextViewHeight(mTextViewContent);

        // 如果是收起状态，重新设置最大行数
        if (showExpandCollapseDrawable) {
            mTextViewContent.setMaxLines(textContentLines);
        }
        mExpandOrCollapse.setVisibility(View.VISIBLE);

        // Re-measure with new setup  : 通过测量获取5行高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (showExpandCollapseDrawable) {
            // Gets the margin between the TextView's bottom and the ViewGroup's bottom

            int height = mTextViewContent.getHeight();//1877
            int getLayoutHeight = mTextViewContent.getLayout().getHeight();//317
            // 保存这个容器的测量高度
            int measuredHeight = getMeasuredHeight();//404
            mMinHeight = mTextViewContent.getLayout().getHeight();
        }
     /*   isHasTextContent = false;
        mExpandOrCollapse.setVisibility(GONE);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int lineCount = mTextViewContent.getLayout().getLineCount();
        if (lineCount <= textContentLines) {
            int singleHeight = getRealTextViewHeight(mTextViewContent);
            resetTextViewHeight(singleHeight);
            return;
        }
        mExpandOrCollapse.setVisibility(VISIBLE);
        if (isExpand) {
            mTextViewContent.setMaxLines(5);
            mTextViewContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //一般用完之后会立即移除监听器
                    mTextViewContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    mMinHeight = mTextViewContent.getHeight();
                    //mMinHeight = mTextViewContent.getLayout().getHeight();
                    LogUtils.logd("ExpandableTextView.onGlobalLayout" + "获取最小高度=" + mMinHeight + "它的lineCount=" + mTextViewContent.getLayout().getLineCount());
                    //超过5行了
                    mTextViewContent.setMaxLines(Integer.MAX_VALUE);
                    // Measure
                    mTextViewContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            //一般用完之后会立即移除监听器
                            mTextViewContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            mMaxHeight = getRealTextViewHeight(mTextViewContent);
                            LogUtils.logd("ExpandableTextView.onGlobalLayout" + "获取最大高度=" + mMaxHeight + "它的lineCount=" + mTextViewContent.getLayout().getLineCount());
                            resetTextViewHeight(mMinHeight);
                        }
                    });
                }
            });
        }*/

    }

    /**
     * 这里我需要知道如果列表在滑动,那么我就让isExpand 为 true ,把列表给合上
     */
    public void setText(String text, int position, int titltTabHeight, RxManager rxManager) {
        mPosition = position;
        mTitltTabHeight = titltTabHeight;
        isHasTextContent = true;
        setVisibility(GONE);
        mTextViewContent.setText("");
        isExpand = mCollapsSatusMap.get(mPosition, true);
        mExpandOrCollapse.setText(isExpand ? getResources().getString(R.string.expand) : getResources().getString(R.string.shink));
        rxManager.on(AppConstant.CICLEZONE_EXPAND_FINISH, new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (!isExpand) {
                    //如果当前是 展开状态,变成收起状态,reset
                    mTextViewContent.setMaxLines(textContentLines);
                    executeExpand();
                }
            }
        });
        if (!TextUtils.isEmpty(text)) {
            mTextViewContent.setText(text);
            setVisibility(VISIBLE);
            // FIXME: 2017/1/22 就因为在更新数据的时候,holder已经有View了,不再走xml新建,复用前面的他的高度没有更新,我只要让父view包裹内容就可以了;不会展上次的View的高度了;
            clearAnimation();
            getLayoutParams().height = LayoutParams.WRAP_CONTENT;
            requestLayout();
        }
    }

    /**
     * @return true 收起后文字内容看不见了
     */
    public boolean isTextViewOverScreen() {
        return isOverScreen;
    }

}
