package com.apiclient.vo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 CuttingToolBindleRecordsVO 
*/
public class CuttingToolBindleRecordsVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;


    /**
     * @fieldName productLineEquipmentCode
    * @fieldType  String
    * @Description  设备编码
     */
    private String productLineEquipmentCode;

    /**
     * @fieldName productLinePartsCode
    * @fieldType  String
    * @Description  
     */
    private String productLinePartsCode;

    /**
     * @fieldName productLineAxleCode
    * @fieldType  String
    * @Description  
     */
    private String productLineAxleCode;

    /**
     * @fieldName productLineProcessCode
    * @fieldType  String
    * @Description  
     */
    private String productLineProcessCode;

    /**
     * @fieldName productLineAssemblylineCode
    * @fieldType  String
    * @Description  
     */
    private String productLineAssemblylineCode;

    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  String
    * @Description  
     */
    private String synthesisCuttingToolCode;

    /**
     * @fieldName synthesisCode
    * @fieldType  String
    * @Description  
     */
    private String synthesisCode;

    /**
     * @fieldName productLineEquipmentName
    * @fieldType  String
    * @Description  
     */
    private String productLineEquipmentName;

    /**
     * @fieldName productLinePartsName
    * @fieldType  String
    * @Description  
     */
    private String productLinePartsName;

    /**
     * @fieldName productLineAxleName
    * @fieldType  String
    * @Description  
     */
    private String productLineAxleName;

    /**
     * @fieldName productLineProcessName
    * @fieldType  String
    * @Description  
     */
    private String productLineProcessName;

    /**
     * @fieldName ratedNumber
    * @fieldType  Integer
    * @Description  
     */
    private Integer ratedNumber;

    /**
     * @fieldName actualNumber
    * @fieldType  Integer
    * @Description  
     */
    private Integer actualNumber;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName productLineAssemblylineCode
    * @fieldType  
    * @Description  
     */
    private ProductLineAssemblylineVO productLineAssemblylineVO;
    /**
     * @fieldName productLineAxleCode
    * @fieldType  
    * @Description  
     */
    private ProductLineAxleVO productLineAxleVO;
    /**
     * @fieldName productLineEquipmentCode
    * @fieldType  
    * @Description  设备编码
     */
    private ProductLineEquipmentVO productLineEquipmentVO;
    /**
     * @fieldName productLinePartsCode
    * @fieldType  
    * @Description  
     */
    private ProductLinePartsVO productLinePartsVO;
    /**
     * @fieldName productLineProcessCode
    * @fieldType  
    * @Description  
     */
    private ProductLineProcessVO productLineProcessVO;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  
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


    /* id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductLineEquipmentCode() {
        return productLineEquipmentCode;
    }

    public void setProductLineEquipmentCode(String productLineEquipmentCode) {
        this.productLineEquipmentCode = productLineEquipmentCode;
    }
    public String getProductLinePartsCode() {
        return productLinePartsCode;
    }

    public void setProductLinePartsCode(String productLinePartsCode) {
        this.productLinePartsCode = productLinePartsCode;
    }
    public String getProductLineAxleCode() {
        return productLineAxleCode;
    }

    public void setProductLineAxleCode(String productLineAxleCode) {
        this.productLineAxleCode = productLineAxleCode;
    }
    public String getProductLineProcessCode() {
        return productLineProcessCode;
    }

    public void setProductLineProcessCode(String productLineProcessCode) {
        this.productLineProcessCode = productLineProcessCode;
    }
    public String getProductLineAssemblylineCode() {
        return productLineAssemblylineCode;
    }

    public void setProductLineAssemblylineCode(String productLineAssemblylineCode) {
        this.productLineAssemblylineCode = productLineAssemblylineCode;
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
    public String getProductLineEquipmentName() {
        return productLineEquipmentName;
    }

    public void setProductLineEquipmentName(String productLineEquipmentName) {
        this.productLineEquipmentName = productLineEquipmentName;
    }
    public String getProductLinePartsName() {
        return productLinePartsName;
    }

    public void setProductLinePartsName(String productLinePartsName) {
        this.productLinePartsName = productLinePartsName;
    }
    public String getProductLineAxleName() {
        return productLineAxleName;
    }

    public void setProductLineAxleName(String productLineAxleName) {
        this.productLineAxleName = productLineAxleName;
    }
    public String getProductLineProcessName() {
        return productLineProcessName;
    }

    public void setProductLineProcessName(String productLineProcessName) {
        this.productLineProcessName = productLineProcessName;
    }
    public Integer getRatedNumber() {
        return ratedNumber;
    }

    public void setRatedNumber(Integer ratedNumber) {
        this.ratedNumber = ratedNumber;
    }
    public Integer getActualNumber() {
        return actualNumber;
    }

    public void setActualNumber(Integer actualNumber) {
        this.actualNumber = actualNumber;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public ProductLineAssemblylineVO getProductLineAssemblylineVO() {
        return productLineAssemblylineVO;
    }

    public void setProductLineAssemblylineVO(ProductLineAssemblylineVO productLineAssemblylineVO) {
        this.productLineAssemblylineVO = productLineAssemblylineVO;
    }
    public ProductLineAxleVO getProductLineAxleVO() {
        return productLineAxleVO;
    }

    public void setProductLineAxleVO(ProductLineAxleVO productLineAxleVO) {
        this.productLineAxleVO = productLineAxleVO;
    }
    public ProductLineEquipmentVO getProductLineEquipmentVO() {
        return productLineEquipmentVO;
    }

    public void setProductLineEquipmentVO(ProductLineEquipmentVO productLineEquipmentVO) {
        this.productLineEquipmentVO = productLineEquipmentVO;
    }
    public ProductLinePartsVO getProductLinePartsVO() {
        return productLinePartsVO;
    }

    public void setProductLinePartsVO(ProductLinePartsVO productLinePartsVO) {
        this.productLinePartsVO = productLinePartsVO;
    }
    public ProductLineProcessVO getProductLineProcessVO() {
        return productLineProcessVO;
    }

    public void setProductLineProcessVO(ProductLineProcessVO productLineProcessVO) {
        this.productLineProcessVO = productLineProcessVO;
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
