package com.google.androidqw.db;

import com.google.androidqw.R;
import com.google.androidqw.api.ApiConstants;
import com.google.androidqw.bean.NewsChannelTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.BaseApplication;

public class NewsChannelTableManager {

    /**
     * 加载新闻类型
     * @return
     */
    public static List<NewsChannelTable> loadNewsChannelsMine() {
        List<String> channelName = Arrays.asList(BaseApplication.getAppContext().getResources().getStringArray(R.array.news_channel_name));
        List<String> channelId = Arrays.asList(BaseApplication.getAppContext().getResources().getStringArray(R.array.news_channel_id));
        ArrayList<NewsChannelTable>newsChannelTables=new ArrayList<>();
        for (int i = 0; i < channelName.size(); i++) {
            NewsChannelTable entity = new NewsChannelTable(channelName.get(i), channelId.get(i)
                    , ApiConstants.getType(channelId.get(i)), i <= 5, i, false);
            newsChannelTables.add(entity);
        }
        return newsChannelTables;
    }
    /**
     * 加载固定新闻类型
     * @return
     */
    public static List<NewsChannelTable> loadNewsChannelsStatic() {
        List<String> channelName = Arrays.asList(BaseApplication.getAppContext().getResources().getStringArray(R.array.news_channel_name_static));
        List<String> channelId = Arrays.asList(BaseApplication.getAppContext().getResources().getStringArray(R.array.news_channel_id_static));
        ArrayList<NewsChannelTable>newsChannelTables=new ArrayList<>();
        for (int i = 0; i < channelName.size(); i++) {
            NewsChannelTable entity = new NewsChannelTable(channelName.get(i), channelId.get(i)
                    , ApiConstants.getType(channelId.get(i)), i <= 5, i, true);
            newsChannelTables.add(entity);
        }
        return newsChannelTables;
    }
}
