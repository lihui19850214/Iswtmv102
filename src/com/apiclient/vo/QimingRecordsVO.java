package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 QimingRecordsVO 
*/
public class QimingRecordsVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;


    /**
     * @fieldName applyNo
    * @fieldType  String
    * @Description  启明出库单号
     */
    private String applyNo;

    /**
     * @fieldName unitqty
    * @fieldType  String
    * @Description  出库数量
     */
    private String unitqty;

    /**
     * @fieldName mtlCode
    * @fieldType  String
    * @Description  物料号
     */
    private String mtlCode;

    /**
     * @fieldName loc
    * @fieldType  String
    * @Description  刀具号
     */
    private String loc;

    /**
     * @fieldName batchNo
    * @fieldType  Integer
    * @Description  批次
     */
    private Integer batchNo;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  材料刀号
     */
    private String cuttingToolCode;

    /**
     * @fieldName cuttingToolBindCode
    * @fieldType  String
    * @Description  材料刀绑定
     */
    private String cuttingToolBindCode;

    /**
     * @fieldName bladeCode
    * @fieldType  String
    * @Description  刀身码
     */
    private String bladeCode;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName productlineProcess
    * @fieldType  String
    * @Description  
     */
    private String productlineProcess;

    /**
     * @fieldName productlineAssemblyline
    * @fieldType  String
    * @Description  
     */
    private String productlineAssemblyline;

    /**
     * @fieldName plant
    * @fieldType  String
    * @Description  
     */
    private String plant;

    /**
     * @fieldName kuguanOperatorCode
    * @fieldType  String
    * @Description  
     */
    private String kuguanOperatorCode;

    /**
     * @fieldName linglOperatorCode
    * @fieldType  String
    * @Description  
     */
    private String linglOperatorCode;

    /**
     * @fieldName kezhangCode
    * @fieldType  String
    * @Description  
     */
    private String kezhangCode;

    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description   开始时间
     */
    private Timestamp createTimeBegin;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description   结束时间
     */
    private Timestamp createTimeEnd;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  
     */
    private Timestamp createTime;

    /**
     * @fieldName kuguanOperatorName
    * @fieldType  String
    * @Description  
     */
    private String kuguanOperatorName;

    /**
     * @fieldName linglOperatorName
    * @fieldType  String
    * @Description  
     */
    private String linglOperatorName;

    /**
     * @fieldName kezhangName
    * @fieldType  String
    * @Description  
     */
    private String kezhangName;



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


    /* id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }
    public String getUnitqty() {
        return unitqty;
    }

    public void setUnitqty(String unitqty) {
        this.unitqty = unitqty;
    }
    public String getMtlCode() {
        return mtlCode;
    }

    public void setMtlCode(String mtlCode) {
        this.mtlCode = mtlCode;
    }
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
    public Integer getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public String getCuttingToolBindCode() {
        return cuttingToolBindCode;
    }

    public void setCuttingToolBindCode(String cuttingToolBindCode) {
        this.cuttingToolBindCode = cuttingToolBindCode;
    }
    public String getBladeCode() {
        return bladeCode;
    }

    public void setBladeCode(String bladeCode) {
        this.bladeCode = bladeCode;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getProductlineProcess() {
        return productlineProcess;
    }

    public void setProductlineProcess(String productlineProcess) {
        this.productlineProcess = productlineProcess;
    }
    public String getProductlineAssemblyline() {
        return productlineAssemblyline;
    }

    public void setProductlineAssemblyline(String productlineAssemblyline) {
        this.productlineAssemblyline = productlineAssemblyline;
    }
    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }
    public String getKuguanOperatorCode() {
        return kuguanOperatorCode;
    }

    public void setKuguanOperatorCode(String kuguanOperatorCode) {
        this.kuguanOperatorCode = kuguanOperatorCode;
    }
    public String getLinglOperatorCode() {
        return linglOperatorCode;
    }

    public void setLinglOperatorCode(String linglOperatorCode) {
        this.linglOperatorCode = linglOperatorCode;
    }
    public String getKezhangCode() {
        return kezhangCode;
    }

    public void setKezhangCode(String kezhangCode) {
        this.kezhangCode = kezhangCode;
    }
    public Timestamp getCreateTimeBegin() {
        return createTimeBegin;
    }

    public void setCreateTimeBegin(Timestamp createTimeBegin) {
        this.createTimeBegin = createTimeBegin;
    }

    public Timestamp getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Timestamp createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    public String getKuguanOperatorName() {
        return kuguanOperatorName;
    }

    public void setKuguanOperatorName(String kuguanOperatorName) {
        this.kuguanOperatorName = kuguanOperatorName;
    }
    public String getLinglOperatorName() {
        return linglOperatorName;
    }

    public void setLinglOperatorName(String linglOperatorName) {
        this.linglOperatorName = linglOperatorName;
    }
    public String getKezhangName() {
        return kezhangName;
    }

    public void setKezhangName(String kezhangName) {
        this.kezhangName = kezhangName;
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
