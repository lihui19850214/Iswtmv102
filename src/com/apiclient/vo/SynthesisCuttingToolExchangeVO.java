package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolExchangeVO 
*/
public class SynthesisCuttingToolExchangeVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  换装id
     */
    private Integer id;


    /**
     * @fieldName synthesisCode
    * @fieldType  String
    * @Description  合成刀业务编码
     */
    private String synthesisCode;

    /**
     * @fieldName rfidCode
    * @fieldType  String
    * @Description  rfid编码
     */
    private String rfidCode;

    /**
     * @fieldName toolUpTime
    * @fieldType  Timestamp
    * @Description  刀具装上时间 开始时间
     */
    private Timestamp toolUpTimeBegin;
    /**
     * @fieldName toolUpTime
    * @fieldType  Timestamp
    * @Description  刀具装上时间 结束时间
     */
    private Timestamp toolUpTimeEnd;
    /**
     * @fieldName toolUpTime
    * @fieldType  Timestamp
    * @Description  刀具装上时间
     */
    private Timestamp toolUpTime;

    /**
     * @fieldName toolUpCustomerCode
    * @fieldType  String
    * @Description  刀具安装人
     */
    private String toolUpCustomerCode;

    /**
     * @fieldName tollUpCustomerName
    * @fieldType  String
    * @Description  刀具安装人姓名
     */
    private String tollUpCustomerName;

    /**
     * @fieldName toolDownTime
    * @fieldType  String
    * @Description  刀具卸下时间
     */
    private String toolDownTime;

    /**
     * @fieldName toolDownCustomerCode
    * @fieldType  String
    * @Description  刀具卸下人
     */
    private String toolDownCustomerCode;

    /**
     * @fieldName tollDownCustomerName
    * @fieldType  String
    * @Description  刀具卸下人姓名
     */
    private String tollDownCustomerName;

    /**
     * @fieldName axleCode
    * @fieldType  String
    * @Description  轴编码
     */
    private String axleCode;

    /**
     * @fieldName axleName
    * @fieldType  String
    * @Description  轴名称
     */
    private String axleName;

    /**
     * @fieldName equipmentCode
    * @fieldType  String
    * @Description  设备编码
     */
    private String equipmentCode;

    /**
     * @fieldName equipmentName
    * @fieldType  String
    * @Description  设备名称
     */
    private String equipmentName;

    /**
     * @fieldName partsCode
    * @fieldType  String
    * @Description  零部件编码
     */
    private String partsCode;

    /**
     * @fieldName partsName
    * @fieldType  String
    * @Description  零部件名称
     */
    private String partsName;

    /**
     * @fieldName fixedNumber
    * @fieldType  Integer
    * @Description  额定数量
     */
    private Integer fixedNumber;

    /**
     * @fieldName realQuantity
    * @fieldType  Integer
    * @Description  实际数量
     */
    private Integer realQuantity;

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


    /* 换装id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSynthesisCode() {
        return synthesisCode;
    }

    public void setSynthesisCode(String synthesisCode) {
        this.synthesisCode = synthesisCode;
    }
    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }
    public Timestamp getToolUpTimeBegin() {
        return toolUpTimeBegin;
    }

    public void setToolUpTimeBegin(Timestamp toolUpTimeBegin) {
        this.toolUpTimeBegin = toolUpTimeBegin;
    }

    public Timestamp getToolUpTimeEnd() {
        return toolUpTimeEnd;
    }

    public void setToolUpTimeEnd(Timestamp toolUpTimeEnd) {
        this.toolUpTimeEnd = toolUpTimeEnd;
    }
    public Timestamp getToolUpTime() {
        return toolUpTime;
    }

    public void setToolUpTime(Timestamp toolUpTime) {
        this.toolUpTime = toolUpTime;
    }
    public String getToolUpCustomerCode() {
        return toolUpCustomerCode;
    }

    public void setToolUpCustomerCode(String toolUpCustomerCode) {
        this.toolUpCustomerCode = toolUpCustomerCode;
    }
    public String getTollUpCustomerName() {
        return tollUpCustomerName;
    }

    public void setTollUpCustomerName(String tollUpCustomerName) {
        this.tollUpCustomerName = tollUpCustomerName;
    }
    public String getToolDownTime() {
        return toolDownTime;
    }

    public void setToolDownTime(String toolDownTime) {
        this.toolDownTime = toolDownTime;
    }
    public String getToolDownCustomerCode() {
        return toolDownCustomerCode;
    }

    public void setToolDownCustomerCode(String toolDownCustomerCode) {
        this.toolDownCustomerCode = toolDownCustomerCode;
    }
    public String getTollDownCustomerName() {
        return tollDownCustomerName;
    }

    public void setTollDownCustomerName(String tollDownCustomerName) {
        this.tollDownCustomerName = tollDownCustomerName;
    }
    public String getAxleCode() {
        return axleCode;
    }

    public void setAxleCode(String axleCode) {
        this.axleCode = axleCode;
    }
    public String getAxleName() {
        return axleName;
    }

    public void setAxleName(String axleName) {
        this.axleName = axleName;
    }
    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
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
    public Integer getFixedNumber() {
        return fixedNumber;
    }

    public void setFixedNumber(Integer fixedNumber) {
        this.fixedNumber = fixedNumber;
    }
    public Integer getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(Integer realQuantity) {
        this.realQuantity = realQuantity;
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
