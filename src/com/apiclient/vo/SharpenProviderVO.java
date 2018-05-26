package com.apiclient.vo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SharpenProviderVO 
*/
public class SharpenProviderVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  供应商id
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
    * @Description  名称
     */
    private String name;

    /**
     * @fieldName type
    * @fieldType  Integer
    * @Description  类型
     */
    private Integer type;

    /**
     * @fieldName address
    * @fieldType  String
    * @Description  地址
     */
    private String address;

    /**
     * @fieldName contact
    * @fieldType  String
    * @Description  
     */
    private String contact;

    /**
     * @fieldName tell
    * @fieldType  String
    * @Description  联系人电话
     */
    private String tell;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;


    private List<OutsideFactoryVO> outsideFactoryVOList;
    private List<OutsidefactoryhistoryVO> outsidefactoryhistoryVOList;

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


    /* 供应商id */
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
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }


    public List<OutsideFactoryVO> getOutsideFactoryVOList() {
        return outsideFactoryVOList;
    }

    public void setOutsideFactoryVOList(List<OutsideFactoryVO> outsideFactoryVOList) {
        this.outsideFactoryVOList = outsideFactoryVOList;
    }
    public List<OutsidefactoryhistoryVO> getOutsidefactoryhistoryVOList() {
        return outsidefactoryhistoryVOList;
    }

    public void setOutsidefactoryhistoryVOList(List<OutsidefactoryhistoryVO> outsidefactoryhistoryVOList) {
        this.outsidefactoryhistoryVOList = outsidefactoryhistoryVOList;
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
