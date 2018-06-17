package com.apiclient.vo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisBladeCodeVO 
*/
public class SynthesisBladeCodeVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;


    /**
     * @fieldName code
    * @fieldType  String
    * @Description  唯一code
     */
    private String code;

    /**
     * @fieldName bladeCode
    * @fieldType  String
    * @Description  刀身码
     */
    private String bladeCode;

    /**
     * @fieldName status
    * @fieldType  String
    * @Description  状态 1已使用 
     */
    private String status;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName maxValue
    * @fieldType  Integer
    * @Description  范围最大值
     */
    private Integer maxValue;

    /**
     * @fieldName minValue
    * @fieldType  Integer
    * @Description  范围最小值
     */
    private Integer minValue;

    /**
      * @fieldName synthesisCode
     * @fieldType  String
     * @Description  合成刀T号
     */
    private String synthesisCode;

    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  String
    * @Description  
     */
    private String synthesisCuttingToolCode;

    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  
     */
    private SynthesisCuttingToolVO synthesisCuttingToolVO;


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

    public String getSynthesisCode() {
        return synthesisCode;
    }

    public void setSynthesisCode(String synthesisCode) {
        this.synthesisCode = synthesisCode;
    }

    /* 标识 */
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
    public String getBladeCode() {
        return bladeCode;
    }

    public void setBladeCode(String bladeCode) {
        this.bladeCode = bladeCode;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }
    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }
    public String getSynthesisCuttingToolCode() {
        return synthesisCuttingToolCode;
    }

    public void setSynthesisCuttingToolCode(String synthesisCuttingToolCode) {
        this.synthesisCuttingToolCode = synthesisCuttingToolCode;
    }

    public SynthesisCuttingToolVO getSynthesisCuttingToolVO() {
        return synthesisCuttingToolVO;
    }

    public void setSynthesisCuttingToolVO(SynthesisCuttingToolVO synthesisCuttingToolVO) {
        this.synthesisCuttingToolVO = synthesisCuttingToolVO;
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
