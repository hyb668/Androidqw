package com.google.androidqw.ui.zone.widegt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.androidqw.R;

import java.util.List;

import utils.CollectionUtils;
import utils.DisplayUtil;
import utils.ImageLoaderUtils;

/**
 * 找到布局id,然后set setPicPathList 给图片路径 : ok
 * ---------------------
 * 显示 : 显示 1 ~ N 个图片的ViewGroup : 图片为正方形
 * Created by lyz on 2016/12/28.
 * // TODO: 2016/12/30  自己尝试实现效果,然后和作者比较,直接抄无意义;
 */
public class MultiImageView extends LinearLayout {
    //view的宽度
    private int MAX_WIDTH = DisplayUtil.getScreenWidth(getContext());
    //图片容器池
    private List<String> picPathList;
    //图片的间距
    private int pxImgPadding = DisplayUtil.dip2px(3);
    //每行显示最大数量
    private int MAX_ROW_COUNT = 3;

    //单个图片的宽高
    private int singlePictureHeight = 0;
    //多个时候图片宽高
    private int muitPictureWithHeight = 0;

    private LayoutParams singlePicParams;
    private LayoutParams muitPicParams;
    private LayoutParams muitPicColumFirstParams;
    private LayoutParams rowParams;


    public MultiImageView(Context context) {
        this(context, null);
    }

    public MultiImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPicPathList(List<String> picPathList) throws IllegalArgumentException {
        if (CollectionUtils.isNullOrEmpty(picPathList)) {
            throw new IllegalArgumentException("picPathList ... null");
        }

        this.picPathList = picPathList;
        //因为3个图片有2个间距
        muitPictureWithHeight = (MAX_WIDTH - pxImgPadding * 2) / 3;
        singlePictureHeight = MAX_WIDTH / 3;
        initLayoutParams();

        initView();
    }


    private void initLayoutParams() {
        int wrapContent = LayoutParams.WRAP_CONTENT;
        int matchParent = LayoutParams.MATCH_PARENT;
        //pxSinglePic
        singlePicParams = new LayoutParams(matchParent, wrapContent);

        //muit
        muitPicColumFirstParams = new LayoutParams(muitPictureWithHeight, muitPictureWithHeight);
        //不是第一个我们给他加一个边距
        muitPicParams = new LayoutParams(muitPictureWithHeight, muitPictureWithHeight);
        muitPicParams.setMargins(pxImgPadding, 0, 0, 0);

        rowParams = new LayoutParams(matchParent, wrapContent);
    }

    private void initView() {
        this.setOrientation(VERTICAL);
        this.removeAllViews();

        if (CollectionUtils.isNullOrEmpty(picPathList)) {
            return;
        }

        int pictureCount = picPathList.size();
        if (pictureCount == 1) {
            addView(createImageView(0, false));
        } else {
            //多个图片 , 如果是4个就每排显示2个
            if (pictureCount == 4) {
                MAX_ROW_COUNT = 2;
            } else {
                MAX_ROW_COUNT = 3;
            }
            //先算行的数量,然后遍历出行的数据放入大容器,在给每一行里放进列的图片
            //如果pictureCount % MAX_ROW_COUNT >0 说明不是大于3就是小于3,那么就至少给他一行;否则就是==0 那么就会是正好/够他的count数量
            int rowCount = pictureCount / MAX_ROW_COUNT
                    + (pictureCount % MAX_ROW_COUNT > 0 ? 1 : 0);

            for (int rowCursor = 0; rowCursor < rowCount; rowCursor++) {
                LinearLayout rowLinearLayout = new LinearLayout(getContext());
                rowLinearLayout.setOrientation(HORIZONTAL);
                rowLinearLayout.setLayoutParams(rowParams);
                addView(rowLinearLayout);
                //如果不是第一排,给他一个top
                rowLinearLayout.setPadding(0, pxImgPadding, 0, 0);
                //算列的数量,如果是最后一排,取剩余,否则就是MAX_ROW_COUNT
                int columCount = MAX_ROW_COUNT;
                if (rowCursor == rowCount - 1) {
                    columCount = pictureCount % MAX_ROW_COUNT == 0 ? MAX_ROW_COUNT : pictureCount % MAX_ROW_COUNT;
                }

                // 每个图片的position,第二排就要从 4 5 6 开始,算出偏移量
                int rowOffset = rowCursor * MAX_ROW_COUNT;
                for (int columCursor = 0; columCursor < columCount; columCursor++) {
                    int position = columCursor + rowOffset;
                    rowLinearLayout.addView(createImageView(position, true));
                }
            }
        }
    }

    /**
     * @param position
     * @param isMultImage 带间距的params
     * @return
     */
    private View createImageView(int position, boolean isMultImage) {
        LayoutParams params;
        String url = picPathList.get(position);
        ImageView imageView = new ColorFilterImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (isMultImage) {
            //如果要是最后一列也就是3的倍数,用不带间距的params
            params = position % MAX_ROW_COUNT == 0 ? muitPicColumFirstParams : muitPicParams;
        } else {
            imageView.setAdjustViewBounds(false);
//            params =  new LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(200));;
            imageView.setMinimumHeight(DisplayUtil.dip2px(300));
            imageView.setMaxHeight(singlePictureHeight);
            params=singlePicParams;
        }
        imageView.setLayoutParams(params);

        imageView.setTag(R.string.zone_img_position, position);
        imageView.setId(url.hashCode());
        imageView.setOnClickListener(mImageViewOnClickListener);
        ImageLoaderUtils.display(getContext(), imageView, url);
        return imageView;
    }

    OnClickListener mImageViewOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, (Integer) v.getTag(R.string.zone_img_position));
            }
        }
    };


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
