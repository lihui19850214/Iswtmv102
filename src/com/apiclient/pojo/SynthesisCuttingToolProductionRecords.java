package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolProductionRecords 
*/
public class SynthesisCuttingToolProductionRecords implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  合成刀加工记录
     */
    private Integer id;

    /**
     * @fieldName assemblylineCode
    * @fieldType  String
    * @Description  流水线编码
     */
    private String assemblylineCode;
    /**
     * @fieldName assemblylineName
    * @fieldType  String
    * @Description  流水线名称
     */
    private String assemblylineName;
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
     * @fieldName synthesisCuttingToolCode
    * @fieldType  String
    * @Description  合成刀编码
     */
    private String synthesisCuttingToolCode;
    /**
     * @fieldName synthesisCode
    * @fieldType  String
    * @Description  合成刀业务编码
     */
    private String synthesisCode;
    /**
     * @fieldName lastTime
    * @fieldType  Timestamp
    * @Description  最后加工时间
     */
    private Timestamp lastTime;
    /**
     * @fieldName processCode
    * @fieldType  String
    * @Description  工序编码
     */
    private String processCode;
    /**
     * @fieldName processName
    * @fieldType  String
    * @Description  工序名称
     */
    private String processName;
    /**
     * @fieldName processingCount
    * @fieldType  Integer
    * @Description  加工数量
     */
    private Integer processingCount;
    /**
     * @fieldName ratedNumber
    * @fieldType  Integer
    * @Description  额定数量
     */
    private Integer ratedNumber;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;
    /**
     * @fieldName productLineCode
    * @fieldType  String
    * @Description  生产关联关系编码
     */
    private String productLineCode;
    /**
     * @fieldName partsCode
    * @fieldType  String
    * @Description  零件编码
     */
    private String partsCode;
    /**
     * @fieldName partsName
    * @fieldType  String
    * @Description  零件名称
     */
    private String partsName;




    /* 合成刀加工记录 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }
    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public Integer getProcessingCount() {
        return processingCount;
    }

    public void setProcessingCount(Integer processingCount) {
        this.processingCount = processingCount;
    }
    public Integer getRatedNumber() {
        return ratedNumber;
    }

    public void setRatedNumber(Integer ratedNumber) {
        this.ratedNumber = ratedNumber;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getProductLineCode() {
        return productLineCode;
    }

    public void setProductLineCode(String productLineCode) {
        this.productLineCode = productLineCode;
    }
    public String getPartsCode() {
        return partsCode;
    }

    public void setPartsCode(String partsCode) {
        this.partsCode = partsCode;
    }
    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }



}
