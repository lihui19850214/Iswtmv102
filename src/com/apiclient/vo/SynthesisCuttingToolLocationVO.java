package com.apiclient.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolLocationVO 
*/
public class SynthesisCuttingToolLocationVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;


    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  String
    * @Description  合成刀编码
     */
    private String synthesisCuttingToolCode;

    /**
     * @fieldName location
    * @fieldType  Integer
    * @Description  位置号
     */
    private Integer location;

    /**
     * @fieldName count
    * @fieldType  Integer
    * @Description  数量
     */
    private Integer count;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  材料刀编码
     */
    private String cuttingToolCode;

    /**
     * @fieldName synthesisCuttingToolBindCode
    * @fieldType  String
    * @Description  绑定合成刀编码
     */
    private String synthesisCuttingToolBindCode;

    /**
     * @fieldName synthesisCuttingToolBindRfid
    * @fieldType  String
    * @Description  绑定合成刀rfid编码
     */
    private String synthesisCuttingToolBindRfid;

    /**
     * @fieldName cuttingToolBladeCode
    * @fieldType  String
    * @Description  
     */
    private String cuttingToolBladeCode;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  材料刀编码
     */
    private CuttingToolVO cuttingToolVO;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  合成刀编码
     */
    private SynthesisCuttingToolVO synthesisCuttingToolVO;
    /**
     * @fieldName synthesisCuttingToolBindCode
    * @fieldType  
    * @Description  绑定合成刀编码
     */
    private SynthesisCuttingToolBindVO synthesisCuttingToolBindVO;


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

    public String getSynthesisCuttingToolCode() {
        return synthesisCuttingToolCode;
    }

    public void setSynthesisCuttingToolCode(String synthesisCuttingToolCode) {
        this.synthesisCuttingToolCode = synthesisCuttingToolCode;
    }
    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public String getSynthesisCuttingToolBindCode() {
        return synthesisCuttingToolBindCode;
    }

    public void setSynthesisCuttingToolBindCode(String synthesisCuttingToolBindCode) {
        this.synthesisCuttingToolBindCode = synthesisCuttingToolBindCode;
    }
    public String getSynthesisCuttingToolBindRfid() {
        return synthesisCuttingToolBindRfid;
    }

    public void setSynthesisCuttingToolBindRfid(String synthesisCuttingToolBindRfid) {
        this.synthesisCuttingToolBindRfid = synthesisCuttingToolBindRfid;
    }
    public String getCuttingToolBladeCode() {
        return cuttingToolBladeCode;
    }

    public void setCuttingToolBladeCode(String cuttingToolBladeCode) {
        this.cuttingToolBladeCode = cuttingToolBladeCode;
    }

    public CuttingToolVO getCuttingToolVO() {
        return cuttingToolVO;
    }

    public void setCuttingToolVO(CuttingToolVO cuttingToolVO) {
        this.cuttingToolVO = cuttingToolVO;
    }
    public SynthesisCuttingToolVO getSynthesisCuttingToolVO() {
        return synthesisCuttingToolVO;
    }

    public void setSynthesisCuttingToolVO(SynthesisCuttingToolVO synthesisCuttingToolVO) {
        this.synthesisCuttingToolVO = synthesisCuttingToolVO;
    }
    public SynthesisCuttingToolBindVO getSynthesisCuttingToolBindVO() {
        return synthesisCuttingToolBindVO;
    }

    public void setSynthesisCuttingToolBindVO(SynthesisCuttingToolBindVO synthesisCuttingToolBindVO) {
        this.synthesisCuttingToolBindVO = synthesisCuttingToolBindVO;
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
