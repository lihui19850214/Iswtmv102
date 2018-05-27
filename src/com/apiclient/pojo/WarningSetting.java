package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 WarningSetting 
*/
public class WarningSetting implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  名称
     */
    private String name;




    /* 标识 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
