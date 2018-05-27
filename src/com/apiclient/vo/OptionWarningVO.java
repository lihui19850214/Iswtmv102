package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 OptionWarningVO 
*/
public class OptionWarningVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;


    /**
     * @fieldName time
    * @fieldType  Timestamp
    * @Description  预警时间 开始时间
     */
    private Timestamp timeBegin;
    /**
     * @fieldName time
    * @fieldType  Timestamp
    * @Description  预警时间 结束时间
     */
    private Timestamp timeEnd;
    /**
     * @fieldName time
    * @fieldType  Timestamp
    * @Description  预警时间
     */
    private Timestamp time;

    /**
     * @fieldName optionUserCode
    * @fieldType  String
    * @Description  操作人编码
     */
    private String optionUserCode;

    /**
     * @fieldName optionUserName
    * @fieldType  String
    * @Description  操作人姓名
     */
    private String optionUserName;

    /**
     * @fieldName option
    * @fieldType  String
    * @Description  操作
     */
    private String option;

    /**
     * @fieldName authorizedUserCode
    * @fieldType  String
    * @Description  授权人编码
     */
    private String authorizedUserCode;

    /**
     * @fieldName authorizedUserName
    * @fieldType  String
    * @Description  授权人姓名
     */
    private String authorizedUserName;

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


    /* id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Timestamp timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Timestamp getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    public String getOptionUserCode() {
        return optionUserCode;
    }

    public void setOptionUserCode(String optionUserCode) {
        this.optionUserCode = optionUserCode;
    }
    public String getOptionUserName() {
        return optionUserName;
    }

    public void setOptionUserName(String optionUserName) {
        this.optionUserName = optionUserName;
    }
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
    public String getAuthorizedUserCode() {
        return authorizedUserCode;
    }

    public void setAuthorizedUserCode(String authorizedUserCode) {
        this.authorizedUserCode = authorizedUserCode;
    }
    public String getAuthorizedUserName() {
        return authorizedUserName;
    }

    public void setAuthorizedUserName(String authorizedUserName) {
        this.authorizedUserName = authorizedUserName;
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
