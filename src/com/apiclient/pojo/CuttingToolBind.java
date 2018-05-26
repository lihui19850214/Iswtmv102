package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 CuttingToolBind 
*/
public class CuttingToolBind implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;

    /**
     * @fieldName code
    * @fieldType  String
    * @Description  编码
     */
    private String code;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  材料刀编号
     */
    private String cuttingToolCode;
    /**
     * @fieldName rfidContainerCode
    * @fieldType  String
    * @Description  RIFD编码
     */
    private String rfidContainerCode;
    /**
     * @fieldName bladeCode
    * @fieldType  String
    * @Description  刀身码
     */
    private String bladeCode;
    /**
     * @fieldName rfidCode
    * @fieldType  String
    * @Description  
     */
    private String rfidCode;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  材料刀编号
     */
    private CuttingTool cuttingTool;
    /**
     * @fieldName rfidContainerCode
    * @fieldType  
    * @Description  RIFD编码
     */
    private RfidContainer rfidContainer;

    /**
      * @fieldName isDel
     * @fieldType  Integer
     * @Description
     */
    private Integer isDel;

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /* id */
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
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public String getRfidContainerCode() {
        return rfidContainerCode;
    }

    public void setRfidContainerCode(String rfidContainerCode) {
        this.rfidContainerCode = rfidContainerCode;
    }
    public String getBladeCode() {
        return bladeCode;
    }

    public void setBladeCode(String bladeCode) {
        this.bladeCode = bladeCode;
    }
    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public CuttingTool getCuttingTool() {
        return cuttingTool;
    }

    public void setCuttingTool(CuttingTool cuttingTool) {
        this.cuttingTool = cuttingTool;
    }
    public RfidContainer getRfidContainer() {
        return rfidContainer;
    }

    public void setRfidContainer(RfidContainer rfidContainer) {
        this.rfidContainer = rfidContainer;
    }


}
