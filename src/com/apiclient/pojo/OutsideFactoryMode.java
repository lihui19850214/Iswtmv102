package com.apiclient.pojo;

/**
 * Created by logan on 2018/5/13.
 */
public enum OutsideFactoryMode {
    reason001("1","换片"),
    reason002("2","刃磨"),
    reason003("3","涂层"),
    reason004("4","粘块"),
    reason005("5","钝化涂层"),
    reason006("6","刃磨涂层"),
    reason007("7","规圆");

    private String key;
    private String name;

    OutsideFactoryMode(String key, String name) {
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
