package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthAgencyVO 
*/
public class AuthAgencyVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  机构id
     */
    private Integer id;


    /**
     * @fieldName code
    * @fieldType  String
    * @Description  编码
     */
    private String code;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  机构名称
     */
    private String name;

    /**
     * @fieldName tel
    * @fieldType  String
    * @Description  机构电话
     */
    private String tel;

    /**
     * @fieldName createDate
    * @fieldType  Timestamp
    * @Description  创建时间 开始时间
     */
    private Timestamp createDateBegin;
    /**
     * @fieldName createDate
    * @fieldType  Timestamp
    * @Description  创建时间 结束时间
     */
    private Timestamp createDateEnd;
    /**
     * @fieldName createDate
    * @fieldType  Timestamp
    * @Description  创建时间
     */
    private Timestamp createDate;

    /**
     * @fieldName corporate
    * @fieldType  String
    * @Description  法人
     */
    private String corporate;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;


    private List<AuthCustomerVO> authCustomerVOList;
    private List<AuthDepartmentVO> authDepartmentVOList;

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


    /* 机构id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    public Timestamp getCreateDateBegin() {
        return createDateBegin;
    }

    public void setCreateDateBegin(Timestamp createDateBegin) {
        this.createDateBegin = createDateBegin;
    }

    public Timestamp getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Timestamp createDateEnd) {
        this.createDateEnd = createDateEnd;
    }
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }


    public List<AuthCustomerVO> getAuthCustomerVOList() {
        return authCustomerVOList;
    }

    public void setAuthCustomerVOList(List<AuthCustomerVO> authCustomerVOList) {
        this.authCustomerVOList = authCustomerVOList;
    }
    public List<AuthDepartmentVO> getAuthDepartmentVOList() {
        return authDepartmentVOList;
    }

    public void setAuthDepartmentVOList(List<AuthDepartmentVO> authDepartmentVOList) {
        this.authDepartmentVOList = authDepartmentVOList;
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
