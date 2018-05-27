package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 DjLineAkp 
*/
public class DjLineAkp implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName lineCode
    * @fieldType  String
    * @Description  生产线代码
     */
    private String lineCode;

    /**
     * @fieldName lineName
    * @fieldType  String
    * @Description  生产线名称
     */
    private String lineName;




    /* 生产线代码 */
    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }



}
