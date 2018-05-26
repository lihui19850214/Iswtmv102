package com.apiclient.vo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolConfigVO 
*/
public class SynthesisCuttingToolConfigVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;


    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  String
    * @Description  合成刀编码
     */
    private String synthesisCuttingToolCode;

    /**
     * @fieldName synthesisCuttingToolTypeId
    * @fieldType  Integer
    * @Description  合成刀类型 1复合刀具 2刀片 3热套 4一体刀 5
     */
    private Integer synthesisCuttingToolTypeId;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName count
    * @fieldType  Integer
    * @Description  数量
     */
    private Integer count;

    /**
     * @fieldName picurl
    * @fieldType  String
    * @Description  图纸url
     */
    private String picurl;

    /**
     * @fieldName location
    * @fieldType  Integer
    * @Description  位置
     */
    private Integer location;

    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  合成刀编码
     */
    private SynthesisCuttingToolVO synthesisCuttingToolVO;
    /**
     * @fieldName synthesisCuttingToolTypeId
    * @fieldType  
    * @Description  合成刀类型 1复合刀具 2刀片 3热套 4一体刀 5
     */
    private SynthesisCuttingToolTypeVO synthesisCuttingToolTypeVO;

    private List<SynthesisCuttingToolLocationConfigVO> synthesisCuttingToolLocationConfigVOList;

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

    public String getSynthesisCuttingToolCode() {
        return synthesisCuttingToolCode;
    }

    public void setSynthesisCuttingToolCode(String synthesisCuttingToolCode) {
        this.synthesisCuttingToolCode = synthesisCuttingToolCode;
    }
    public Integer getSynthesisCuttingToolTypeId() {
        return synthesisCuttingToolTypeId;
    }

    public void setSynthesisCuttingToolTypeId(Integer synthesisCuttingToolTypeId) {
        this.synthesisCuttingToolTypeId = synthesisCuttingToolTypeId;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public SynthesisCuttingToolVO getSynthesisCuttingToolVO() {
        return synthesisCuttingToolVO;
    }

    public void setSynthesisCuttingToolVO(SynthesisCuttingToolVO synthesisCuttingToolVO) {
        this.synthesisCuttingToolVO = synthesisCuttingToolVO;
    }
    public SynthesisCuttingToolTypeVO getSynthesisCuttingToolTypeVO() {
        return synthesisCuttingToolTypeVO;
    }

    public void setSynthesisCuttingToolTypeVO(SynthesisCuttingToolTypeVO synthesisCuttingToolTypeVO) {
        this.synthesisCuttingToolTypeVO = synthesisCuttingToolTypeVO;
    }

    public List<SynthesisCuttingToolLocationConfigVO> getSynthesisCuttingToolLocationConfigVOList() {
        return synthesisCuttingToolLocationConfigVOList;
    }

    public void setSynthesisCuttingToolLocationConfigVOList(List<SynthesisCuttingToolLocationConfigVO> synthesisCuttingToolLocationConfigVOList) {
        this.synthesisCuttingToolLocationConfigVOList = synthesisCuttingToolLocationConfigVOList;
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
