package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 CuttingToolBindleRecords 
*/
public class CuttingToolBindleRecords implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;

    /**
     * @fieldName productLineEquipmentCode
    * @fieldType  String
    * @Description  设备编码
     */
    private String productLineEquipmentCode;
    /**
     * @fieldName productLinePartsCode
    * @fieldType  String
    * @Description  
     */
    private String productLinePartsCode;
    /**
     * @fieldName productLineAxleCode
    * @fieldType  String
    * @Description  
     */
    private String productLineAxleCode;
    /**
     * @fieldName productLineProcessCode
    * @fieldType  String
    * @Description  
     */
    private String productLineProcessCode;
    /**
     * @fieldName productLineAssemblylineCode
    * @fieldType  String
    * @Description  
     */
    private String productLineAssemblylineCode;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  String
    * @Description  
     */
    private String synthesisCuttingToolCode;
    /**
     * @fieldName synthesisCode
    * @fieldType  String
    * @Description  
     */
    private String synthesisCode;
    /**
     * @fieldName productLineEquipmentName
    * @fieldType  String
    * @Description  
     */
    private String productLineEquipmentName;
    /**
     * @fieldName productLinePartsName
    * @fieldType  String
    * @Description  
     */
    private String productLinePartsName;
    /**
     * @fieldName productLineAxleName
    * @fieldType  String
    * @Description  
     */
    private String productLineAxleName;
    /**
     * @fieldName productLineProcessName
    * @fieldType  String
    * @Description  
     */
    private String productLineProcessName;
    /**
     * @fieldName ratedNumber
    * @fieldType  Integer
    * @Description  
     */
    private Integer ratedNumber;
    /**
     * @fieldName actualNumber
    * @fieldType  Integer
    * @Description  
     */
    private Integer actualNumber;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;
    /**
     * @fieldName operatorName
    * @fieldType  String
    * @Description  操作人姓名
     */
    private String operatorName;
    /**
     * @fieldName operator
    * @fieldType  String
    * @Description  操作人code
     */
    private String operator;
    /**
     * @fieldName operatorTime
    * @fieldType  Timestamp
    * @Description  操作日期
     */
    private Timestamp operatorTime;

    /**
     * @fieldName productLineAssemblylineCode
    * @fieldType  
    * @Description  
     */
    private ProductLineAssemblyline productLineAssemblyline;
    /**
     * @fieldName productLineAxleCode
    * @fieldType  
    * @Description  
     */
    private ProductLineAxle productLineAxle;
    /**
     * @fieldName productLineEquipmentCode
    * @fieldType  
    * @Description  设备编码
     */
    private ProductLineEquipment productLineEquipment;
    /**
     * @fieldName productLinePartsCode
    * @fieldType  
    * @Description  
     */
    private ProductLineParts productLineParts;
    /**
     * @fieldName productLineProcessCode
    * @fieldType  
    * @Description  
     */
    private ProductLineProcess productLineProcess;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  
     */
    private SynthesisCuttingTool synthesisCuttingTool;



    /* id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductLineEquipmentCode() {
        return productLineEquipmentCode;
    }

    public void setProductLineEquipmentCode(String productLineEquipmentCode) {
        this.productLineEquipmentCode = productLineEquipmentCode;
    }
    public String getProductLinePartsCode() {
        return productLinePartsCode;
    }

    public void setProductLinePartsCode(String productLinePartsCode) {
        this.productLinePartsCode = productLinePartsCode;
    }
    public String getProductLineAxleCode() {
        return productLineAxleCode;
    }

    public void setProductLineAxleCode(String productLineAxleCode) {
        this.productLineAxleCode = productLineAxleCode;
    }
    public String getProductLineProcessCode() {
        return productLineProcessCode;
    }

    public void setProductLineProcessCode(String productLineProcessCode) {
        this.productLineProcessCode = productLineProcessCode;
    }
    public String getProductLineAssemblylineCode() {
        return productLineAssemblylineCode;
    }

    public void setProductLineAssemblylineCode(String productLineAssemblylineCode) {
        this.productLineAssemblylineCode = productLineAssemblylineCode;
    }
    public String getSynthesisCuttingToolCode() {
        return synthesisCuttingToolCode;
    }

    public void setSynthesisCuttingToolCode(String synthesisCuttingToolCode) {
        this.synthesisCuttingToolCode = synthesisCuttingToolCode;
    }
    public String getSynthesisCode() {
        return synthesisCode;
    }

    public void setSynthesisCode(String synthesisCode) {
        this.synthesisCode = synthesisCode;
    }
    public String getProductLineEquipmentName() {
        return productLineEquipmentName;
    }

    public void setProductLineEquipmentName(String productLineEquipmentName) {
        this.productLineEquipmentName = productLineEquipmentName;
    }
    public String getProductLinePartsName() {
        return productLinePartsName;
    }

    public void setProductLinePartsName(String productLinePartsName) {
        this.productLinePartsName = productLinePartsName;
    }
    public String getProductLineAxleName() {
        return productLineAxleName;
    }

    public void setProductLineAxleName(String productLineAxleName) {
        this.productLineAxleName = productLineAxleName;
    }
    public String getProductLineProcessName() {
        return productLineProcessName;
    }

    public void setProductLineProcessName(String productLineProcessName) {
        this.productLineProcessName = productLineProcessName;
    }
    public Integer getRatedNumber() {
        return ratedNumber;
    }

    public void setRatedNumber(Integer ratedNumber) {
        this.ratedNumber = ratedNumber;
    }
    public Integer getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(Integer actualNumber) {
        this.actualNumber = actualNumber;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    public Timestamp getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Timestamp operatorTime) {
        this.operatorTime = operatorTime;
    }

    public ProductLineAssemblyline getProductLineAssemblyline() {
        return productLineAssemblyline;
    }

    public void setProductLineAssemblyline(ProductLineAssemblyline productLineAssemblyline) {
        this.productLineAssemblyline = productLineAssemblyline;
    }
    public ProductLineAxle getProductLineAxle() {
        return productLineAxle;
    }

    public void setProductLineAxle(ProductLineAxle productLineAxle) {
        this.productLineAxle = productLineAxle;
    }
    public ProductLineEquipment getProductLineEquipment() {
        return productLineEquipment;
    }

    public void setProductLineEquipment(ProductLineEquipment productLineEquipment) {
        this.productLineEquipment = productLineEquipment;
    }
    public ProductLineParts getProductLineParts() {
        return productLineParts;
    }

    public void setProductLineParts(ProductLineParts productLineParts) {
        this.productLineParts = productLineParts;
    }
    public ProductLineProcess getProductLineProcess() {
        return productLineProcess;
    }

    public void setProductLineProcess(ProductLineProcess productLineProcess) {
        this.productLineProcess = productLineProcess;
    }
    public SynthesisCuttingTool getSynthesisCuttingTool() {
        return synthesisCuttingTool;
    }

    public void setSynthesisCuttingTool(SynthesisCuttingTool synthesisCuttingTool) {
        this.synthesisCuttingTool = synthesisCuttingTool;
    }


}
