package com.apiclient.vo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 ProductLineVO 
*/
public class ProductLineVO implements Serializable {

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
    private ProductLineAxleVO productLineAxleVO;
    /**
     * @fieldName assemblylineCode
    * @fieldType  
    * @Description  流水线ID
     */
    private ProductLineAssemblylineVO productLineAssemblylineVO;
    /**
     * @fieldName equipmentCode
    * @fieldType  
    * @Description  设备ID
     */
    private ProductLineEquipmentVO productLineEquipmentVO;
    /**
     * @fieldName processCode
    * @fieldType  
    * @Description  
     */
    private ProductLineProcessVO productLineProcessVO;
    /**
     * @fieldName partsCode
    * @fieldType  
    * @Description  零件
     */
    private ProductLinePartsVO productLinePartsVO;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  合成刀编码
     */
    private SynthesisCuttingToolVO synthesisCuttingToolVO;


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

    public ProductLineAxleVO getProductLineAxleVO() {
        return productLineAxleVO;
    }

    public void setProductLineAxleVO(ProductLineAxleVO productLineAxleVO) {
        this.productLineAxleVO = productLineAxleVO;
    }
    public ProductLineAssemblylineVO getProductLineAssemblylineVO() {
        return productLineAssemblylineVO;
    }

    public void setProductLineAssemblylineVO(ProductLineAssemblylineVO productLineAssemblylineVO) {
        this.productLineAssemblylineVO = productLineAssemblylineVO;
    }
    public ProductLineEquipmentVO getProductLineEquipmentVO() {
        return productLineEquipmentVO;
    }

    public void setProductLineEquipmentVO(ProductLineEquipmentVO productLineEquipmentVO) {
        this.productLineEquipmentVO = productLineEquipmentVO;
    }
    public ProductLineProcessVO getProductLineProcessVO() {
        return productLineProcessVO;
    }

    public void setProductLineProcessVO(ProductLineProcessVO productLineProcessVO) {
        this.productLineProcessVO = productLineProcessVO;
    }
    public ProductLinePartsVO getProductLinePartsVO() {
        return productLinePartsVO;
    }

    public void setProductLinePartsVO(ProductLinePartsVO productLinePartsVO) {
        this.productLinePartsVO = productLinePartsVO;
    }
    public SynthesisCuttingToolVO getSynthesisCuttingToolVO() {
        return synthesisCuttingToolVO;
    }

    public void setSynthesisCuttingToolVO(SynthesisCuttingToolVO synthesisCuttingToolVO) {
        this.synthesisCuttingToolVO = synthesisCuttingToolVO;
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
