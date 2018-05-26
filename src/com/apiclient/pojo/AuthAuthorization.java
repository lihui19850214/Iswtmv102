package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthAuthorization 
*/
public class AuthAuthorization implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  
     */
    private Integer id;

    /**
     * @fieldName reason
    * @fieldType  Integer
    * @Description  授权原因（0断刀,1丢刀,2补领,3到寿,4卸下后安上,5重复换装,6重复复磨,7重复初始化设备,8库存盘点,9出库报废，10其它）
     */
    private Integer reason;
    /**
     * @fieldName authorizedTime
    * @fieldType  Timestamp
    * @Description  授权时间
     */
    private Timestamp authorizedTime;
    /**
     * @fieldName note
    * @fieldType  String
    * @Description  备注（情况简述）
     */
    private String note;
    /**
     * @fieldName customerCode
    * @fieldType  String
    * @Description  用户
     */
    private String customerCode;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  删除区分(0有效1删除)
     */
    private Integer isDel;

    /**
     * @fieldName customerCode
    * @fieldType  
    * @Description  用户
     */
    private AuthCustomer authCustomer;



    /*  */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }
    public Timestamp getAuthorizedTime() {
        return authorizedTime;
    }

    public void setAuthorizedTime(Timestamp authorizedTime) {
        this.authorizedTime = authorizedTime;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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


}
