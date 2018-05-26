package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolMaterialInventory 
*/
public class SynthesisCuttingToolMaterialInventory implements Serializable{

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
     * @fieldName synthesisCode
    * @fieldType  String
    * @Description  合成刀业务编码
     */
    private String synthesisCode;
    /**
     * @fieldName prepareLibraryCount
    * @fieldType  Integer
    * @Description  备刀库数量
     */
    private Integer prepareLibraryCount;
    /**
     * @fieldName forGrindingCount
    * @fieldType  Integer
    * @Description  待刃磨数量
     */
    private Integer forGrindingCount;
    /**
     * @fieldName inUseCount
    * @fieldType  Integer
    * @Description  安装数量
     */
    private Integer inUseCount;
    /**
     * @fieldName total
    * @fieldType  Integer
    * @Description  总数
     */
    private Integer total;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  合成刀编码
     */
    private SynthesisCuttingTool synthesisCuttingTool;



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
    public String getSynthesisCode() {
        return synthesisCode;
    }

    public void setSynthesisCode(String synthesisCode) {
        this.synthesisCode = synthesisCode;
    }
    public Integer getPrepareLibraryCount() {
        return prepareLibraryCount;
    }

    public void setPrepareLibraryCount(Integer prepareLibraryCount) {
        this.prepareLibraryCount = prepareLibraryCount;
    }
    public Integer getForGrindingCount() {
        return forGrindingCount;
    }

    public void setForGrindingCount(Integer forGrindingCount) {
        this.forGrindingCount = forGrindingCount;
    }
    public Integer getInUseCount() {
        return inUseCount;
    }

    public void setInUseCount(Integer inUseCount) {
        this.inUseCount = inUseCount;
    }
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public SynthesisCuttingTool getSynthesisCuttingTool() {
        return synthesisCuttingTool;
    }

    public void setSynthesisCuttingTool(SynthesisCuttingTool synthesisCuttingTool) {
        this.synthesisCuttingTool = synthesisCuttingTool;
    }


}
