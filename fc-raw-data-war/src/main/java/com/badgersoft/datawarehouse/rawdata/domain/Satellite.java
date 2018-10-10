package com.badgersoft.datawarehouse.rawdata.domain;

public class Satellite {

    private String name;
    private String url;
    private String imageUrl;

    public Satellite() {
    }

    public Satellite(String name, String url, String imageUrl) {
        this.name = name;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
