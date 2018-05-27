package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 MaterialInventory 
*/
public class MaterialInventory implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  材料刀标识
     */
    private Integer id;

    /**
     * @fieldName warehouseCode
    * @fieldType  String
    * @Description  库房code
     */
    private String warehouseCode;
    /**
     * @fieldName prepareLibraryCount
    * @fieldType  Integer
    * @Description  备刀库数量
     */
    private Integer prepareLibraryCount;
    /**
     * @fieldName forGrindingInCount
    * @fieldType  Integer
    * @Description  厂内待刃磨数量
     */
    private Integer forGrindingInCount;
    /**
     * @fieldName forGrindingOutCount
    * @fieldType  Integer
    * @Description  场外带刃磨数量
     */
    private Integer forGrindingOutCount;
    /**
     * @fieldName grindingOutCount
    * @fieldType  Integer
    * @Description  场外刃磨数量
     */
    private Integer grindingOutCount;
    /**
     * @fieldName productLineCount
    * @fieldType  Integer
    * @Description  生产线数量 设备上
     */
    private Integer productLineCount;
    /**
     * @fieldName scrapCount
    * @fieldType  Integer
    * @Description  报废数量
     */
    private Integer scrapCount;
    /**
     * @fieldName cuttingToolCode
    * @fieldType  String
    * @Description  
     */
    private String cuttingToolCode;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;
    /**
     * @fieldName toInstallCount
    * @fieldType  Integer
    * @Description  待按上数量
     */
    private Integer toInstallCount;
    /**
     * @fieldName toUninstallCount
    * @fieldType  Integer
    * @Description  待卸下数量
     */
    private Integer toUninstallCount;
    /**
     * @fieldName toExchangeCount
    * @fieldType  Integer
    * @Description  待换装数量
     */
    private Integer toExchangeCount;

    /**
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  
     */
    private CuttingTool cuttingTool;



    /* 材料刀标识 */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
    public Integer getPrepareLibraryCount() {
        return prepareLibraryCount;
    }

    public void setPrepareLibraryCount(Integer prepareLibraryCount) {
        this.prepareLibraryCount = prepareLibraryCount;
    }
    public Integer getForGrindingInCount() {
        return forGrindingInCount;
    }

    public void setForGrindingInCount(Integer forGrindingInCount) {
        this.forGrindingInCount = forGrindingInCount;
    }
    public Integer getForGrindingOutCount() {
        return forGrindingOutCount;
    }

    public void setForGrindingOutCount(Integer forGrindingOutCount) {
        this.forGrindingOutCount = forGrindingOutCount;
    }
    public Integer getGrindingOutCount() {
        return grindingOutCount;
    }

    public void setGrindingOutCount(Integer grindingOutCount) {
        this.grindingOutCount = grindingOutCount;
    }
    public Integer getProductLineCount() {
        return productLineCount;
    }

    public void setProductLineCount(Integer productLineCount) {
        this.productLineCount = productLineCount;
    }
    public Integer getScrapCount() {
        return scrapCount;
    }

    public void setScrapCount(Integer scrapCount) {
        this.scrapCount = scrapCount;
    }
    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Integer getToInstallCount() {
        return toInstallCount;
    }

    public void setToInstallCount(Integer toInstallCount) {
        this.toInstallCount = toInstallCount;
    }
    public Integer getToUninstallCount() {
        return toUninstallCount;
    }

    public void setToUninstallCount(Integer toUninstallCount) {
        this.toUninstallCount = toUninstallCount;
    }
    public Integer getToExchangeCount() {
        return toExchangeCount;
    }

    public void setToExchangeCount(Integer toExchangeCount) {
        this.toExchangeCount = toExchangeCount;
    }

    public CuttingTool getCuttingTool() {
        return cuttingTool;
    }

    public void setCuttingTool(CuttingTool cuttingTool) {
        this.cuttingTool = cuttingTool;
    }


}
