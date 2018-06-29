package com.apiclient.constants;

/**
 * Created by logan on 2018/6/28.
 */
public enum ToolBusinessStatusEnum {
    Library("1","备刀库"),
    ForGrinding("2","待刃磨");


    ToolBusinessStatusEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    private String key;
    private String name;

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
