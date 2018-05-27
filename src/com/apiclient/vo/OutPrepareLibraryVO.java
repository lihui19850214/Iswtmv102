package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 OutPrepareLibraryVO 
*/
public class OutPrepareLibraryVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  出库id
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
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  
     */
    private String cuttingToolCode;

    /**
     * @fieldName productlineProcess
    * @fieldType  String
    * @Description  工序
     */
    private String productlineProcess;

    /**
     * @fieldName productlineAssemblyline
    * @fieldType  String
    * @Description  流水线
     */
    private String productlineAssemblyline;

    /**
     * @fieldName plant
    * @fieldType  String
    * @Description  工厂
     */
    private String plant;

    /**
     * @fieldName kuguanOperatorCode
    * @fieldType  String
    * @Description  库管员code
     */
    private String kuguanOperatorCode;

    /**
     * @fieldName kuguanOperatorName
    * @fieldType  String
    * @Description  库管员姓名
     */
    private String kuguanOperatorName;

    /**
     * @fieldName linglOperatorCode
    * @fieldType  String
    * @Description  领料员code
     */
    private String linglOperatorCode;

    /**
     * @fieldName linglOperatorName
    * @fieldType  String
    * @Description  领料员姓名
     */
    private String linglOperatorName;

    /**
     * @fieldName kezhangCode
    * @fieldType  String
    * @Description  科长code
     */
    private String kezhangCode;

    /**
     * @fieldName kezhangName
    * @fieldType  String
    * @Description  科长姓名
     */
    private String kezhangName;

    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  出库时间 开始时间
     */
    private Timestamp createTimeBegin;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  出库时间 结束时间
     */
    private Timestamp createTimeEnd;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  出库时间
     */
    private Timestamp createTime;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  
     */
    private CuttingToolVO cuttingToolVO;


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


    /* 出库id */
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
    public String getKuguanOperatorName() {
        return kuguanOperatorName;
    }

    public void setKuguanOperatorName(String kuguanOperatorName) {
        this.kuguanOperatorName = kuguanOperatorName;
    }
    public String getLinglOperatorCode() {
        return linglOperatorCode;
    }

    public void setLinglOperatorCode(String linglOperatorCode) {
        this.linglOperatorCode = linglOperatorCode;
    }
    public String getLinglOperatorName() {
        return linglOperatorName;
    }

    public void setLinglOperatorName(String linglOperatorName) {
        this.linglOperatorName = linglOperatorName;
    }
    public String getKezhangCode() {
        return kezhangCode;
    }

    public void setKezhangCode(String kezhangCode) {
        this.kezhangCode = kezhangCode;
    }
    public String getKezhangName() {
        return kezhangName;
    }

    public void setKezhangName(String kezhangName) {
        this.kezhangName = kezhangName;
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

    public CuttingToolVO getCuttingToolVO() {
        return cuttingToolVO;
    }

    public void setCuttingToolVO(CuttingToolVO cuttingToolVO) {
        this.cuttingToolVO = cuttingToolVO;
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
