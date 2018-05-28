package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 ImpowerRecorder 
*/
public class ImpowerRecorder implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  授权记录标识
     */
    private Integer id;

    /**
     * @fieldName toolCode
    * @fieldType  String
    * @Description  刀具号
     */
    private String toolCode;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  创建时间
     */
    private Timestamp createTime;
    /**
     * @fieldName impowerUser
    * @fieldType  String
    * @Description  授权人code
     */
    private String impowerUser;
    /**
     * @fieldName impowerUserName
    * @fieldType  String
    * @Description  授权人名称
     */
    private String impowerUserName;
    /**
     * @fieldName reasonKey
    * @fieldType  String
    * @Description  授权原因
     */
    private String reasonKey;
    /**
     * @fieldName reasonValue
    * @fieldType  String
    * @Description  授权原因文本
     */
    private String reasonValue;
    /**
     * @fieldName currentStatusKey
    * @fieldType  String
    * @Description  刀具状态
     */
    private String currentStatusKey;
    /**
     * @fieldName currentStatusName
    * @fieldType  String
    * @Description  当前状态文本
     */
    private String currentStatusName;
    /**
     * @fieldName operatorKey
    * @fieldType  String
    * @Description  操作key
     */
    private String operatorKey;
    /**
     * @fieldName operatorValue
    * @fieldType  String
    * @Description  操作文本
     */
    private String operatorValue;
    /**
     * @fieldName operatorUserCode
    * @fieldType  String
    * @Description  操作者code
     */
    private String operatorUserCode;
    /**
     * @fieldName operatorUserName
    * @fieldType  String
    * @Description  操作者姓名
     */
    private String operatorUserName;
    /**
     * @fieldName resume
    * @fieldType  String
    * @Description  简述
     */
    private String resume;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName rfidLasercode
     * @fieldType  String
     * @Description  RFID 标签
     */
    private String rfidLasercode;


    /* 授权记录标识 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    public String getImpowerUser() {
        return impowerUser;
    }

    public void setImpowerUser(String impowerUser) {
        this.impowerUser = impowerUser;
    }
    public String getImpowerUserName() {
        return impowerUserName;
    }

    public void setImpowerUserName(String impowerUserName) {
        this.impowerUserName = impowerUserName;
    }
    public String getReasonKey() {
        return reasonKey;
    }

    public void setReasonKey(String reasonKey) {
        this.reasonKey = reasonKey;
    }
    public String getReasonValue() {
        return reasonValue;
    }

    public void setReasonValue(String reasonValue) {
        this.reasonValue = reasonValue;
    }
    public String getCurrentStatusKey() {
        return currentStatusKey;
    }

    public void setCurrentStatusKey(String currentStatusKey) {
        this.currentStatusKey = currentStatusKey;
    }
    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }
    public String getOperatorKey() {
        return operatorKey;
    }

    public void setOperatorKey(String operatorKey) {
        this.operatorKey = operatorKey;
    }
    public String getOperatorValue() {
        return operatorValue;
    }

    public void setOperatorValue(String operatorValue) {
        this.operatorValue = operatorValue;
    }
    public String getOperatorUserCode() {
        return operatorUserCode;
    }

    public void setOperatorUserCode(String operatorUserCode) {
        this.operatorUserCode = operatorUserCode;
    }
    public String getOperatorUserName() {
        return operatorUserName;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }
    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getRfidLasercode() {
        return rfidLasercode;
    }

    public void setRfidLasercode(String rfidLasercode) {
        this.rfidLasercode = rfidLasercode;
    }

}
