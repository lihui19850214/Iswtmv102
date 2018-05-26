package com.apiclient.pojo;

/**
 * Created by logan on 2018/5/11.
 * 刀具消耗类型
 */
public enum  ConsumeTypeEnum {

    Applicable_Bit("1","可刃磨钻头"),
    Applicable_Cutter_Blade("2","可刃磨刀片"),
    Single_Use_Cutter_Blade("3","一次性刀片"),
    Others("9","其他");

    private String key;
    private String name;

    ConsumeTypeEnum(String key, String name) {
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
