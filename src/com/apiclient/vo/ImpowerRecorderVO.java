package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 ImpowerRecorderVO 
*/
public class ImpowerRecorderVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  授权记录标识
     */
    private Integer id;


    /**
     * @fieldName toolCode
    * @fieldType  String
    * @Description  刀具号
     */
    private String toolCode;

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
     * @fieldName impowerUser
    * @fieldType  String
    * @Description  授权人code
     */
    private String impowerUser;

    /**
     * @fieldName impowerUserName
    * @fieldType  String
    * @Description  授权人名称
     */
    private String impowerUserName;

    /**
     * @fieldName reasonKey
    * @fieldType  String
    * @Description  授权原因
     */
    private String reasonKey;

    /**
     * @fieldName reasonValue
    * @fieldType  String
    * @Description  授权原因文本
     */
    private String reasonValue;

    /**
     * @fieldName currentStatusKey
    * @fieldType  String
    * @Description  刀具状态
     */
    private String currentStatusKey;

    /**
     * @fieldName currentStatusName
    * @fieldType  String
    * @Description  当前状态文本
     */
    private String currentStatusName;

    /**
     * @fieldName operatorKey
    * @fieldType  String
    * @Description  操作key
     */
    private String operatorKey;

    /**
     * @fieldName operatorValue
    * @fieldType  String
    * @Description  操作文本
     */
    private String operatorValue;

    /**
     * @fieldName operatorUserCode
    * @fieldType  String
    * @Description  操作者code
     */
    private String operatorUserCode;

    /**
     * @fieldName operatorUserName
    * @fieldType  String
    * @Description  操作者姓名
     */
    private String operatorUserName;

    /**
     * @fieldName resume
    * @fieldType  String
    * @Description  简述
     */
    private String resume;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;



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


    /* 授权记录标识 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
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
    public String getImpowerUser() {
        return impowerUser;
    }

    public void setImpowerUser(String impowerUser) {
        this.impowerUser = impowerUser;
    }
    public String getImpowerUserName() {
        return impowerUserName;
    }

    public void setImpowerUserName(String impowerUserName) {
        this.impowerUserName = impowerUserName;
    }
    public String getReasonKey() {
        return reasonKey;
    }

    public void setReasonKey(String reasonKey) {
        this.reasonKey = reasonKey;
    }
    public String getReasonValue() {
        return reasonValue;
    }

    public void setReasonValue(String reasonValue) {
        this.reasonValue = reasonValue;
    }
    public String getCurrentStatusKey() {
        return currentStatusKey;
    }

    public void setCurrentStatusKey(String currentStatusKey) {
        this.currentStatusKey = currentStatusKey;
    }
    public String getCurrentStatusName() {
        return currentStatusName;
    }

    public void setCurrentStatusName(String currentStatusName) {
        this.currentStatusName = currentStatusName;
    }
    public String getOperatorKey() {
        return operatorKey;
    }

    public void setOperatorKey(String operatorKey) {
        this.operatorKey = operatorKey;
    }
    public String getOperatorValue() {
        return operatorValue;
    }

    public void setOperatorValue(String operatorValue) {
        this.operatorValue = operatorValue;
    }
    public String getOperatorUserCode() {
        return operatorUserCode;
    }

    public void setOperatorUserCode(String operatorUserCode) {
        this.operatorUserCode = operatorUserCode;
    }
    public String getOperatorUserName() {
        return operatorUserName;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }
    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
