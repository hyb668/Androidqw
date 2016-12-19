package com.google.androidqw;

import android.widget.Button;

import com.google.androidqw.utils.BaseModel;
import com.google.androidqw.utils.ResultBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.File;

import app.BaseApplication;
import base.BaseActivity;
import baserx.RxSchedulers;
import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import utils.LogUtils;

/**
 * ============================================================
 * <p/>
 * 版 权 ： 刘宇哲 版权所有 (c) 2015
 * <p/>
 * 作 者 : 刘宇哲
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ：  on 2016/11/16.
 * <p/>
 * 描 述 ：
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class retrofitTest extends BaseActivity  {

    @Bind(R.id.btn_object)
    Button mBtnObject;

    @Override
    public int getLayoutId() {
        return R.layout.activity_retrofit;
    }

    @Override
    public void initPresenter() {

    }


    @OnClick(R.id.btn_object)
    public void onclick() {
        String url = "http://api.123shishi.com/thingsapp/attendance/getAttendanceInfo?endDate=2016-12-13&userUuid=32ae9e79-b12a-4286-a353-c00dde062441" +
                "&companyUuid=267e0131-a881-42e7-8ac0-72d81447510f&startDate=2016-12-13&longitude=116.450726";
        //开启log
    }

    @Override
    public void initView() {
        String url = "http://api.123shishi.com/thingsapp/attendance/getAttendanceInfo?endDate=2016-12-13&userUuid=32ae9e79-b12a-4286-a353-c00dde062441" +
                "&companyUuid=267e0131-a881-42e7-8ac0-72d81447510f&startDate=2016-12-13&longitude=116.450726";
        //开启log
        //缓存
        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        Retrofit build = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://c.m.163.com/")
                .build();

        APiBean service = build.create(APiBean.class);
        //走下面代码蹦了,需要在这里把类型给他
//        APiBean<SignMol> s = service;
//        APiBean<ResultBean> s = service;


//          APiBean<ResultBean> aPiBean = service;

        Observable<BaseModel> observable = service.getRequestURL(url);


        observable
                .compose(RxSchedulers.<BaseModel>io_main())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.logd("NewsFragment.onError." + e.getMessage());
                    }
                    @Override
                    public void onNext(BaseModel summaries) {
                       ResultBean summaries1= (ResultBean) summaries;
                        Logger.json("" + summaries.result.toString());
                        Logger.json("" + summaries1.getPunchInfo());
//                        for (SignMol.ResultBean.PunchInfoBean punchInfoBean : summaries.getResult().getPunchInfo()
//                                ) {
//                            Logger.d("" + punchInfoBean.getUuid());
//                        }
                    }
                });

    }


}
