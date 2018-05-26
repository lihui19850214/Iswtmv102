package com.apiclient.vo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 RfidContainerVO 
*/
public class RfidContainerVO implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  RFID载体ID
     */
    private Integer id;


    /**
     * @fieldName code
    * @fieldType  String
    * @Description  RFID标签
     */
    private String code;

    /**
     * @fieldName useCount
    * @fieldType  Integer
    * @Description  使用次数
     */
    private Integer useCount;

    /**
     * @fieldName laserCode
    * @fieldType  String
    * @Description  激光识别码
     */
    private String laserCode;

    /**
     * @fieldName bindType
    * @fieldType  Integer
    * @Description  绑定类型(0RFID 1 激光码 2 工具盘 9 其他)
     */
    private Integer bindType;

    /**
     * @fieldName labelType
    * @fieldType  Integer
    * @Description  标签类型（0库位标签，1单品刀，2合成刀具，3员工卡，4设备，5容器，6备刀库）
     */
    private Integer labelType;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  删除区分(0有效1删除)
     */
    private Integer isDel;

    /**
     * @fieldName operatorCode
    * @fieldType  Integer
    * @Description  操作人
     */
    private Integer operatorCode;

    /**
     * @fieldName prevOperation
    * @fieldType  String
    * @Description  上次操作
     */
    private String prevOperation;

    /**
     * @fieldName prevKey
    * @fieldType  Integer
    * @Description  上次操作key
     */
    private Integer prevKey;

    /**
     * @fieldName unicode
    * @fieldType  String
    * @Description  刀具的唯一码
     */
    private String unicode;

    /**
     * @fieldName operatorName
    * @fieldType  String
    * @Description  操作人姓名
     */
    private String operatorName;


    private List<AuthCustomerVO> authCustomerVOList;
    private List<CuttingToolBindVO> cuttingToolBindVOList;
    private List<ProductLineEquipmentVO> productLineEquipmentVOList;
    private List<SynthesisCuttingToolBindVO> synthesisCuttingToolBindVOList;

    /**
     * @fieldName currentPage
    * @fieldType  Integer
    * @Description  当前页码
     */
    private Integer currentPage = 1;

//    /**
//     * @fieldName totalPage
//    * @fieldType  Integer
//    * @Description  总页数
//     */
//    private Integer totalPage;

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


    /* RFID载体ID */
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
    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }
    public String getLaserCode() {
        return laserCode;
    }

    public void setLaserCode(String laserCode) {
        this.laserCode = laserCode;
    }
    public Integer getBindType() {
        return bindType;
    }

    public void setBindType(Integer bindType) {
        this.bindType = bindType;
    }
    public Integer getLabelType() {
        return labelType;
    }

    public void setLabelType(Integer labelType) {
        this.labelType = labelType;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }
    public String getPrevOperation() {
        return prevOperation;
    }

    public void setPrevOperation(String prevOperation) {
        this.prevOperation = prevOperation;
    }
    public Integer getPrevKey() {
        return prevKey;
    }

    public void setPrevKey(Integer prevKey) {
        this.prevKey = prevKey;
    }
    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }


    public List<AuthCustomerVO> getAuthCustomerVOList() {
        return authCustomerVOList;
    }

    public void setAuthCustomerVOList(List<AuthCustomerVO> authCustomerVOList) {
        this.authCustomerVOList = authCustomerVOList;
    }
    public List<CuttingToolBindVO> getCuttingToolBindVOList() {
        return cuttingToolBindVOList;
    }

    public void setCuttingToolBindVOList(List<CuttingToolBindVO> cuttingToolBindVOList) {
        this.cuttingToolBindVOList = cuttingToolBindVOList;
    }
    public List<ProductLineEquipmentVO> getProductLineEquipmentVOList() {
        return productLineEquipmentVOList;
    }

    public void setProductLineEquipmentVOList(List<ProductLineEquipmentVO> productLineEquipmentVOList) {
        this.productLineEquipmentVOList = productLineEquipmentVOList;
    }
    public List<SynthesisCuttingToolBindVO> getSynthesisCuttingToolBindVOList() {
        return synthesisCuttingToolBindVOList;
    }

    public void setSynthesisCuttingToolBindVOList(List<SynthesisCuttingToolBindVO> synthesisCuttingToolBindVOList) {
        this.synthesisCuttingToolBindVOList = synthesisCuttingToolBindVOList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.startRecord = (this.currentPage-1)*pageSize;
    }

//    public Integer getTotalPage() {
//        return totalPage;
//    }
//
//    public void setTotalPage(Integer totalPage) {
//        this.totalPage = totalPage;
//        this.maxPage = this.totalPage/this.pageSize+(this.totalPage%this.pageSize)>0?1:0;
//    }

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
