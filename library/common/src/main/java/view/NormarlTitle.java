package view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.R;

import utils.DisplayUtil;

/**
 * ============================================================
 * <p>
 * 版 权 ： 刘宇哲 版权所有 (c) 2016
 * <p>
 * 作 者 : 刘宇哲
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ：  on 2016/12/19.
 * <p>
 * 描 述 ：
 * <p>
 * <p>
 * 修订历史 ：
 * <p>
 * ============================================================
 **/
public class NormarlTitle extends FrameLayout {


    private ImageView ivRight;
    private TextView ivBack,tvTitle, tvRight;
    private FrameLayout rlCommonTitle;
    private Context context;

    public NormarlTitle(Context context) {
        super(context);
        initview(context);
    }

    public NormarlTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public NormarlTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
    }

    private void initview(Context context) {
        View.inflate(context, R.layout.bar_normal, this);
        ivBack = (TextView) findViewById(R.id.tv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.image_right);
        rlCommonTitle = (FrameLayout) findViewById(R.id.common_title);
    }



    public void setHeaderHeight() {
        rlCommonTitle.setPadding(0, DisplayUtil.getStatusBarHeight(context), 0, 0);
        rlCommonTitle.requestLayout();
    }

    /**
     * 管理返回按钮
     */
    public void setBackVisibility(boolean visible) {
        if (visible) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧字符串
     * @param visiable
     */
    public void setTvLeftVisiable(boolean visiable){
        if (visiable){
            ivBack.setVisibility(View.VISIBLE);
        }else{
            ivBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧字符串
     * @param tvLeftText
     */
    public void setTvLeft(String tvLeftText){
        ivBack.setText(tvLeftText);
    }


    /**
     * 管理标题
     */
    public void setTitleVisibility(boolean visible) {
        if (visible) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    public void setTitleText(String string) {
        tvTitle.setText(string);
    }

    public void setTitleText(int string) {
        tvTitle.setText(string);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    /**
     * 右图标
     */
    public void setRightImagVisibility(boolean visible) {
        ivRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightImagSrc(int id) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(id);
    }

    /**
     * 获取右按钮
     * @return
     */
    public View getRightImage() {
        return ivRight;
    }

    /**
     * 左图标
     *
     * @param id
     */
    public void setLeftImagSrc(int id) {
        ivBack.setCompoundDrawables(getResources().getDrawable(id),null,null,null);
    }
    /**
     * 左文字
     *
     * @param str
     */
    public void setLeftTitle(String str) {
        ivBack.setText(str);
    }

    /**
     * 右标题
     */
    public void setRightTitleVisibility(boolean visible) {
        tvRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightTitle(String text) {
        tvRight.setText(text);
    }

    /*
     * 点击事件
     */
    public void setOnBackListener(OnClickListener listener) {
        ivBack.setOnClickListener(listener);
    }

    public void setOnRightImagListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setOnRightTextListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    /**
     * 标题背景颜色
     *
     * @param color
     */
    public void setBackGroundColor(int color) {
        rlCommonTitle.setBackgroundColor(color);
    }
    public Drawable getBackGroundDrawable() {
        return rlCommonTitle.getBackground();
    }
}
