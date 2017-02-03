package com.google.androidqw.db;

import com.google.androidqw.QwApplication;
import com.google.androidqw.R;
import com.google.androidqw.bean.VideoChannelTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liuyuzhe on 2017/1/31.
 */

public class VideoChannelTableManager {

      public static List<VideoChannelTable> loadVideoChannels(){
          List<String> videoChannelNames = Arrays.asList(QwApplication.getAppContext().getResources().getStringArray(R.array.video_channel_name));
          List<String> videoChannelIds = Arrays.asList(QwApplication.getAppContext().getResources().getStringArray(R.array.video_channel_id));
          List<VideoChannelTable> channelTableList=new ArrayList<>();
          for (int i = 0; i < videoChannelIds.size(); i++) {
              VideoChannelTable table = new VideoChannelTable(videoChannelIds.get(i),videoChannelNames.get(i));
              channelTableList.add(table);
          }
          return channelTableList;
      }
}
