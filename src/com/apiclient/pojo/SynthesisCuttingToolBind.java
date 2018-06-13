package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolBind 
*/
public class SynthesisCuttingToolBind implements Serializable{

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
    * @Description  绑定编码
     */
    private String code;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;
    /**
     * @fieldName createTime
    * @fieldType  Timestamp
    * @Description  创建时间
     */
    private Timestamp createTime;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  String
    * @Description  合成刀编码
     */
    private String synthesisCuttingToolCode;
    /**
     * @fieldName rfidContainerCode
    * @fieldType  String
    * @Description  RFID标签
     */
    private String rfidContainerCode;
    /**
     * @fieldName synthesisCode
    * @fieldType  String
    * @Description  合成刀业务编码
     */
    private String synthesisCode;
    /**
     * @fieldName bladeCode
    * @fieldType  Integer
    * @Description  刀身码
     */
    private String bladeCode;

    /**
     * @fieldName rfidContainerCode
    * @fieldType  
    * @Description  RFID标签
     */
    private RfidContainer rfidContainer;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  合成刀编码
     */
    private SynthesisCuttingTool synthesisCuttingTool;

    private List<SynthesisCuttingToolBindleRecords> synthesisCuttingToolBindleRecordsList;
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
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    public String getSynthesisCuttingToolCode() {
        return synthesisCuttingToolCode;
    }

    public void setSynthesisCuttingToolCode(String synthesisCuttingToolCode) {
        this.synthesisCuttingToolCode = synthesisCuttingToolCode;
    }
    public String getRfidContainerCode() {
        return rfidContainerCode;
    }

    public void setRfidContainerCode(String rfidContainerCode) {
        this.rfidContainerCode = rfidContainerCode;
    }
    public String getSynthesisCode() {
        return synthesisCode;
    }

    public void setSynthesisCode(String synthesisCode) {
        this.synthesisCode = synthesisCode;
    }
    public String getBladeCode() {
        return bladeCode;
    }

    public void setBladeCode(String bladeCode) {
        this.bladeCode = bladeCode;
    }

    public RfidContainer getRfidContainer() {
        return rfidContainer;
    }

    public void setRfidContainer(RfidContainer rfidContainer) {
        this.rfidContainer = rfidContainer;
    }
    public SynthesisCuttingTool getSynthesisCuttingTool() {
        return synthesisCuttingTool;
    }

    public void setSynthesisCuttingTool(SynthesisCuttingTool synthesisCuttingTool) {
        this.synthesisCuttingTool = synthesisCuttingTool;
    }

    public List<SynthesisCuttingToolBindleRecords> getSynthesisCuttingToolBindleRecordsList() {
        return synthesisCuttingToolBindleRecordsList;
    }

    public void setSynthesisCuttingToolBindleRecordsList(List<SynthesisCuttingToolBindleRecords> synthesisCuttingToolBindleRecordsList) {
        this.synthesisCuttingToolBindleRecordsList = synthesisCuttingToolBindleRecordsList;
    }
    public List<SynthesisCuttingToolLocation> getSynthesisCuttingToolLocationList() {
        return synthesisCuttingToolLocationList;
    }

    public void setSynthesisCuttingToolLocationList(List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList) {
        this.synthesisCuttingToolLocationList = synthesisCuttingToolLocationList;
    }

}
