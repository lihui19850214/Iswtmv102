package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingTool 
*/
public class SynthesisCuttingTool implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;

    /**
     * @fieldName code
    * @fieldType  String
    * @Description  编码
     */
    private String code;
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
    private SynthesisCuttingToolType synthesisCuttingToolType;

    private List<ProductLine> productLineList;
    private List<SynthesisCuttingToolBind> synthesisCuttingToolBindList;
    private List<SynthesisCuttingToolConfig> synthesisCuttingToolConfigList;
    private List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList;


    /* id */
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

    public SynthesisCuttingToolType getSynthesisCuttingToolType() {
        return synthesisCuttingToolType;
    }

    public void setSynthesisCuttingToolType(SynthesisCuttingToolType synthesisCuttingToolType) {
        this.synthesisCuttingToolType = synthesisCuttingToolType;
    }

    public List<ProductLine> getProductLineList() {
        return productLineList;
    }

    public void setProductLineList(List<ProductLine> productLineList) {
        this.productLineList = productLineList;
    }
    public List<SynthesisCuttingToolBind> getSynthesisCuttingToolBindList() {
        return synthesisCuttingToolBindList;
    }

    public void setSynthesisCuttingToolBindList(List<SynthesisCuttingToolBind> synthesisCuttingToolBindList) {
        this.synthesisCuttingToolBindList = synthesisCuttingToolBindList;
    }
    public List<SynthesisCuttingToolConfig> getSynthesisCuttingToolConfigList() {
        return synthesisCuttingToolConfigList;
    }

    public void setSynthesisCuttingToolConfigList(List<SynthesisCuttingToolConfig> synthesisCuttingToolConfigList) {
        this.synthesisCuttingToolConfigList = synthesisCuttingToolConfigList;
    }
    public List<SynthesisCuttingToolLocation> getSynthesisCuttingToolLocationList() {
        return synthesisCuttingToolLocationList;
    }

    public void setSynthesisCuttingToolLocationList(List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList) {
        this.synthesisCuttingToolLocationList = synthesisCuttingToolLocationList;
    }

}
