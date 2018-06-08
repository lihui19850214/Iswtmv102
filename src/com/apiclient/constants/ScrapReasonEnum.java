package com.apiclient.constants;

/**
 * Created by logan on 2018/5/13.
 */
public enum ScrapReasonEnum {
    scrapReason001("1","断刀"),
    scrapReason002("2","丢刀"),
    scrapReason003("3","加工尺寸不满足");

    private String key;
    private String name;

    ScrapReasonEnum(String key, String name) {
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
