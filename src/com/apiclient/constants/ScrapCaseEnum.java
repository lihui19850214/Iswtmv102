package com.apiclient.constants;

/**
 * Created by logan on 2018/6/28.
 */
public enum ScrapCaseEnum {
    Case_1("1","丢刀"),
    Case_2("2","断刀"),
    Case_3("3","加工尺寸不满足");

    ScrapCaseEnum(String key, String name) {
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
