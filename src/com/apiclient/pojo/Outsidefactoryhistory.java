package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 Outsidefactoryhistory 
*/
public class Outsidefactoryhistory implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  厂外修复履历ID
     */
    private Integer id;

    /**
     * @fieldName orderNum
    * @fieldType  String
    * @Description  通知单号
     */
    private String orderNum;
    /**
     * @fieldName outNum
    * @fieldType  String
    * @Description  出门单号
     */
    private String outNum;
    /**
     * @fieldName sharpenProviderCode
    * @fieldType  String
    * @Description  商家ID
     */
    private String sharpenProviderCode;
    /**
     * @fieldName materialNum
    * @fieldType  String
    * @Description  材料号
     */
    private String materialNum;
    /**
     * @fieldName backFactoryNumber
    * @fieldType  Integer
    * @Description  回厂数量
     */
    private Integer backFactoryNumber;
    /**
     * @fieldName createUser
    * @fieldType  String
    * @Description  创建人
     */
    private String createUser;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  创建时间
     */
    private Timestamp createTime;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  
     */
    private String cuttingToolCode;

    /**
     * @fieldName sharpenProviderCode
    * @fieldType  
    * @Description  商家ID
     */
    private SharpenProvider sharpenProvider;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  
     */
    private CuttingTool cuttingTool;



    /* 厂外修复履历ID */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    public String getOutNum() {
        return outNum;
    }

    public void setOutNum(String outNum) {
        this.outNum = outNum;
    }
    public String getSharpenProviderCode() {
        return sharpenProviderCode;
    }

    public void setSharpenProviderCode(String sharpenProviderCode) {
        this.sharpenProviderCode = sharpenProviderCode;
    }
    public String getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(String materialNum) {
        this.materialNum = materialNum;
    }
    public Integer getBackFactoryNumber() {
        return backFactoryNumber;
    }

    public void setBackFactoryNumber(Integer backFactoryNumber) {
        this.backFactoryNumber = backFactoryNumber;
    }
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }

    public SharpenProvider getSharpenProvider() {
        return sharpenProvider;
    }

    public void setSharpenProvider(SharpenProvider sharpenProvider) {
        this.sharpenProvider = sharpenProvider;
    }
    public CuttingTool getCuttingTool() {
        return cuttingTool;
    }

    public void setCuttingTool(CuttingTool cuttingTool) {
        this.cuttingTool = cuttingTool;
    }


}
