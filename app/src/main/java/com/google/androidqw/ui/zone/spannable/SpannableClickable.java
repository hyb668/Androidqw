package com.google.androidqw.ui.zone.spannable;


import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.google.androidqw.R;

import app.BaseApplication;


/**
 * @author yiw
 * @Description:
 * @date 16/1/2 16:32
 */
public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {
    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        //接收者 , 发送者走false
        int color = BaseApplication.getAppContext().getResources().getColor((R.color.main_color));
        ds.setColor(color);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
