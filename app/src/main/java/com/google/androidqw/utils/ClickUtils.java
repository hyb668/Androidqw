package com.google.androidqw.utils;

import android.os.SystemClock;

import utils.ToastUitl;

/**
 * Created by liuyuzhe on 2017/2/1.
 */

public class ClickUtils {


    private static long lastTime;

    public static boolean isFastDoubleClick() {
        long nowTime = SystemClock.elapsedRealtime();//开机到现在的毫秒数
        if (nowTime - lastTime < 3000) {
            return true;
        } else {
            lastTime = nowTime;
            ToastUitl.show("再按一次退出程序!");
            return false;
        }
    }
}
