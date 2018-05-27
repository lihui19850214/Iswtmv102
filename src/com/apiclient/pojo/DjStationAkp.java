package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 DjStationAkp 
*/
public class DjStationAkp implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName stationCode
    * @fieldType  String
    * @Description  工序代码
     */
    private String stationCode;

    /**
     * @fieldName stationName
    * @fieldType  String
    * @Description  工序名称
     */
    private String stationName;
    /**
     * @fieldName lineCode
    * @fieldType  String
    * @Description  生产线代码
     */
    private String lineCode;




    /* 工序代码 */
    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }



}
