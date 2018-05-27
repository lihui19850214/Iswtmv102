package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 OutsidefactoryhistoryVO 
*/
public class OutsidefactoryhistoryVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  厂外修复履历ID
     */
    private Integer id;


    /**
     * @fieldName orderNum
    * @fieldType  String
    * @Description  通知单号
     */
    private String orderNum;

    /**
     * @fieldName outNum
    * @fieldType  String
    * @Description  出门单号
     */
    private String outNum;

    /**
     * @fieldName sharpenProviderCode
    * @fieldType  String
    * @Description  商家ID
     */
    private String sharpenProviderCode;

    /**
     * @fieldName materialNum
    * @fieldType  String
    * @Description  材料号
     */
    private String materialNum;

    /**
     * @fieldName backFactoryNumber
    * @fieldType  Integer
    * @Description  回厂数量
     */
    private Integer backFactoryNumber;

    /**
     * @fieldName createUser
    * @fieldType  String
    * @Description  创建人
     */
    private String createUser;

    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  创建时间 开始时间
     */
    private Timestamp createTimeBegin;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  创建时间 结束时间
     */
    private Timestamp createTimeEnd;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  创建时间
     */
    private Timestamp createTime;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  
     */
    private String cuttingToolCode;

    /**
     * @fieldName sharpenProviderCode
    * @fieldType  
    * @Description  商家ID
     */
    private SharpenProviderVO sharpenProviderVO;
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


    /* 厂外修复履历ID */
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
    public String getOutNum() {
        return outNum;
    }

    public void setOutNum(String outNum) {
        this.outNum = outNum;
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
    public Integer getBackFactoryNumber() {
        return backFactoryNumber;
    }

    public void setBackFactoryNumber(Integer backFactoryNumber) {
        this.backFactoryNumber = backFactoryNumber;
    }
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public SharpenProviderVO getSharpenProviderVO() {
        return sharpenProviderVO;
    }

    public void setSharpenProviderVO(SharpenProviderVO sharpenProviderVO) {
        this.sharpenProviderVO = sharpenProviderVO;
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
