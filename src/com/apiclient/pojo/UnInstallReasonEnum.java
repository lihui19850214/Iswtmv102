package com.apiclient.pojo;

/**
 * Created by logan on 2018/5/13.
 */
public enum  UnInstallReasonEnum {
    reason001("1","正常卸下"),
    reason002("2","机床原因"),
    reason003("3","表面质量不满足"),
    reason004("4","加工尺寸不满足"),
    reason005("5","打刀"),
    reason007("9","其他");

    private String key;
    private String name;

    UnInstallReasonEnum(String key, String name) {
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
