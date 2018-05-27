package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 PowerHasGroup 
*/
public class PowerHasGroup implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;

    /**
     * @fieldName powerGroupCode
    * @fieldType  Integer
    * @Description  权限组
     */
    private Integer powerGroupCode;
    /**
     * @fieldName powerCode
    * @fieldType  Integer
    * @Description  权限
     */
    private Integer powerCode;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;

    /**
     * @fieldName powerCode
    * @fieldType  
    * @Description  权限
     */
    private Power power;
    /**
     * @fieldName powerGroupCode
    * @fieldType  
    * @Description  权限组
     */
    private PowerGroup powerGroup;



    /* 标识 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPowerGroupCode() {
        return powerGroupCode;
    }

    public void setPowerGroupCode(Integer powerGroupCode) {
        this.powerGroupCode = powerGroupCode;
    }
    public Integer getPowerCode() {
        return powerCode;
    }

    public void setPowerCode(Integer powerCode) {
        this.powerCode = powerCode;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }
    public PowerGroup getPowerGroup() {
        return powerGroup;
    }

    public void setPowerGroup(PowerGroup powerGroup) {
        this.powerGroup = powerGroup;
    }


}
