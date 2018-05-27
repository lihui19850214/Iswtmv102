package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 InsideFactoryVO 
*/
public class InsideFactoryVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  场内刃磨id
     */
    private Integer id;


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
     * @fieldName toolType
    * @fieldType  String
    * @Description  刀具类型（1钻头2.刀片）
     */
    private String toolType;

    /**
     * @fieldName approver
    * @fieldType  String
    * @Description  审批人
     */
    private String approver;

    /**
     * @fieldName repairState
    * @fieldType  String
    * @Description  修复状态(1 待刃磨 2已刃磨）
     */
    private String repairState;

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
     * @fieldName productLineEquipmentCode
    * @fieldType  String
    * @Description  刃磨设备
     */
    private String productLineEquipmentCode;

    /**
     * @fieldName oprator
    * @fieldType  String
    * @Description  操作人
     */
    private String oprator;

    /**
     * @fieldName opratorName
    * @fieldType  String
    * @Description  操作人姓名
     */
    private String opratorName;

    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  刃磨时间 开始时间
     */
    private Timestamp createTimeBegin;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  刃磨时间 结束时间
     */
    private Timestamp createTimeEnd;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  刃磨时间
     */
    private Timestamp createTime;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  
     */
    private CuttingToolVO cuttingToolVO;
    /**
     * @fieldName productLineEquipmentCode
    * @fieldType  
    * @Description  刃磨设备
     */
    private ProductLineEquipmentVO productLineEquipmentVO;


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


    /* 场内刃磨id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
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
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public String getProductLineEquipmentCode() {
        return productLineEquipmentCode;
    }

    public void setProductLineEquipmentCode(String productLineEquipmentCode) {
        this.productLineEquipmentCode = productLineEquipmentCode;
    }
    public String getOprator() {
        return oprator;
    }

    public void setOprator(String oprator) {
        this.oprator = oprator;
    }
    public String getOpratorName() {
        return opratorName;
    }

    public void setOpratorName(String opratorName) {
        this.opratorName = opratorName;
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
    public ProductLineEquipmentVO getProductLineEquipmentVO() {
        return productLineEquipmentVO;
    }

    public void setProductLineEquipmentVO(ProductLineEquipmentVO productLineEquipmentVO) {
        this.productLineEquipmentVO = productLineEquipmentVO;
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
