package com.apiclient.constants;

/**
 * Created by logan on 2018/5/6.
 */
public enum RFIDEnum {
    //标签类型（1库位标签，2单品刀，3合成刀具，4员工卡，5设备，6容器，7备刀库）
    lib(1,"库位标签"),
    cutting_tool(2,"单品刀"),
    synthesis_cutting_tool(3,"合成刀具"),
    employee(4,"员工卡"),
    equipment(5,"设备"),
    container(6,"容器"),
    prepare_library(7,"备刀库");

    private String name;
    private Integer key;

    RFIDEnum(int key, String name) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
