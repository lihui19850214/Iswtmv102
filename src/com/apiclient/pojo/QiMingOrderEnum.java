package com.apiclient.pojo;

/**
 * Created by logan on 2018/5/11.
 */
public enum  QiMingOrderEnum {

    newOrder("0","新建"),
    approved("1","已审核"),
    outed("2","已出库"),
    received("3","已收货"),
    singled("4","已制签"),
    approve_error("5","审核失败"),
    revocation("6","撤销");



    private String key;
    private String name;

    QiMingOrderEnum(String key, String name) {
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
