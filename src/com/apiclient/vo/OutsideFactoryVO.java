package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 OutsideFactoryVO 
*/
public class OutsideFactoryVO implements Serializable {

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
    * @Description  出厂日期 开始时间
     */
    private Timestamp manufactureDateBegin;
    /**
     * @fieldName manufactureDate
    * @fieldType  Timestamp
    * @Description  出厂日期 结束时间
     */
    private Timestamp manufactureDateEnd;
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
    private CuttingToolVO cuttingToolVO;
    /**
     * @fieldName sharpenProviderCode
    * @fieldType  
    * @Description  外委商
     */
    private SharpenProviderVO sharpenProviderVO;


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
    public Timestamp getManufactureDateBegin() {
        return manufactureDateBegin;
    }

    public void setManufactureDateBegin(Timestamp manufactureDateBegin) {
        this.manufactureDateBegin = manufactureDateBegin;
    }

    public Timestamp getManufactureDateEnd() {
        return manufactureDateEnd;
    }

    public void setManufactureDateEnd(Timestamp manufactureDateEnd) {
        this.manufactureDateEnd = manufactureDateEnd;
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

    public CuttingToolVO getCuttingToolVO() {
        return cuttingToolVO;
    }

    public void setCuttingToolVO(CuttingToolVO cuttingToolVO) {
        this.cuttingToolVO = cuttingToolVO;
    }
    public SharpenProviderVO getSharpenProviderVO() {
        return sharpenProviderVO;
    }

    public void setSharpenProviderVO(SharpenProviderVO sharpenProviderVO) {
        this.sharpenProviderVO = sharpenProviderVO;
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
