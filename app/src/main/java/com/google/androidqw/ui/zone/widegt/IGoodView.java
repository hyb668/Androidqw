package com.google.androidqw.ui.zone.widegt;

import com.google.androidqw.R;

/**
 * Created by lyz on 2017/1/25.
 */
public interface IGoodView {
    //移动距离
    int DISTANCE = 60;
    //y轴偏移量
    int FROM_Y_OFFSET = 0;
    //y移动的最终偏移量
    int TO_Y_OFFSET = DISTANCE;
    //起始透明度
    float FROM_ALPH = 1.0f;
    //结束透明度
    float TO_ALPH = 0.0f;
    //动画时长
    int DURATION = 800;
    //默认文本
    String TEXT = "";

    int TEXT_SIZE = 16; // 默认文本字体大小

    int TEXT_COLOR = R.color.black;   // 默认文本字体颜色
}
