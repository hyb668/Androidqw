package com.google.androidqw.ui.task.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/23.
 */
public class TaskItem implements Serializable {
    public String uuid;
    public String content;
    public String createDate;
    public String successDate;

    public TaskItem(String uuid, String content, String createDate) {
        this.uuid = uuid;
        this.content = content;
        this.createDate = createDate;
    }

    public TaskItem(String uuid, String content, String createDate, String successDate) {
        this.uuid = uuid;
        this.content = content;
        this.createDate = createDate;
        this.successDate = successDate;
    }

    public TaskItem() {
    }
}
