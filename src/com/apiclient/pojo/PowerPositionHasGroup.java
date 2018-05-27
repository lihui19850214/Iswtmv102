package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 PowerPositionHasGroup 
*/
public class PowerPositionHasGroup implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;

    /**
     * @fieldName authPositionCode
    * @fieldType  Integer
    * @Description  
     */
    private Integer authPositionCode;
    /**
     * @fieldName powerGroupCode
    * @fieldType  Integer
    * @Description  
     */
    private Integer powerGroupCode;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;

    /**
     * @fieldName authPositionCode
    * @fieldType  
    * @Description  
     */
    private AuthPosition authPosition;
    /**
     * @fieldName powerGroupCode
    * @fieldType  
    * @Description  
     */
    private PowerGroup powerGroup;



    /* 标识 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthPositionCode() {
        return authPositionCode;
    }

    public void setAuthPositionCode(Integer authPositionCode) {
        this.authPositionCode = authPositionCode;
    }
    public Integer getPowerGroupCode() {
        return powerGroupCode;
    }

    public void setPowerGroupCode(Integer powerGroupCode) {
        this.powerGroupCode = powerGroupCode;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public AuthPosition getAuthPosition() {
        return authPosition;
    }

    public void setAuthPosition(AuthPosition authPosition) {
        this.authPosition = authPosition;
    }
    public PowerGroup getPowerGroup() {
        return powerGroup;
    }

    public void setPowerGroup(PowerGroup powerGroup) {
        this.powerGroup = powerGroup;
    }


}
