package com.apiclient.vo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 PowerGroupVO 
*/
public class PowerGroupVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;


    /**
     * @fieldName name
    * @fieldType  String
    * @Description  权限组名称
     */
    private String name;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName isRole
    * @fieldType  String
    * @Description  是否为角色分组 1是 0 否
     */
    private String isRole;


    private List<PowerHasGroupVO> powerHasGroupVOList;
    private List<PowerPositionHasGroupVO> powerPositionHasGroupVOList;
    private List<PowerUserHasGroupVO> powerUserHasGroupVOList;

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


    /* 标识 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public String getIsRole() {
        return isRole;
    }

    public void setIsRole(String isRole) {
        this.isRole = isRole;
    }


    public List<PowerHasGroupVO> getPowerHasGroupVOList() {
        return powerHasGroupVOList;
    }

    public void setPowerHasGroupVOList(List<PowerHasGroupVO> powerHasGroupVOList) {
        this.powerHasGroupVOList = powerHasGroupVOList;
    }
    public List<PowerPositionHasGroupVO> getPowerPositionHasGroupVOList() {
        return powerPositionHasGroupVOList;
    }

    public void setPowerPositionHasGroupVOList(List<PowerPositionHasGroupVO> powerPositionHasGroupVOList) {
        this.powerPositionHasGroupVOList = powerPositionHasGroupVOList;
    }
    public List<PowerUserHasGroupVO> getPowerUserHasGroupVOList() {
        return powerUserHasGroupVOList;
    }

    public void setPowerUserHasGroupVOList(List<PowerUserHasGroupVO> powerUserHasGroupVOList) {
        this.powerUserHasGroupVOList = powerUserHasGroupVOList;
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
