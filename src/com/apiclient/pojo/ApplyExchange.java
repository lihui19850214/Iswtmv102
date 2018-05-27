package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 ApplyExchange 
*/
public class ApplyExchange implements Serializable{


    ///
    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  换领申请ID
     */
    private Integer id;

    /**
     * @fieldName code
    * @fieldType  String
    * @Description  换领申请流水号
     */
    private String code;
    /**
     * @fieldName count
    * @fieldType  Integer
    * @Description  数量
     */
    private Integer count;
    /**
     * @fieldName quantity
    * @fieldType  Integer
    * @Description  备货数量
     */
    private Integer quantity;
    /**
     * @fieldName receiveCount
    * @fieldType  Integer
    * @Description  送回数量
     */
    private Integer receiveCount;
    /**
     * @fieldName brokenCount
    * @fieldType  Integer
    * @Description  坏刀数量
     */
    private Integer brokenCount;
    /**
     * @fieldName lostCount
    * @fieldType  Integer
    * @Description  丢失数量
     */
    private Integer lostCount;
    /**
     * @fieldName lifeOverNumber
    * @fieldType  Integer
    * @Description  到使用寿命数量
     */
    private Integer lifeOverNumber;
    /**
     * @fieldName applyUser
    * @fieldType  String
    * @Description  申请人
     */
    private String applyUser;
    /**
     * @fieldName applyTime
    * @fieldType  Timestamp
    * @Description  申请时间
     */
    private Timestamp applyTime;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName applyUserName
    * @fieldType  String
    * @Description  申请人名称
     */
    private String applyUserName;




    /* 换领申请ID */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(Integer receiveCount) {
        this.receiveCount = receiveCount;
    }
    public Integer getBrokenCount() {
        return brokenCount;
    }

    public void setBrokenCount(Integer brokenCount) {
        this.brokenCount = brokenCount;
    }
    public Integer getLostCount() {
        return lostCount;
    }

    public void setLostCount(Integer lostCount) {
        this.lostCount = lostCount;
    }
    public Integer getLifeOverNumber() {
        return lifeOverNumber;
    }

    public void setLifeOverNumber(Integer lifeOverNumber) {
        this.lifeOverNumber = lifeOverNumber;
    }
    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }
    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }



}
