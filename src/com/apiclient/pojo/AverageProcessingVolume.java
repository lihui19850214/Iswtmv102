package com.apiclient.pojo;

/**
 * Created by logan on 2018/5/13.
 */
public enum AverageProcessingVolume {
    Installed("1500","1500"),UnInstall("3000","3000");

    private String key;
    private String name;

    AverageProcessingVolume(String key, String name) {
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
