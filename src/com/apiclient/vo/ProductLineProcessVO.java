package com.apiclient.vo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 ProductLineProcessVO 
*/
public class ProductLineProcessVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  工序id
     */
    private Integer id;


    /**
     * @fieldName code
    * @fieldType  String
    * @Description  编码
     */
    private String code;

    /**
     * @fieldName assemblylineCode
    * @fieldType  String
    * @Description  流水线
     */
    private String assemblylineCode;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  名称
     */
    private String name;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;

    /**
     * @fieldName assemblylineCode
    * @fieldType  
    * @Description  流水线
     */
    private ProductLineAssemblylineVO productLineAssemblylineVO;

    private List<ProductLineVO> productLineVOList;

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


    /* 工序id */
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
    public String getAssemblylineCode() {
        return assemblylineCode;
    }

    public void setAssemblylineCode(String assemblylineCode) {
        this.assemblylineCode = assemblylineCode;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public ProductLineAssemblylineVO getProductLineAssemblylineVO() {
        return productLineAssemblylineVO;
    }

    public void setProductLineAssemblylineVO(ProductLineAssemblylineVO productLineAssemblylineVO) {
        this.productLineAssemblylineVO = productLineAssemblylineVO;
    }

    public List<ProductLineVO> getProductLineVOList() {
        return productLineVOList;
    }

    public void setProductLineVOList(List<ProductLineVO> productLineVOList) {
        this.productLineVOList = productLineVOList;
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
