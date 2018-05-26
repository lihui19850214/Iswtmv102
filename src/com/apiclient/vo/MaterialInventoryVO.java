package com.apiclient.vo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 MaterialInventoryVO 
*/
public class MaterialInventoryVO implements Serializable{

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
    * @Description  生产线数量
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
     * @fieldName cuttingToolCode
    * @fieldType  
    * @Description  
     */
    private CuttingToolVO cuttingToolVO;


    /**
     * @fieldName currentPage
    * @fieldType  Integer
    * @Description  当前页码
     */
    private Integer currentPage = 1;

    /**
     * @fieldName totalPage
    * @fieldType  Integer
    * @Description  总页数
     */
    private Integer totalPage;

    /**
     * @fieldName pageSize
    * @fieldType  Integer
    * @Description  每页记录条数
     */
    private Integer pageSize = 10;

    /**
     * @fieldName maxPage
    * @fieldType  Integer
    * @Description  总页数
     */
    private Integer maxPage;

    /**
     * @fieldName startRecord
    * @fieldType  Integer
    * @Description  开始查询记录
     */
    private Integer startRecord;

    /**
      * @fieldName toInstallCount
     * @fieldType  Integer
     * @Description  待按上数量
     */
    private Integer toInstallCount;

    /**
      * @fieldName toInstallCount
     * @fieldType  Integer
     * @Description  待按上数量
     */
    private Integer toUninstallCount;

    public Integer getToUninstallCount() {
        return toUninstallCount;
    }

    public void setToUninstallCount(Integer toUninstallCount) {
        this.toUninstallCount = toUninstallCount;
    }

    public Integer getToInstallCount() {
        return toInstallCount;
    }

    public void setToInstallCount(Integer toInstallCount) {
        this.toInstallCount = toInstallCount;
    }

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

    public CuttingToolVO getCuttingToolVO() {
        return cuttingToolVO;
    }

    public void setCuttingToolVO(CuttingToolVO cuttingToolVO) {
        this.cuttingToolVO = cuttingToolVO;
    }


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.startRecord = (this.currentPage-1)*pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        this.maxPage = this.totalPage/this.pageSize+(this.totalPage%this.pageSize)>0?1:0;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRecord() {
        return startRecord;
    }

    public void setStartRecord(Integer startRecord) {
        this.startRecord = startRecord;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }



}
