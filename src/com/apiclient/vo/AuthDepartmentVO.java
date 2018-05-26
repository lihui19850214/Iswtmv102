package com.apiclient.vo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthDepartmentVO 
*/
public class AuthDepartmentVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  部门id
     */
    private Integer id;


    /**
     * @fieldName code
    * @fieldType  String
    * @Description  部门编码
     */
    private String code;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  部门名称
     */
    private String name;

    /**
     * @fieldName phone
    * @fieldType  String
    * @Description  部门电话
     */
    private String phone;

    /**
     * @fieldName leaderName
    * @fieldType  String
    * @Description  部门负责人
     */
    private String leaderName;

    /**
     * @fieldName leaderMobile
    * @fieldType  String
    * @Description  负责人手机
     */
    private String leaderMobile;

    /**
     * @fieldName description
    * @fieldType  String
    * @Description  描述
     */
    private String description;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;

    /**
     * @fieldName authAgencyCode
    * @fieldType  Integer
    * @Description  
     */
    private Integer authAgencyCode;

    /**
     * @fieldName authAgencyCode
    * @fieldType  
    * @Description  
     */
    private AuthAgencyVO authAgencyVO;

    private List<AuthCustomerVO> authCustomerVOList;
    private List<AuthPositionVO> authPositionVOList;

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


    /* 部门id */
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
    public String getLeaderMobile() {
        return leaderMobile;
    }

    public void setLeaderMobile(String leaderMobile) {
        this.leaderMobile = leaderMobile;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Integer getAuthAgencyCode() {
        return authAgencyCode;
    }

    public void setAuthAgencyCode(Integer authAgencyCode) {
        this.authAgencyCode = authAgencyCode;
    }

    public AuthAgencyVO getAuthAgencyVO() {
        return authAgencyVO;
    }

    public void setAuthAgencyVO(AuthAgencyVO authAgencyVO) {
        this.authAgencyVO = authAgencyVO;
    }

    public List<AuthCustomerVO> getAuthCustomerVOList() {
        return authCustomerVOList;
    }

    public void setAuthCustomerVOList(List<AuthCustomerVO> authCustomerVOList) {
        this.authCustomerVOList = authCustomerVOList;
    }
    public List<AuthPositionVO> getAuthPositionVOList() {
        return authPositionVOList;
    }

    public void setAuthPositionVOList(List<AuthPositionVO> authPositionVOList) {
        this.authPositionVOList = authPositionVOList;
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
