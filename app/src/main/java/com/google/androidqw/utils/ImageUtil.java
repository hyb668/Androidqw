package com.google.androidqw.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.google.androidqw.R;

import java.io.File;


/**
 * des:
 * Created by xsf
 * on 2016.04.10:36
 */
public class ImageUtil {
    private static String BASE_PHOTO_URL = "";

    /**
     * @param url
     * @return
     */
    public static String getImageUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("http") || new File(url).isFile()) {
                return url;
            } else {
                return BASE_PHOTO_URL + url;
            }
        } else {
            return "";
        }
    }


    public static void setViewToVISIBLE(View... args) {
        for (View view : args) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void setViewToGONE(View... args) {
        for (View view : args) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * @return
     */
    public static SpannableStringBuilder setTextViewGreenSpannable(Context context, String strContent) {
        SpannableStringBuilder style = new SpannableStringBuilder(strContent);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.color_fd9405));
        style.setSpan(colorSpan,
                0, strContent.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }


}

