/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.google.androidqw.bean;

import android.databinding.ObservableField;

import java.io.Serializable;
import java.util.List;

public class NewsPhotoDetail implements Serializable {
    private String title;
    private List<Picture> pictures;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }



    public static class Picture  implements Serializable {
        public ObservableField<String> title=new ObservableField<>();
        public ObservableField<String> imgSrc=new ObservableField<>();

        public Picture(String titleStr,String imgSrcStr){
            title.set(titleStr);
            imgSrc.set(imgSrcStr);
        }
    }



    public NewsPhotoDetail() {
    }


}
