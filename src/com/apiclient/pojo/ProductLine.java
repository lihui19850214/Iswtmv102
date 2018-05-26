package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 ProductLine 
*/
public class ProductLine implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  关联关系id
     */
    private Integer id;

    /**
     * @fieldName productLineCode
    * @fieldType  String
    * @Description  生产线编码
     */
    private String productLineCode;
    /**
     * @fieldName axleCode
    * @fieldType  String
    * @Description  轴ID
     */
    private String axleCode;
    /**
     * @fieldName assemblylineCode
    * @fieldType  String
    * @Description  流水线ID
     */
    private String assemblylineCode;
    /**
     * @fieldName equipmentCode
    * @fieldType  String
    * @Description  设备ID
     */
    private String equipmentCode;
    /**
     * @fieldName processCode
    * @fieldType  String
    * @Description  
     */
    private String processCode;
    /**
     * @fieldName toolDurable
    * @fieldType  Integer
    * @Description  
     */
    private Integer toolDurable;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName partsCode
    * @fieldType  String
    * @Description  零件
     */
    private String partsCode;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  String
    * @Description  合成刀编码
     */
    private String synthesisCuttingToolCode;

    /**
     * @fieldName axleCode
    * @fieldType  
    * @Description  轴ID
     */
    private ProductLineAxle productLineAxle;
    /**
     * @fieldName assemblylineCode
    * @fieldType  
    * @Description  流水线ID
     */
    private ProductLineAssemblyline productLineAssemblyline;
    /**
     * @fieldName equipmentCode
    * @fieldType  
    * @Description  设备ID
     */
    private ProductLineEquipment productLineEquipment;
    /**
     * @fieldName processCode
    * @fieldType  
    * @Description  
     */
    private ProductLineProcess productLineProcess;
    /**
     * @fieldName partsCode
    * @fieldType  
    * @Description  零件
     */
    private ProductLineParts productLineParts;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  合成刀编码
     */
    private SynthesisCuttingTool synthesisCuttingTool;



    /* 关联关系id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductLineCode() {
        return productLineCode;
    }

    public void setProductLineCode(String productLineCode) {
        this.productLineCode = productLineCode;
    }
    public String getAxleCode() {
        return axleCode;
    }

    public void setAxleCode(String axleCode) {
        this.axleCode = axleCode;
    }
    public String getAssemblylineCode() {
        return assemblylineCode;
    }

    public void setAssemblylineCode(String assemblylineCode) {
        this.assemblylineCode = assemblylineCode;
    }
    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }
    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }
    public Integer getToolDurable() {
        return toolDurable;
    }

    public void setToolDurable(Integer toolDurable) {
        this.toolDurable = toolDurable;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public String getPartsCode() {
        return partsCode;
    }

    public void setPartsCode(String partsCode) {
        this.partsCode = partsCode;
    }
    public String getSynthesisCuttingToolCode() {
        return synthesisCuttingToolCode;
    }

    public void setSynthesisCuttingToolCode(String synthesisCuttingToolCode) {
        this.synthesisCuttingToolCode = synthesisCuttingToolCode;
    }

    public ProductLineAxle getProductLineAxle() {
        return productLineAxle;
    }

    public void setProductLineAxle(ProductLineAxle productLineAxle) {
        this.productLineAxle = productLineAxle;
    }
    public ProductLineAssemblyline getProductLineAssemblyline() {
        return productLineAssemblyline;
    }

    public void setProductLineAssemblyline(ProductLineAssemblyline productLineAssemblyline) {
        this.productLineAssemblyline = productLineAssemblyline;
    }
    public ProductLineEquipment getProductLineEquipment() {
        return productLineEquipment;
    }

    public void setProductLineEquipment(ProductLineEquipment productLineEquipment) {
        this.productLineEquipment = productLineEquipment;
    }
    public ProductLineProcess getProductLineProcess() {
        return productLineProcess;
    }

    public void setProductLineProcess(ProductLineProcess productLineProcess) {
        this.productLineProcess = productLineProcess;
    }
    public ProductLineParts getProductLineParts() {
        return productLineParts;
    }

    public void setProductLineParts(ProductLineParts productLineParts) {
        this.productLineParts = productLineParts;
    }
    public SynthesisCuttingTool getSynthesisCuttingTool() {
        return synthesisCuttingTool;
    }

    public void setSynthesisCuttingTool(SynthesisCuttingTool synthesisCuttingTool) {
        this.synthesisCuttingTool = synthesisCuttingTool;
    }


}
