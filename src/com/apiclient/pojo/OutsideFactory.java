package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 OutsideFactory 
*/
public class OutsideFactory implements Serializable {

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
    * @Description  外委单号
     */
    private String orderNum;
    /**
     * @fieldName sharpenProviderCode
    * @fieldType  String
    * @Description  外委商
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
     * @fieldName grindingType
    * @fieldType  String
    * @Description  修复类型（0.厂外图层1.厂外复磨）
     */
    private String grindingType;
    /**
     * @fieldName toolType
    * @fieldType  String
    * @Description  刀具类型（0.钻头1.刀片）
     */
    private String toolType;
    /**
     * @fieldName manufactureDate
    * @fieldType  Timestamp
    * @Description  出厂日期
     */
    private Timestamp manufactureDate;
    /**
     * @fieldName approverCode
    * @fieldType  String
    * @Description  审批人
     */
    private String approverCode;
    /**
     * @fieldName repairState
    * @fieldType  String
    * @Description  修复状态(1.待出厂 2.已出厂 3.已送回）
     */
    private String repairState;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  材料刀编码
     */
    private String cuttingToolCode;
    /**
     * @fieldName zcCode
    * @fieldType  String
    * @Description  资材单号
     */
    private String zcCode;
    /**
     * @fieldName handlers
    * @fieldType  String
    * @Description  经手人
     */
    private String handlers;
    /**
     * @fieldName sender
    * @fieldType  String
    * @Description  邮寄人
     */
    private String sender;
    /**
     * @fieldName outWay
    * @fieldType  String
    * @Description  外委方式
     */
    private String outWay;
    /**
     * @fieldName opratorName
    * @fieldType  String
    * @Description  操作人
     */
    private String opratorName;
    /**
     * @fieldName opratorCode
    * @fieldType  String
    * @Description  
     */
    private String opratorCode;
    /**
     * @fieldName code
    * @fieldType  String
    * @Description  编码
     */
    private String code;
    /**
     * @fieldName qmSharpenProviderCode
    * @fieldType  String
    * @Description  启明外委供应商编码
     */
    private String qmSharpenProviderCode;
    /**
     * @fieldName qmSharpenProviderName
    * @fieldType  String
    * @Description  启明外委供应商名称
     */
    private String qmSharpenProviderName;
    /**
     * @fieldName approverName
    * @fieldType  String
    * @Description  审批人姓名
     */
    private String approverName;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  材料刀编码
     */
    private CuttingTool cuttingTool;
    /**
     * @fieldName sharpenProviderCode
    * @fieldType  
    * @Description  外委商
     */
    private SharpenProvider sharpenProvider;



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
    public String getGrindingType() {
        return grindingType;
    }

    public void setGrindingType(String grindingType) {
        this.grindingType = grindingType;
    }
    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }
    public Timestamp getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Timestamp manufactureDate) {
        this.manufactureDate = manufactureDate;
    }
    public String getApproverCode() {
        return approverCode;
    }

    public void setApproverCode(String approverCode) {
        this.approverCode = approverCode;
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
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public String getZcCode() {
        return zcCode;
    }

    public void setZcCode(String zcCode) {
        this.zcCode = zcCode;
    }
    public String getHandlers() {
        return handlers;
    }

    public void setHandlers(String handlers) {
        this.handlers = handlers;
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getOutWay() {
        return outWay;
    }

    public void setOutWay(String outWay) {
        this.outWay = outWay;
    }
    public String getOpratorName() {
        return opratorName;
    }

    public void setOpratorName(String opratorName) {
        this.opratorName = opratorName;
    }
    public String getOpratorCode() {
        return opratorCode;
    }

    public void setOpratorCode(String opratorCode) {
        this.opratorCode = opratorCode;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getQmSharpenProviderCode() {
        return qmSharpenProviderCode;
    }

    public void setQmSharpenProviderCode(String qmSharpenProviderCode) {
        this.qmSharpenProviderCode = qmSharpenProviderCode;
    }
    public String getQmSharpenProviderName() {
        return qmSharpenProviderName;
    }

    public void setQmSharpenProviderName(String qmSharpenProviderName) {
        this.qmSharpenProviderName = qmSharpenProviderName;
    }
    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public CuttingTool getCuttingTool() {
        return cuttingTool;
    }

    public void setCuttingTool(CuttingTool cuttingTool) {
        this.cuttingTool = cuttingTool;
    }
    public SharpenProvider getSharpenProvider() {
        return sharpenProvider;
    }

    public void setSharpenProvider(SharpenProvider sharpenProvider) {
        this.sharpenProvider = sharpenProvider;
    }


}
