package com.google.androidqw.ui.news.model;

import com.google.androidqw.api.Api;
import com.google.androidqw.api.HostType;
import com.google.androidqw.bean.NewsDetail;
import com.google.androidqw.ui.news.contract.NewsDetailContract;

import java.util.List;
import java.util.Map;

import baserx.RxSchedulers;
import rx.functions.Func1;

/**
 * Created by lyz on 2017/1/26.
 */
public class NewsDetailModel implements NewsDetailContract.Model {
    @Override
    public rx.Observable<NewsDetail> getNewDetailsDatas(final String postId) {
        return Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getNewDetail(Api.getCacheControl(), postId)
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> map) {
                        NewsDetail newsDetail = map.get(postId);
                        //过滤下
                        parseDatas(newsDetail);
                        return newsDetail;
                    }

                }).compose(RxSchedulers.<NewsDetail>io_main());
    }

    private void parseDatas(NewsDetail detail) {
        List<NewsDetail.ImgBean> imgs = detail.getImg();
        if (isTwo(imgs)) {
            String body = detail.getBody();
            body=change(imgs,body);
            detail.setBody(body);
        }

    }

    /**
     *  把图片替换到对应的html的字符串中
     * @return
     */
    private String change(List<NewsDetail.ImgBean> imgSrcs, String newsBody) {
        for (int i = 0; i < imgSrcs.size(); i++) {
            String oldChars = "<!--IMG#" + i + "-->";
            String newChars;
            if (i == 0) {
                newChars = "";
            } else {
                newChars = "<img src=\"" + imgSrcs.get(i).getSrc() + "\" />";
            }
            newsBody = newsBody.replace(oldChars, newChars);

        }
        return newsBody;
    }

    private boolean isTwo(List<NewsDetail.ImgBean> imgs) {
        return imgs != null && imgs.size() >=2;
    }
}
