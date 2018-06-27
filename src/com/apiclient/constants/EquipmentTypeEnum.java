package com.apiclient.constants;

/**
 * Created by logan on 2018/6/13.
 */
public enum EquipmentTypeEnum {
    Processing(1,"加工设备"),
    Grinding(2,"刃磨设备");

    EquipmentTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    private Integer key;
    private String name;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
