package com.google.androidqw.ui.task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.androidqw.R;
import com.google.androidqw.ui.task.adapter.TaskAadapter;
import com.google.androidqw.ui.task.bean.TaskItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import base.BaseActivity;
import butterknife.Bind;
import utils.ACache;
import utils.CollectionUtils;
import utils.TimeUtil;
import view.NormarlTitle;

/**
 * 任务列表
 */
public class TaskActivity extends BaseActivity {


    @Bind(R.id.layout_recycleView)
    RecyclerView mLayoutRecycleView;
    @Bind(R.id.titlt_task_tab)
    NormarlTitle mTitltTaskTab;

    public static void start(Context context) {
        Intent intent = new Intent(context, TaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_task;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mTitltTaskTab.setTitleText("任务列表");
        mLayoutRecycleView.setLayoutManager(new LinearLayoutManager(this));
        initdata();
    }

    /***
     * 每次上来都从本地去取数据,如果取到了2个就把2个条目的数据给上, 第三个就是new新的对象然后存入本地,下次直接取出;
     */
    private void initdata() {
        // TODO: 2016/12/23  完成任务时怎么处理
        List<TaskItem> datas = new ArrayList();
        datas.add(new TaskItem(UUID.randomUUID().toString(), "多渠道打包", TimeUtil.getCurrentDay()));
        List<TaskItem> taskItems = (List<TaskItem>) ACache.get(this, "taskCache").getAsObject("taskDatasKey");
        if (!CollectionUtils.isNullOrEmpty(taskItems)) {
//            3                2;
            if (datas.size() > taskItems.size()) {
                //有新的任务,取出最后一个新的任务给本地数据
                for (int i = datas.size() - 1; i >= 0; i--) {
                    taskItems.add(datas.get(i));
                    if (taskItems.size() == datas.size())
                        break;
                }
            }
        } else {
            taskItems = datas;
        }
        ACache.get(this, "taskCache").put("taskDatasKey", (Serializable) taskItems);
        mLayoutRecycleView.setAdapter(new TaskAadapter(this, R.layout.item_task, taskItems));
    }


}
