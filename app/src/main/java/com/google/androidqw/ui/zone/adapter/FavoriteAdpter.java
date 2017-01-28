package com.google.androidqw.ui.zone.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.View;

import com.google.androidqw.R;
import com.google.androidqw.ui.zone.bean.FavortItem;
import com.google.androidqw.ui.zone.spannable.CircleMovementMethod;
import com.google.androidqw.ui.zone.spannable.SpannableClickable;
import com.google.androidqw.ui.zone.widegt.FavoriteTextView;

import java.util.List;

import utils.CollectionUtils;
import utils.ToastUitl;

/**
 * des:点赞适配器
 * Created by lyz on 2016/12/27.
 */
public class FavoriteAdpter {

    private FavoriteTextView favoriteTextView;
    private Context mContext;
    List<FavortItem> mDatas;

    public FavoriteAdpter(Context context) {
        mContext = context;
    }

    public void bindListView(FavoriteTextView favoriteTextView) {
        if (null == favoriteTextView) {
            throw new IllegalArgumentException("FavoriteTextView ........ is null ");
        }
        this.favoriteTextView = favoriteTextView;
    }

    public void setDatas(List list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    public void resetDatas() {
        mDatas = null;
        notifyDataSetChanged();
    }


    public void notifyDataSetChanged() {
        if (favoriteTextView == null) {
            throw new NullPointerException("FavoriteTextView is null , bindListView please!");
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (!CollectionUtils.isNullOrEmpty(mDatas)) {
            //添加图片

            builder.append(getImgSpan());
            for (int i = 0; i < mDatas.size(); i++) {
                builder.append(" " + getClickableSpan(mDatas.get(i).getUserNickname(), i));

                if (i != mDatas.size() - 1) {
                    builder.append("、");
                }
            }

            favoriteTextView.setText(builder);
            favoriteTextView.setMovementMethod(new CircleMovementMethod(R.color.circle_name_selector_color));
        }
    }

    private SpannableString getClickableSpan(final String userNickname, final int position) {
        SpannableString spannableString = new SpannableString(userNickname);

        spannableString.setSpan(new SpannableClickable() {
            @Override
            public void onClick(View widget) {
                ToastUitl.show("点赞名字 = " + userNickname + " &position = " + position);
            }
        }, 0, userNickname.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    private SpannableString getImgSpan() {
        String text = " ";
        SpannableString imgSpanStr = new SpannableString(text);

        imgSpanStr.setSpan(new ImageSpan(mContext, R.drawable.zan, DynamicDrawableSpan.ALIGN_BASELINE),
                0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanStr;
    }


}
