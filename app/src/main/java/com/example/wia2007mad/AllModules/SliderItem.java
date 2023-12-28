package com.example.wia2007mad.AllModules;

public class SliderItem {

    private String url;

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public SliderItem(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return this.description;
    }
}
