package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 CuttingToolsScrap 
*/
public class CuttingToolsScrap implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  报废id
     */
    private Integer id;

    /**
     * @fieldName toolType
    * @fieldType  String
    * @Description  刀具类型（0:可刃磨钻头1可刃磨刀片2一次性刀片9其他）
     */
    private String toolType;
    /**
     * @fieldName materialNum
    * @fieldType  String
    * @Description  材料号
     */
    private String materialNum;
    /**
     * @fieldName cause
    * @fieldType  String
    * @Description  报废原因 1丢刀 2断刀 3刀寿 9 其他
     */
    private String cause;
    /**
     * @fieldName count
    * @fieldType  Integer
    * @Description  报废数量
     */
    private Integer count;
    /**
     * @fieldName time
    * @fieldType  Timestamp
    * @Description  报废时间
     */
    private Timestamp time;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName customerCode
    * @fieldType  String
    * @Description  操作人
     */
    private String customerCode;
    /**
     * @fieldName custormerName
    * @fieldType  String
    * @Description  操作人姓名
     */
    private String custormerName;
    /**
     * @fieldName authorizedCode
    * @fieldType  String
    * @Description  授权人
     */
    private String authorizedCode;
    /**
     * @fieldName authorizedName
    * @fieldType  String
    * @Description  授权人姓名
     */
    private String authorizedName;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  
     */
    private String cuttingToolCode;
    /**
     * @fieldName status
    * @fieldType  String
    * @Description  报废状态 1待刃磨 2待出厂 3备用刀
     */
    private String status;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  
     */
    private CuttingTool cuttingTool;



    /* 报废id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }
    public String getMaterialNum() {
        return materialNum;
    }

    public void setMaterialNum(String materialNum) {
        this.materialNum = materialNum;
    }
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    public String getCustormerName() {
        return custormerName;
    }

    public void setCustormerName(String custormerName) {
        this.custormerName = custormerName;
    }
    public String getAuthorizedCode() {
        return authorizedCode;
    }

    public void setAuthorizedCode(String authorizedCode) {
        this.authorizedCode = authorizedCode;
    }
    public String getAuthorizedName() {
        return authorizedName;
    }

    public void setAuthorizedName(String authorizedName) {
        this.authorizedName = authorizedName;
    }
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CuttingTool getCuttingTool() {
        return cuttingTool;
    }

    public void setCuttingTool(CuttingTool cuttingTool) {
        this.cuttingTool = cuttingTool;
    }


}
