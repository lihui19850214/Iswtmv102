package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolBindleRecordsVO 
*/
public class SynthesisCuttingToolBindleRecordsVO implements Serializable{

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
    * @Description  
     */
    private String synthesisCuttingToolCode;

    /**
     * @fieldName synthesisCode
    * @fieldType  String
    * @Description  合成刀业务编码
     */
    private String synthesisCode;

    /**
     * @fieldName productLineEquipmentCode
    * @fieldType  String
    * @Description  设备
     */
    private String productLineEquipmentCode;

    /**
     * @fieldName productLineAssemblylineCode
    * @fieldType  String
    * @Description  流水线
     */
    private String productLineAssemblylineCode;

    /**
     * @fieldName productLineProcessCode
    * @fieldType  String
    * @Description  工序
     */
    private String productLineProcessCode;

    /**
     * @fieldName productLineAxleCode
    * @fieldType  String
    * @Description  
     */
    private String productLineAxleCode;

    /**
     * @fieldName productLinePartsCode
    * @fieldType  String
    * @Description  
     */
    private String productLinePartsCode;

    /**
     * @fieldName status
    * @fieldType  String
    * @Description  1 安上 2 卸下
     */
    private String status;

    /**
     * @fieldName operatorInstall
    * @fieldType  String
    * @Description  操作者
     */
    private String operatorInstall;

    /**
     * @fieldName reason
    * @fieldType  String
    * @Description  卸下原因
     */
    private String reason;

    /**
     * @fieldName ratedNumber
    * @fieldType  Integer
    * @Description  额定数量
     */
    private Integer ratedNumber;

    /**
     * @fieldName actualNumber
    * @fieldType  Integer
    * @Description  实际数量
     */
    private Integer actualNumber;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;

    /**
     * @fieldName operatorUninstall
    * @fieldType  String
    * @Description  
     */
    private String operatorUninstall;

    /**
     * @fieldName operatorInstallName
    * @fieldType  String
    * @Description  
     */
    private String operatorInstallName;

    /**
     * @fieldName operatorUninstallName
    * @fieldType  String
    * @Description  
     */
    private String operatorUninstallName;

    /**
     * @fieldName operatorUninstallTime
    * @fieldType  Timestamp
    * @Description  卸下时间 开始时间
     */
    private Timestamp operatorUninstallTimeBegin;
    /**
     * @fieldName operatorUninstallTime
    * @fieldType  Timestamp
    * @Description  卸下时间 结束时间
     */
    private Timestamp operatorUninstallTimeEnd;
    /**
     * @fieldName operatorUninstallTime
    * @fieldType  Timestamp
    * @Description  卸下时间
     */
    private Timestamp operatorUninstallTime;

    /**
     * @fieldName operatorInstallTime
    * @fieldType  Timestamp
    * @Description  按上时间 开始时间
     */
    private Timestamp operatorInstallTimeBegin;
    /**
     * @fieldName operatorInstallTime
    * @fieldType  Timestamp
    * @Description  按上时间 结束时间
     */
    private Timestamp operatorInstallTimeEnd;
    /**
     * @fieldName operatorInstallTime
    * @fieldType  Timestamp
    * @Description  按上时间
     */
    private Timestamp operatorInstallTime;

    /**
     * @fieldName synthesisCuttingToolBindCode
    * @fieldType  String
    * @Description  
     */
    private String synthesisCuttingToolBindCode;

    /**
     * @fieldName bindRfid
    * @fieldType  String
    * @Description  
     */
    private String bindRfid;

    /**
     * @fieldName productLineAssemblylineCode
    * @fieldType  
    * @Description  流水线
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
    * @Description  设备
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
    * @Description  工序
     */
    private ProductLineProcessVO productLineProcessVO;
    /**
     * @fieldName synthesisCuttingToolCode
    * @fieldType  
    * @Description  
     */
    private SynthesisCuttingToolVO synthesisCuttingToolVO;
    /**
     * @fieldName synthesisCuttingToolBindCode
    * @fieldType  
    * @Description  
     */
    private SynthesisCuttingToolBindVO synthesisCuttingToolBindVO;


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
    public String getProductLineEquipmentCode() {
        return productLineEquipmentCode;
    }

