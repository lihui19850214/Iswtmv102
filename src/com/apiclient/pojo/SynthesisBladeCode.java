package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisBladeCode 
*/
public class SynthesisBladeCode implements Serializable{

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
    private SynthesisCuttingTool synthesisCuttingTool;



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

    public SynthesisCuttingTool getSynthesisCuttingTool() {
        return synthesisCuttingTool;
    }

    public void setSynthesisCuttingTool(SynthesisCuttingTool synthesisCuttingTool) {
        this.synthesisCuttingTool = synthesisCuttingTool;
    }


}
