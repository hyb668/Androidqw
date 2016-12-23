package com.google.androidqw.ui.task.adapter;

import android.content.Context;

import com.google.androidqw.R;
import com.google.androidqw.ui.task.bean.TaskItem;

import java.util.List;

import unvieradapter.ViewHolderHelper;
import unvieradapter.recyclerview.CommonRecycleViewAdapter;
import utils.FormatUtil;

/**
 * Created by Administrator on 2016/12/23.
 */
public class TaskAadapter extends CommonRecycleViewAdapter<TaskItem> {

    public TaskAadapter(Context context, int layoutId, List<TaskItem> mDatass) {
        super(context, layoutId, mDatass);
    }

    @Override
    public void convert(ViewHolderHelper helper, TaskItem taskItem) {
        String text =  "            任务 : "+taskItem.content+"\n"
                      +"            开始时间 : "+taskItem.createDate+"\n"
                      +"            结束时间 : "+ FormatUtil.checkValue(taskItem.successDate);
        helper.setText(R.id.tv_task_content, text);
    }


}