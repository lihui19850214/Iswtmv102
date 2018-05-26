package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 CuttingToolProductionRecords 
*/
public class CuttingToolProductionRecords implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  材料刀加工记录
     */
    private Integer id;

    /**
     * @fieldName businessCode
    * @fieldType  String
    * @Description  材料刀物料码
     */
    private String businessCode;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  材料刀编码
     */
    private String cuttingToolCode;
    /**
     * @fieldName productLineName
    * @fieldType  String
    * @Description  生产线名
     */
    private String productLineName;
    /**
     * @fieldName productLineCode
    * @fieldType  String
    * @Description  生产线编码
     */
    private String productLineCode;
    /**
     * @fieldName equipmentName
    * @fieldType  String
    * @Description  设备名称
     */
    private String equipmentName;
    /**
     * @fieldName equipmentCode
    * @fieldType  String
    * @Description  设备编码
     */
    private String equipmentCode;
    /**
     * @fieldName axleName
    * @fieldType  String
    * @Description  轴名称
     */
    private String axleName;
    /**
     * @fieldName axleCode
    * @fieldType  String
    * @Description  轴编码
     */
    private String axleCode;
    /**
     * @fieldName assemblylineCode
    * @fieldType  String
    * @Description  工序编码
     */
    private String assemblylineCode;
    /**
     * @fieldName assemblylineName
    * @fieldType  String
    * @Description  工序名称
     */
    private String assemblylineName;
    /**
     * @fieldName lastTime
    * @fieldType  Timestamp
    * @Description  最后加工时间
     */
    private Timestamp lastTime;
    /**
     * @fieldName processingCount
    * @fieldType  Integer
    * @Description  加工数量
     */
    private Integer processingCount;
    /**
     * @fieldName synthesisToolConsumptionQuantity
    * @fieldType  String
    * @Description  材料刀消耗数量
     */
    private Integer synthesisToolConsumptionQuantity;
    /**
     * @fieldName processName
    * @fieldType  String
    * @Description  工序名称
     */
    private String processName;
    /**
     * @fieldName processCode
    * @fieldType  String
    * @Description  工序编码
     */
    private String processCode;




    /* 材料刀加工记录 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public String getProductLineName() {
        return productLineName;
    }

    public void setProductLineName(String productLineName) {
        this.productLineName = productLineName;
    }
    public String getProductLineCode() {
        return productLineCode;
    }

    public void setProductLineCode(String productLineCode) {
        this.productLineCode = productLineCode;
    }
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }
    public String getAxleName() {
        return axleName;
    }

    public void setAxleName(String axleName) {
        this.axleName = axleName;
    }
    public String getAxleCode() {
        return axleCode;
    }

    public void setAxleCode(String axleCode) {
        this.axleCode = axleCode;
    }
    public String getAssemblylineCode() {
        return assemblylineCode;
    }

    public void setAssemblylineCode(String assemblylineCode) {
        this.assemblylineCode = assemblylineCode;
    }
    public String getAssemblylineName() {
        return assemblylineName;
    }

    public void setAssemblylineName(String assemblylineName) {
        this.assemblylineName = assemblylineName;
    }
    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }
    public Integer getProcessingCount() {
        return processingCount;
    }

    public void setProcessingCount(Integer processingCount) {
        this.processingCount = processingCount;
    }
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public Integer getSynthesisToolConsumptionQuantity() {
        return synthesisToolConsumptionQuantity;
    }

    public void setSynthesisToolConsumptionQuantity(Integer synthesisToolConsumptionQuantity) {
        this.synthesisToolConsumptionQuantity = synthesisToolConsumptionQuantity;
    }
}
