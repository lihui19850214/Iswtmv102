package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 DjOwnerAkp 
*/
public class DjOwnerAkp implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName ownerCode
    * @fieldType  String
    * @Description  供应商代码
     */
    private String ownerCode;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  供应商名称
     */
    private String name;




    /* 供应商代码 */
    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
