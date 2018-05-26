package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 OutsideFactory 
*/
public class OutsideFactory implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  场外复磨id
     */
    private Integer id;

    /**
     * @fieldName orderNum
    * @fieldType  String
    * @Description  通知单号
     */
    private String orderNum;
    /**
     * @fieldName sharpenProviderCode
    * @fieldType  String
    * @Description  
     */
    private String sharpenProviderCode;
    /**
     * @fieldName materialNum
    * @fieldType  String
    * @Description  材料号
     */
    private String materialNum;
    /**
     * @fieldName numberGrinding
    * @fieldType  Integer
    * @Description  复磨数量
     */
    private Integer numberGrinding;
    /**
     * @fieldName receiveNumber
    * @fieldType  Integer
    * @Description  
     */
    private Integer receiveNumber;
    /**
     * @fieldName grindingType
    * @fieldType  String
    * @Description  修复类型（0.厂外图层1.厂外复磨）
     */
    private String grindingType;
    /**
     * @fieldName laserCode
    * @fieldType  String
    * @Description  激光码
     */
    private String laserCode;
    /**
     * @fieldName toolType
    * @fieldType  String
    * @Description  刀具类型（0.钻头1.刀片）
     */
    private String toolType;
    /**
     * @fieldName outNum
    * @fieldType  String
    * @Description  出门单号
     */
    private String outNum;
    /**
     * @fieldName manufactureDate
    * @fieldType  Timestamp
    * @Description  出厂日期
     */
    private Timestamp manufactureDate;
    /**
     * @fieldName approver
    * @fieldType  String
    * @Description  审批人
     */
    private String approver;
    /**
     * @fieldName repairState
    * @fieldType  String
    * @Description  修复状态(0.待出厂1.已出厂2.已送回）
     */
    private String repairState;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName senduser
    * @fieldType  String
    * @Description  
     */
    private String senduser;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  材料刀编码
     */
    private String cuttingToolCode;

    /**
     * @fieldName sharpenProviderCode
    * @fieldType  
    * @Description  
     */
    private SharpenProvider sharpenProvider;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  材料刀编码
     */
    private CuttingTool cuttingTool;



    /* 场外复磨id */
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
    public Integer getNumberGrinding() {
        return numberGrinding;
    }

    public void setNumberGrinding(Integer numberGrinding) {
        this.numberGrinding = numberGrinding;
    }
    public Integer getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(Integer receiveNumber) {
        this.receiveNumber = receiveNumber;
    }
    public String getGrindingType() {
        return grindingType;
    }

    public void setGrindingType(String grindingType) {
        this.grindingType = grindingType;
    }
    public String getLaserCode() {
        return laserCode;
    }

    public void setLaserCode(String laserCode) {
        this.laserCode = laserCode;
    }
    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }
    public String getOutNum() {
        return outNum;
    }

    public void setOutNum(String outNum) {
        this.outNum = outNum;
    }
    public Timestamp getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Timestamp manufactureDate) {
        this.manufactureDate = manufactureDate;
    }
    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }
    public String getRepairState() {
        return repairState;
    }

    public void setRepairState(String repairState) {
        this.repairState = repairState;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getSenduser() {
        return senduser;
    }

    public void setSenduser(String senduser) {
        this.senduser = senduser;
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