    public void setProductLineEquipmentCode(String productLineEquipmentCode) {
        this.productLineEquipmentCode = productLineEquipmentCode;
    }
    public String getProductLineAssemblylineCode() {
        return productLineAssemblylineCode;
    }

    public void setProductLineAssemblylineCode(String productLineAssemblylineCode) {
        this.productLineAssemblylineCode = productLineAssemblylineCode;
    }
    public String getProductLineProcessCode() {
        return productLineProcessCode;
    }

    public void setProductLineProcessCode(String productLineProcessCode) {
        this.productLineProcessCode = productLineProcessCode;
    }
    public String getProductLineAxleCode() {
        return productLineAxleCode;
    }

    public void setProductLineAxleCode(String productLineAxleCode) {
        this.productLineAxleCode = productLineAxleCode;
    }
    public String getProductLinePartsCode() {
        return productLinePartsCode;
    }

    public void setProductLinePartsCode(String productLinePartsCode) {
        this.productLinePartsCode = productLinePartsCode;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getOperatorInstall() {
        return operatorInstall;
    }

    public void setOperatorInstall(String operatorInstall) {
        this.operatorInstall = operatorInstall;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
    public String getOperatorUninstall() {
        return operatorUninstall;
    }

    public void setOperatorUninstall(String operatorUninstall) {
        this.operatorUninstall = operatorUninstall;
    }
    public String getOperatorInstallName() {
        return operatorInstallName;
    }

    public void setOperatorInstallName(String operatorInstallName) {
        this.operatorInstallName = operatorInstallName;
    }
    public String getOperatorUninstallName() {
        return operatorUninstallName;
    }

    public void setOperatorUninstallName(String operatorUninstallName) {
        this.operatorUninstallName = operatorUninstallName;
    }
    public Timestamp getOperatorUninstallTimeBegin() {
        return operatorUninstallTimeBegin;
    }

    public void setOperatorUninstallTimeBegin(Timestamp operatorUninstallTimeBegin) {
        this.operatorUninstallTimeBegin = operatorUninstallTimeBegin;
    }

    public Timestamp getOperatorUninstallTimeEnd() {
        return operatorUninstallTimeEnd;
    }

    public void setOperatorUninstallTimeEnd(Timestamp operatorUninstallTimeEnd) {
        this.operatorUninstallTimeEnd = operatorUninstallTimeEnd;
    }
    public Timestamp getOperatorUninstallTime() {
        return operatorUninstallTime;
    }

    public void setOperatorUninstallTime(Timestamp operatorUninstallTime) {
        this.operatorUninstallTime = operatorUninstallTime;
    }
    public Timestamp getOperatorInstallTimeBegin() {
        return operatorInstallTimeBegin;
    }

    public void setOperatorInstallTimeBegin(Timestamp operatorInstallTimeBegin) {
        this.operatorInstallTimeBegin = operatorInstallTimeBegin;
    }

    public Timestamp getOperatorInstallTimeEnd() {
        return operatorInstallTimeEnd;
    }

    public void setOperatorInstallTimeEnd(Timestamp operatorInstallTimeEnd) {
        this.operatorInstallTimeEnd = operatorInstallTimeEnd;
    }
    public Timestamp getOperatorInstallTime() {
        return operatorInstallTime;
    }

    public void setOperatorInstallTime(Timestamp operatorInstallTime) {
        this.operatorInstallTime = operatorInstallTime;
    }
    public String getSynthesisCuttingToolBindCode() {
        return synthesisCuttingToolBindCode;
    }

    public void setSynthesisCuttingToolBindCode(String synthesisCuttingToolBindCode) {
        this.synthesisCuttingToolBindCode = synthesisCuttingToolBindCode;
    }
    public String getBindRfid() {
        return bindRfid;
    }

    public void setBindRfid(String bindRfid) {
        this.bindRfid = bindRfid;
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
    public SynthesisCuttingToolBindVO getSynthesisCuttingToolBindVO() {
        return synthesisCuttingToolBindVO;
    }

    public void setSynthesisCuttingToolBindVO(SynthesisCuttingToolBindVO synthesisCuttingToolBindVO) {
        this.synthesisCuttingToolBindVO = synthesisCuttingToolBindVO;
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
