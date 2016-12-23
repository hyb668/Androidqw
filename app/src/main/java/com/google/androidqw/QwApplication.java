package com.google.androidqw;

import app.BaseApplication;
import utils.LogUtils;

/**
 * Created by Administrator on 2016/12/23.
 */
public class QwApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtils.logInit(com.example.common.BuildConfig.LOG_DEBUG);
    }
}
