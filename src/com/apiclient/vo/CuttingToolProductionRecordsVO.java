package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 CuttingToolProductionRecordsVO 
*/
public class CuttingToolProductionRecordsVO implements Serializable{

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
    * @Description  最后加工时间 开始时间
     */
    private Timestamp lastTimeBegin;
    /**
     * @fieldName lastTime
    * @fieldType  Timestamp
    * @Description  最后加工时间 结束时间
     */
    private Timestamp lastTimeEnd;
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
    private String synthesisToolConsumptionQuantity;

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



    /**
     * @fieldName currentPage
    * @fieldType  Integer
    * @Description  当前页码
     */
    private Integer currentPage = 1;

    /**
     * @fieldName totalPage
    * @fieldType  Integer
    * @Description  总页数
     */
    private Integer totalPage;

    /**
     * @fieldName pageSize
    * @fieldType  Integer
    * @Description  每页记录条数
     */
    private Integer pageSize = 10;

    /**
     * @fieldName maxPage
    * @fieldType  Integer
    * @Description  总页数
     */
    private Integer maxPage;

    /**
     * @fieldName startRecord
    * @fieldType  Integer
    * @Description  开始查询记录
     */
    private Integer startRecord;


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
    public Timestamp getLastTimeBegin() {
        return lastTimeBegin;
    }

    public void setLastTimeBegin(Timestamp lastTimeBegin) {
        this.lastTimeBegin = lastTimeBegin;
    }

    public Timestamp getLastTimeEnd() {
        return lastTimeEnd;
    }

    public void setLastTimeEnd(Timestamp lastTimeEnd) {
        this.lastTimeEnd = lastTimeEnd;
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
    public String getSynthesisToolConsumptionQuantity() {
        return synthesisToolConsumptionQuantity;
    }

    public void setSynthesisToolConsumptionQuantity(String synthesisToolConsumptionQuantity) {
        this.synthesisToolConsumptionQuantity = synthesisToolConsumptionQuantity;
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



    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.startRecord = (this.currentPage-1)*pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        this.maxPage = this.totalPage/this.pageSize+(this.totalPage%this.pageSize)>0?1:0;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRecord() {
        return startRecord;
    }

    public void setStartRecord(Integer startRecord) {
        this.startRecord = startRecord;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }



}
