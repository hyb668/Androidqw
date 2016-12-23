package com.google.androidqw.ui.task.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/12/23.
 */
public class TaskItem implements Parcelable {
    public String uuid;
    public String content;
    public String createDate;
    public String successDate;

    public TaskItem(String uuid, String content, String createDate) {
        this.uuid = uuid;
        this.content = content;
        this.createDate = createDate;
    }

    protected TaskItem(Parcel in) {
        uuid = in.readString();
        content = in.readString();
        createDate = in.readString();
        successDate = in.readString();
    }

    public static final Creator<TaskItem> CREATOR = new Creator<TaskItem>() {
        @Override
        public TaskItem createFromParcel(Parcel in) {
            return new TaskItem(in);
        }

        @Override
        public TaskItem[] newArray(int size) {
            return new TaskItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uuid);
        dest.writeString(content);
        dest.writeString(createDate);
        dest.writeString(successDate);
    }
}
