package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 PowerUserHasGroup 
*/
public class PowerUserHasGroup implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;

    /**
     * @fieldName authCustomerCode
    * @fieldType  String
    * @Description  
     */
    private String authCustomerCode;
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
     * @fieldName authCustomerCode
    * @fieldType  
    * @Description  
     */
    private AuthCustomer authCustomer;
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

    public String getAuthCustomerCode() {
        return authCustomerCode;
    }

    public void setAuthCustomerCode(String authCustomerCode) {
        this.authCustomerCode = authCustomerCode;
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

    public AuthCustomer getAuthCustomer() {
        return authCustomer;
    }

    public void setAuthCustomer(AuthCustomer authCustomer) {
        this.authCustomer = authCustomer;
    }
    public PowerGroup getPowerGroup() {
        return powerGroup;
    }

    public void setPowerGroup(PowerGroup powerGroup) {
        this.powerGroup = powerGroup;
    }


}
