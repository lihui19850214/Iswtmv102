package com.apiclient.vo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 DjMtlAkpVO 
*/
public class DjMtlAkpVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName ownerCode
    * @fieldType  String
    * @Description  供应商代码
     */
    private String ownerCode;


    /**
     * @fieldName mtlCode
    * @fieldType  String
    * @Description  物料代码
     */
    private String mtlCode;

    /**
     * @fieldName mtlName
    * @fieldType  String
    * @Description  物料名称
     */
    private String mtlName;

    /**
     * @fieldName susr11
    * @fieldType  String
    * @Description  刀具号
     */
    private String susr11;

    /**
     * @fieldName susr13
    * @fieldType  String
    * @Description  外委标识，W外委，N非外委
     */
    private String susr13;

    /**
     * @fieldName susr18
    * @fieldType  String
    * @Description  最大刃磨次数
     */
    private String susr18;

    /**
     * @fieldName susr20
    * @fieldType  String
    * @Description  最大刀头号
     */
    private String susr20;

    /**
     * @fieldName specification
    * @fieldType  String
    * @Description  规格型号
     */
    private String specification;

    /**
     * @fieldName brand
    * @fieldType  String
    * @Description  刀具品牌
     */
    private String brand;

    /**
     * @fieldName orderCode
    * @fieldType  String
    * @Description  刀具订货号
     */
    private String orderCode;



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


    /* 供应商代码 */
    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getMtlCode() {
        return mtlCode;
    }

    public void setMtlCode(String mtlCode) {
        this.mtlCode = mtlCode;
    }
    public String getMtlName() {
        return mtlName;
    }

    public void setMtlName(String mtlName) {
        this.mtlName = mtlName;
    }
    public String getSusr11() {
        return susr11;
    }

    public void setSusr11(String susr11) {
        this.susr11 = susr11;
    }
    public String getSusr13() {
        return susr13;
    }

    public void setSusr13(String susr13) {
        this.susr13 = susr13;
    }
    public String getSusr18() {
        return susr18;
    }

    public void setSusr18(String susr18) {
        this.susr18 = susr18;
    }
    public String getSusr20() {
        return susr20;
    }

    public void setSusr20(String susr20) {
        this.susr20 = susr20;
    }
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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
