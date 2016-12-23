package com.google.androidqw.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.androidqw.R;

/**
 * Created by lyz on 2016/12/21.
 * 一个图片和文本的自定义属性
 */
public class LayouItemView extends LinearLayout {

    private LinearLayout mLinearMoments;
    private TextView mTextViewDetails;
    private ImageView mImgViewPic;

    public LayouItemView(Context context) {
        this(context, null);
    }

    public LayouItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LayouItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.layout_item_view, this);
        mLinearMoments = (LinearLayout) view.findViewById(R.id.ll_moments);
        mTextViewDetails = (TextView) view.findViewById(R.id.text_view_details);
        mImgViewPic = (ImageView) view.findViewById(R.id.img_view_pic);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LayoutItemView);
        String itemDetailsText = a.getString(R.styleable.LayoutItemView_itemDetail);
        Drawable itemDrawable = a.getDrawable(R.styleable.LayoutItemView_itemPicBackground);
        a.recycle();

//      setBackground(getResources().getDrawable(R.drawable.selector_gray_deepgray));
        mImgViewPic.setBackgroundDrawable(itemDrawable);
        setText(itemDetailsText);
    }

    private void setBackground(int itemDrawable) {
        mImgViewPic.setImageBitmap(BitmapFactory.decodeResource(getResources(), itemDrawable));
    }

    private void setText(String itemDetailsText) {
        mTextViewDetails.setText(itemDetailsText);
    }

    public void setImageDrawable(String text) {
        setText(text);
    }

    public void setImgViewPic(@DrawableRes int resId) {
        setBackground(resId);
    }

}
