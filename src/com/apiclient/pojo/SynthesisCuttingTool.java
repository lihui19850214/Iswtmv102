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


    private List<CuttingToolBindleRecords> cuttingToolBindleRecordsList;
    private List<ProductLine> productLineList;
    private List<SynthesisBladeCode> synthesisBladeCodeList;
    private List<SynthesisCuttingToolBind> synthesisCuttingToolBindList;
    private List<SynthesisCuttingToolBindleRecords> synthesisCuttingToolBindleRecordsList;
    private List<SynthesisCuttingToolConfig> synthesisCuttingToolConfigList;
    private List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList;
    private List<SynthesisCuttingToolMaterialInventory> synthesisCuttingToolMaterialInventoryList;


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


    public List<CuttingToolBindleRecords> getCuttingToolBindleRecordsList() {
        return cuttingToolBindleRecordsList;
    }

    public void setCuttingToolBindleRecordsList(List<CuttingToolBindleRecords> cuttingToolBindleRecordsList) {
        this.cuttingToolBindleRecordsList = cuttingToolBindleRecordsList;
    }
    public List<ProductLine> getProductLineList() {
        return productLineList;
    }

    public void setProductLineList(List<ProductLine> productLineList) {
        this.productLineList = productLineList;
    }
    public List<SynthesisBladeCode> getSynthesisBladeCodeList() {
        return synthesisBladeCodeList;
    }

    public void setSynthesisBladeCodeList(List<SynthesisBladeCode> synthesisBladeCodeList) {
        this.synthesisBladeCodeList = synthesisBladeCodeList;
    }
    public List<SynthesisCuttingToolBind> getSynthesisCuttingToolBindList() {
        return synthesisCuttingToolBindList;
    }

    public void setSynthesisCuttingToolBindList(List<SynthesisCuttingToolBind> synthesisCuttingToolBindList) {
        this.synthesisCuttingToolBindList = synthesisCuttingToolBindList;
    }
    public List<SynthesisCuttingToolBindleRecords> getSynthesisCuttingToolBindleRecordsList() {
        return synthesisCuttingToolBindleRecordsList;
    }

    public void setSynthesisCuttingToolBindleRecordsList(List<SynthesisCuttingToolBindleRecords> synthesisCuttingToolBindleRecordsList) {
        this.synthesisCuttingToolBindleRecordsList = synthesisCuttingToolBindleRecordsList;
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
    public List<SynthesisCuttingToolMaterialInventory> getSynthesisCuttingToolMaterialInventoryList() {
        return synthesisCuttingToolMaterialInventoryList;
    }

    public void setSynthesisCuttingToolMaterialInventoryList(List<SynthesisCuttingToolMaterialInventory> synthesisCuttingToolMaterialInventoryList) {
        this.synthesisCuttingToolMaterialInventoryList = synthesisCuttingToolMaterialInventoryList;
    }

}
