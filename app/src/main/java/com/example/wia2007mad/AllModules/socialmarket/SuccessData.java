package com.example.wia2007mad.AllModules.socialmarket;

import java.io.Serializable;

public class SuccessData implements Serializable {

    public String successtext;
    public String imageUrl;

    public String url;
    public String name;
    public String storyDesc;

    public SuccessData() {
    }

    public SuccessData(String successtext, String imageUrl,String url,String name,String storyDesc) {
        this.successtext = successtext;
        this.imageUrl = imageUrl;
        this.url = url;
        this.name = name;
        this.storyDesc = storyDesc;
    }

    public String getSuccesstext() {
        return successtext;
    }

    public void setSuccesstext(String successtext) {
        this.successtext = successtext;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoryDesc() {
        return storyDesc;
    }

    public void setStoryDesc(String storyDesc) {
        this.storyDesc = storyDesc;
    }

    @Override
    public String toString() {
        return "SuccessData{" +
                "successtext='" + successtext + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", storyDesc='" + storyDesc + '\'' +
                '}';
    }
}
