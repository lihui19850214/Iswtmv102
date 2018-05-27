package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolConfig 
*/
public class SynthesisCuttingToolConfig implements Serializable {

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
    private SynthesisCuttingTool synthesisCuttingTool;
    /**
     * @fieldName synthesisCuttingToolTypeId
    * @fieldType  
    * @Description  合成刀类型 1复合刀具 2刀片 3热套 4一体刀 5
     */
    private SynthesisCuttingToolType synthesisCuttingToolType;

    private List<SynthesisCuttingToolLocationConfig> synthesisCuttingToolLocationConfigList;


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

    public SynthesisCuttingTool getSynthesisCuttingTool() {
        return synthesisCuttingTool;
    }

    public void setSynthesisCuttingTool(SynthesisCuttingTool synthesisCuttingTool) {
        this.synthesisCuttingTool = synthesisCuttingTool;
    }
    public SynthesisCuttingToolType getSynthesisCuttingToolType() {
        return synthesisCuttingToolType;
    }

    public void setSynthesisCuttingToolType(SynthesisCuttingToolType synthesisCuttingToolType) {
        this.synthesisCuttingToolType = synthesisCuttingToolType;
    }

    public List<SynthesisCuttingToolLocationConfig> getSynthesisCuttingToolLocationConfigList() {
        return synthesisCuttingToolLocationConfigList;
    }

    public void setSynthesisCuttingToolLocationConfigList(List<SynthesisCuttingToolLocationConfig> synthesisCuttingToolLocationConfigList) {
        this.synthesisCuttingToolLocationConfigList = synthesisCuttingToolLocationConfigList;
    }

}
