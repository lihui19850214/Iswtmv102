package com.apiclient.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolLocation 
*/
public class SynthesisCuttingToolLocation implements Serializable{

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
    private CuttingTool cuttingTool;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  合成刀编码
     */
    private SynthesisCuttingTool synthesisCuttingTool;
    /**
     * @fieldName synthesisCuttingToolBindCode
    * @fieldType  
    * @Description  绑定合成刀编码
     */
    private SynthesisCuttingToolBind synthesisCuttingToolBind;



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

    public CuttingTool getCuttingTool() {
        return cuttingTool;
    }

    public void setCuttingTool(CuttingTool cuttingTool) {
        this.cuttingTool = cuttingTool;
    }
    public SynthesisCuttingTool getSynthesisCuttingTool() {
        return synthesisCuttingTool;
    }

    public void setSynthesisCuttingTool(SynthesisCuttingTool synthesisCuttingTool) {
        this.synthesisCuttingTool = synthesisCuttingTool;
    }
    public SynthesisCuttingToolBind getSynthesisCuttingToolBind() {
        return synthesisCuttingToolBind;
    }

    public void setSynthesisCuttingToolBind(SynthesisCuttingToolBind synthesisCuttingToolBind) {
        this.synthesisCuttingToolBind = synthesisCuttingToolBind;
    }


}
