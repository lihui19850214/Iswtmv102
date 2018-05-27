package com.apiclient.constants;

/**
 * Created by logan on 2018/5/26.
 */
public enum  PowerTypeEnum {
    operation_type("1","1","可操作权限"),
    data_type("2","2","数据权限"),
    flow_type("3","3","流程权限"),
    menu_type("4","4","菜单权限"),
    root("5","1","根权限");

    private String key;
    private String typeKey;
    private String name;

    PowerTypeEnum(String key, String typeKey, String name) {
        this.key = key;
        this.typeKey = typeKey;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
