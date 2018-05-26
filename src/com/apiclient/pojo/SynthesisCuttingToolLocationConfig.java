package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolLocationConfig 
*/
public class SynthesisCuttingToolLocationConfig implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;

    /**
     * @fieldName location
    * @fieldType  Integer
    * @Description  位置号
     */
    private Integer location;
    /**
     * @fieldName count
    * @fieldType  Integer
    * @Description  材料刀数量
     */
    private Integer count;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;
    /**
     * @fieldName replaceCuttingToolCode1
    * @fieldType  String
    * @Description  
     */
    private String replaceCuttingToolCode1;
    /**
     * @fieldName replaceCuttingToolCode2
    * @fieldType  String
    * @Description  
     */
    private String replaceCuttingToolCode2;
    /**
     * @fieldName replaceCuttingToolCode3
    * @fieldType  String
    * @Description  
     */
    private String replaceCuttingToolCode3;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  材料刀
     */
    private String cuttingToolCode;
    /**
     * @fieldName synthesisCuttingToolConfigId
    * @fieldType  Integer
    * @Description  
     */
    private Integer synthesisCuttingToolConfigId;
    /**
     * @fieldName type
    * @fieldType  String
    * @Description  材料刀类型
     */
    private String type;

    /**
     * @fieldName replaceCuttingToolCode1
    * @fieldType  
    * @Description  
     */
    private CuttingTool replaceCuttingTool1;
    /**
     * @fieldName replaceCuttingToolCode2
    * @fieldType  
    * @Description  
     */
    private CuttingTool replaceCuttingTool2;
    /**
     * @fieldName replaceCuttingToolCode3
    * @fieldType  
    * @Description  
     */
    private CuttingTool replaceCuttingTool3;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  材料刀
     */
    private CuttingTool cuttingTool;
    /**
     * @fieldName synthesisCuttingToolConfigId
    * @fieldType  
    * @Description  
     */
    private SynthesisCuttingToolConfig synthesisCuttingToolConfig;



    /* 标识 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public String getReplaceCuttingToolCode1() {
        return replaceCuttingToolCode1;
    }

    public void setReplaceCuttingToolCode1(String replaceCuttingToolCode1) {
        this.replaceCuttingToolCode1 = replaceCuttingToolCode1;
    }
    public String getReplaceCuttingToolCode2() {
        return replaceCuttingToolCode2;
    }

    public void setReplaceCuttingToolCode2(String replaceCuttingToolCode2) {
        this.replaceCuttingToolCode2 = replaceCuttingToolCode2;
    }
    public String getReplaceCuttingToolCode3() {
        return replaceCuttingToolCode3;
    }

    public void setReplaceCuttingToolCode3(String replaceCuttingToolCode3) {
        this.replaceCuttingToolCode3 = replaceCuttingToolCode3;
    }
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public Integer getSynthesisCuttingToolConfigId() {
        return synthesisCuttingToolConfigId;
    }

    public void setSynthesisCuttingToolConfigId(Integer synthesisCuttingToolConfigId) {
        this.synthesisCuttingToolConfigId = synthesisCuttingToolConfigId;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CuttingTool getReplaceCuttingTool1() {
        return replaceCuttingTool1;
    }

    public void setReplaceCuttingTool1(CuttingTool replaceCuttingTool1) {
        this.replaceCuttingTool1 = replaceCuttingTool1;
    }

    public CuttingTool getReplaceCuttingTool2() {
        return replaceCuttingTool2;
    }

    public void setReplaceCuttingTool2(CuttingTool replaceCuttingTool2) {
        this.replaceCuttingTool2 = replaceCuttingTool2;
    }

    public CuttingTool getReplaceCuttingTool3() {
        return replaceCuttingTool3;
    }

    public void setReplaceCuttingTool3(CuttingTool replaceCuttingTool3) {
        this.replaceCuttingTool3 = replaceCuttingTool3;
    }

    public CuttingTool getCuttingTool() {
        return cuttingTool;
    }

    public void setCuttingTool(CuttingTool cuttingTool) {
        this.cuttingTool = cuttingTool;
    }

    public SynthesisCuttingToolConfig getSynthesisCuttingToolConfig() {
        return synthesisCuttingToolConfig;
    }

    public void setSynthesisCuttingToolConfig(SynthesisCuttingToolConfig synthesisCuttingToolConfig) {
        this.synthesisCuttingToolConfig = synthesisCuttingToolConfig;
    }


}
