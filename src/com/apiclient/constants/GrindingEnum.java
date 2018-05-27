package com.apiclient.constants;

/**
 * Created by logan on 2018/5/12.
 */
public enum GrindingEnum {
    inside("1","厂内刃磨"),outside("2","场外刃磨"),outside_tuceng("3","场外涂层");

    private String key;

    private String name;

    GrindingEnum(String key, String name) {
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
