package com.apiclient.pojo;

/**
 * Created by logan on 2018/5/13.
 */
public enum ScrapStateEnum {
    scrapState001("1","待刃磨"),
    scrapState002("2","待出厂"),
    scrapState003("3","备用刀");

    private String key;
    private String name;

    ScrapStateEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
