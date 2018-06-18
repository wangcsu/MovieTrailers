package com.android.gw.movietrailers.Models;

public class Trailer {
    private String name;
    private String key;
    private String type;
    private String site;
    private String url;

    public Trailer(String name, String key, String type, String site) {
        this.name = name;
        this.key = key;
        this.type = type;
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setUrl() {
        this.url = "http://www.youtube.com/watch?v=" + getKey();
    }

    public String getUrl() {
        return url;
    }
}
