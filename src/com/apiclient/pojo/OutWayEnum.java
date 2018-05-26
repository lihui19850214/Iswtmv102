package com.apiclient.pojo;

/**
 * Created by logan on 2018/5/12.
 */
public enum OutWayEnum {

    tuceng("1","涂层"),
    renmo("2","刃磨"),
    换片("3","换片"),
    niankuai("4","粘块"),
    dunhuatuceng("5","钝化涂层"),
    renmotuceng("6","刃磨涂层"),
    guiyuan("7","规原");


    private String key;
    private String name;

    OutWayEnum(String key, String name) {
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
