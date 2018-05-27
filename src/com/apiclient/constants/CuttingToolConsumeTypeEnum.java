package com.apiclient.constants;

/**
 * Created by logan on 2018/5/24.
 */
public enum CuttingToolConsumeTypeEnum {

    griding_zt("1","可刃磨钻头"),griding_dp("2","可刃磨刀片"),single_use_dp("3","一次性刀片"),other("9","其他");

    private String key;

    private String name;

    CuttingToolConsumeTypeEnum(String key, String name) {
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
