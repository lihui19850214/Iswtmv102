package com.apiclient.vo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolVO 
*/
public class SynthesisCuttingToolVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;


    /**
     * @fieldName synthesisCode
    * @fieldType  String
    * @Description  合成刀业务编码
     */
    private String synthesisCode;

    /**
     * @fieldName picUrl
    * @fieldType  String
    * @Description  图纸url
     */
    private String picUrl;

    /**
     * @fieldName code
    * @fieldType  String
    * @Description  编码
     */
    private String code;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName synthesisCuttingToolTypeId
    * @fieldType  Integer
    * @Description  合成刀类型 
     */
    private Integer synthesisCuttingToolTypeId;

    /**
     * @fieldName abc
    * @fieldType  String
    * @Description  
     */
    private String abc;

    /**
     * @fieldName synthesisCuttingToolTypeId
    * @fieldType  
    * @Description  合成刀类型 
     */
    private SynthesisCuttingToolTypeVO synthesisCuttingToolTypeVO;

    private List<ProductLineVO> productLineVOList;
    private List<SynthesisCuttingToolBindVO> synthesisCuttingToolBindVOList;
    private List<SynthesisCuttingToolConfigVO> synthesisCuttingToolConfigVOList;
    private List<SynthesisCuttingToolLocationVO> synthesisCuttingToolLocationVOList;
    private List<SynthesisCuttingToolMaterialInventoryVO> synthesisCuttingToolMaterialInventoryVOList;

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

    public String getSynthesisCode() {
        return synthesisCode;
    }

    public void setSynthesisCode(String synthesisCode) {
        this.synthesisCode = synthesisCode;
    }
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Integer getSynthesisCuttingToolTypeId() {
        return synthesisCuttingToolTypeId;
    }

    public void setSynthesisCuttingToolTypeId(Integer synthesisCuttingToolTypeId) {
        this.synthesisCuttingToolTypeId = synthesisCuttingToolTypeId;
    }
    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public SynthesisCuttingToolTypeVO getSynthesisCuttingToolTypeVO() {
        return synthesisCuttingToolTypeVO;
    }

    public void setSynthesisCuttingToolTypeVO(SynthesisCuttingToolTypeVO synthesisCuttingToolTypeVO) {
        this.synthesisCuttingToolTypeVO = synthesisCuttingToolTypeVO;
    }

    public List<ProductLineVO> getProductLineVOList() {
        return productLineVOList;
    }

    public void setProductLineVOList(List<ProductLineVO> productLineVOList) {
        this.productLineVOList = productLineVOList;
    }
    public List<SynthesisCuttingToolBindVO> getSynthesisCuttingToolBindVOList() {
        return synthesisCuttingToolBindVOList;
    }

    public void setSynthesisCuttingToolBindVOList(List<SynthesisCuttingToolBindVO> synthesisCuttingToolBindVOList) {
        this.synthesisCuttingToolBindVOList = synthesisCuttingToolBindVOList;
    }
    public List<SynthesisCuttingToolConfigVO> getSynthesisCuttingToolConfigVOList() {
        return synthesisCuttingToolConfigVOList;
    }

    public void setSynthesisCuttingToolConfigVOList(List<SynthesisCuttingToolConfigVO> synthesisCuttingToolConfigVOList) {
        this.synthesisCuttingToolConfigVOList = synthesisCuttingToolConfigVOList;
    }
    public List<SynthesisCuttingToolLocationVO> getSynthesisCuttingToolLocationVOList() {
        return synthesisCuttingToolLocationVOList;
    }

    public void setSynthesisCuttingToolLocationVOList(List<SynthesisCuttingToolLocationVO> synthesisCuttingToolLocationVOList) {
        this.synthesisCuttingToolLocationVOList = synthesisCuttingToolLocationVOList;
    }
    public List<SynthesisCuttingToolMaterialInventoryVO> getSynthesisCuttingToolMaterialInventoryVOList() {
        return synthesisCuttingToolMaterialInventoryVOList;
    }

    public void setSynthesisCuttingToolMaterialInventoryVOList(List<SynthesisCuttingToolMaterialInventoryVO> synthesisCuttingToolMaterialInventoryVOList) {
        this.synthesisCuttingToolMaterialInventoryVOList = synthesisCuttingToolMaterialInventoryVOList;
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
