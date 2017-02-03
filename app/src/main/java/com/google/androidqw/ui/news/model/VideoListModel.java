package com.google.androidqw.ui.news.model;

import com.google.androidqw.api.Api;
import com.google.androidqw.api.HostType;
import com.google.androidqw.bean.VideoData;
import com.google.androidqw.ui.news.contract.VideoListContract;

import java.util.List;
import java.util.Map;

import baserx.RxSchedulers;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import utils.TimeUtil;

/**
 * Created by liuyuzhe on 2017/2/1.
 */

public class VideoListModel implements VideoListContract.Model {

    @Override
    public Observable<List<VideoData>> loadVideoListDatas(final String type, int startPage) {

        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getVideoList(Api.getCacheControl(), type, startPage)
                .flatMap(new Func1<Map<String, List<VideoData>>, Observable<VideoData>>() {
                    @Override
                    public Observable<VideoData> call(Map<String, List<VideoData>> map) {


                        return Observable.from(map.get(type));
                    }
                }).map(new Func1<VideoData, VideoData>() {
                    @Override
                    public VideoData call(VideoData videoData) {
                        String videoDataPtime = videoData.getPtime();
                        String ptime = TimeUtil.formatDate(videoDataPtime);
                        videoData.setPtime(ptime);
                        return videoData;
                    }
                }).distinct()
                .toSortedList(new Func2<VideoData, VideoData, Integer>() {
                    @Override
                    public Integer call(VideoData videoData, VideoData videoData2) {
                        return videoData.getPtime().compareTo(videoData2.getPtime());
                    }
                }).compose(RxSchedulers.<List<VideoData>>io_main());


    }


}