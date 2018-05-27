package com.apiclient.vo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthPositionVO 
*/
public class AuthPositionVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  
     */
    private Integer id;


    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;

    /**
     * @fieldName code
    * @fieldType  String
    * @Description  职务编码
     */
    private String code;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  职务名称
     */
    private String name;

    /**
     * @fieldName authDepartmentCode1
    * @fieldType  Integer
    * @Description  
     */
    private Integer authDepartmentCode1;

    /**
     * @fieldName authDepartmentCode1
    * @fieldType  
    * @Description  
     */
    private AuthDepartmentVO authDepartmentVO;

    private List<AuthCustomerVO> authCustomerVOList;

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


    /*  */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
    public Integer getAuthDepartmentCode1() {
        return authDepartmentCode1;
    }

    public void setAuthDepartmentCode1(Integer authDepartmentCode1) {
        this.authDepartmentCode1 = authDepartmentCode1;
    }

    public AuthDepartmentVO getAuthDepartmentVO() {
        return authDepartmentVO;
    }

    public void setAuthDepartmentVO(AuthDepartmentVO authDepartmentVO) {
        this.authDepartmentVO = authDepartmentVO;
    }

    public List<AuthCustomerVO> getAuthCustomerVOList() {
        return authCustomerVOList;
    }

    public void setAuthCustomerVOList(List<AuthCustomerVO> authCustomerVOList) {
        this.authCustomerVOList = authCustomerVOList;
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
