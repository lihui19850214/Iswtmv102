package com.apiclient.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 CuttingTool 
*/
public class CuttingTool implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  材料刀id
     */
    private Integer id;

    /**
     * @fieldName code
    * @fieldType  String
    * @Description  刀具编码
     */
    private String code;
    /**
     * @fieldName libraryCode
    * @fieldType  String
    * @Description  库位码
     */
    private String libraryCode;
    /**
     * @fieldName businessCode
    * @fieldType  String
    * @Description  业务编码
     */
    private String businessCode;
    /**
     * @fieldName name
    * @fieldType  String
    * @Description  名称
     */
    private String name;
    /**
     * @fieldName type
    * @fieldType  String
    * @Description  刀具分类(1刀具2辅具3配套9其他）
     */
    private String type;
    /**
     * @fieldName consumeType
    * @fieldType  String
    * @Description  消耗类别(0:可刃磨钻头1可刃磨刀片2一次性刀片9其他
     */
    private String consumeType;
    /**
     * @fieldName pic
    * @fieldType  String
    * @Description  图纸
     */
    private String pic;
    /**
     * @fieldName specifications
    * @fieldType  String
    * @Description  规格型号
     */
    private String specifications;
    /**
     * @fieldName cutNumber
    * @fieldType  Integer
    * @Description  刃口数
     */
    private Integer cutNumber;
    /**
     * @fieldName brand
    * @fieldType  String
    * @Description  品牌
     */
    private String brand;
    /**
     * @fieldName grinding
    * @fieldType  String
    * @Description  修磨类别(0:厂内修磨，1厂外修磨，2厂外涂层
     */
    private String grinding;
    /**
     * @fieldName sharpenNum
    * @fieldType  Integer
    * @Description  可使用次数
     */
    private Integer sharpenNum;
    /**
     * @fieldName materialMax
    * @fieldType  Integer
    * @Description  最大库存
     */
    private Integer materialMax;
    /**
     * @fieldName materialMin
    * @fieldType  Integer
    * @Description  最小库存
     */
    private Integer materialMin;
    /**
     * @fieldName toolNumber
    * @fieldType  Integer
    * @Description  可刃磨次数
     */
    private Integer toolNumber;
    /**
     * @fieldName sharpenCriterion
    * @fieldType  BigDecimal
    * @Description  复磨标准
     */
    private BigDecimal sharpenCriterion;
    /**
     * @fieldName materialLength
    * @fieldType  BigDecimal
    * @Description  刀具长度
     */
    private BigDecimal materialLength;
    /**
     * @fieldName sharpenLength
    * @fieldType  BigDecimal
    * @Description  可刃磨长度
     */
    private BigDecimal sharpenLength;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName beiMin
    * @fieldType  Integer
    * @Description  
     */
    private Integer beiMin;
    /**
     * @fieldName beiMax
    * @fieldType  Integer
    * @Description  
     */
    private Integer beiMax;
    /**
     * @fieldName toolPrice
    * @fieldType  BigDecimal
    * @Description  
     */
    private BigDecimal toolPrice;
    /**
     * @fieldName averagePrice
    * @fieldType  BigDecimal
    * @Description  
     */
    private BigDecimal averagePrice;
    /**
     * @fieldName parametersType
    * @fieldType  String
    * @Description  
     */
    private String parametersType;
    /**
     * @fieldName userfulType
    * @fieldType  String
    * @Description  用途类型 1 刀片,钻头，合成刀，热套类 2 一体刀 3筒刀 4专机 9其他
     */
    private String userfulType;
    /**
     * @fieldName qimingCode
    * @fieldType  String
    * @Description  启明编码
     */
    private String qimingCode;
    /**
     * @fieldName wuliaoCode
    * @fieldType  String
    * @Description  物料编码
     */
    private String wuliaoCode;


    private List<CuttingToolBind> cuttingToolBindList;
    private List<CuttingToolsScrap> cuttingToolsScrapList;
    private List<InsideFactory> insideFactoryList;
    private List<MaterialInventory> materialInventoryList;
    private List<OutPrepareLibrary> outPrepareLibraryList;
    private List<OutsideFactory> outsideFactoryList;
    private List<Outsidefactoryhistory> outsidefactoryhistoryList;
    private List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList;
    private List<SynthesisCuttingToolLocationConfig> synthesisCuttingToolLocationConfigList;


    /* 材料刀id */
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
    public String getLibraryCode() {
        return libraryCode;
    }

    public void setLibraryCode(String libraryCode) {
        this.libraryCode = libraryCode;
    }
    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(String consumeType) {
        this.consumeType = consumeType;
    }
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
    public Integer getCutNumber() {
        return cutNumber;
    }

    public void setCutNumber(Integer cutNumber) {
        this.cutNumber = cutNumber;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getGrinding() {
        return grinding;
    }

    public void setGrinding(String grinding) {
        this.grinding = grinding;
    }
    public Integer getSharpenNum() {
        return sharpenNum;
    }

    public void setSharpenNum(Integer sharpenNum) {
        this.sharpenNum = sharpenNum;
    }
    public Integer getMaterialMax() {
        return materialMax;
    }

    public void setMaterialMax(Integer materialMax) {
        this.materialMax = materialMax;
    }
    public Integer getMaterialMin() {
        return materialMin;
    }

    public void setMaterialMin(Integer materialMin) {
        this.materialMin = materialMin;
    }
    public Integer getToolNumber() {
        return toolNumber;
    }

    public void setToolNumber(Integer toolNumber) {
        this.toolNumber = toolNumber;
    }
    public BigDecimal getSharpenCriterion() {
        return sharpenCriterion;
    }

    public void setSharpenCriterion(BigDecimal sharpenCriterion) {
        this.sharpenCriterion = sharpenCriterion;
    }
    public BigDecimal getMaterialLength() {
        return materialLength;
    }

    public void setMaterialLength(BigDecimal materialLength) {
        this.materialLength = materialLength;
    }
    public BigDecimal getSharpenLength() {
        return sharpenLength;
    }

    public void setSharpenLength(BigDecimal sharpenLength) {
        this.sharpenLength = sharpenLength;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Integer getBeiMin() {
        return beiMin;
    }

    public void setBeiMin(Integer beiMin) {
        this.beiMin = beiMin;
    }
    public Integer getBeiMax() {
        return beiMax;
    }

    public void setBeiMax(Integer beiMax) {
        this.beiMax = beiMax;
    }
    public BigDecimal getToolPrice() {
        return toolPrice;
    }

    public void setToolPrice(BigDecimal toolPrice) {
        this.toolPrice = toolPrice;
    }
    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }
    public String getParametersType() {
        return parametersType;
    }

    public void setParametersType(String parametersType) {
        this.parametersType = parametersType;
    }
    public String getUserfulType() {
        return userfulType;
    }

    public void setUserfulType(String userfulType) {
        this.userfulType = userfulType;
    }
    public String getQimingCode() {
        return qimingCode;
    }

    public void setQimingCode(String qimingCode) {
        this.qimingCode = qimingCode;
    }
    public String getWuliaoCode() {
        return wuliaoCode;
    }

    public void setWuliaoCode(String wuliaoCode) {
        this.wuliaoCode = wuliaoCode;
    }


    public List<CuttingToolBind> getCuttingToolBindList() {
        return cuttingToolBindList;
    }

    public void setCuttingToolBindList(List<CuttingToolBind> cuttingToolBindList) {
        this.cuttingToolBindList = cuttingToolBindList;
    }
    public List<CuttingToolsScrap> getCuttingToolsScrapList() {
        return cuttingToolsScrapList;
    }

    public void setCuttingToolsScrapList(List<CuttingToolsScrap> cuttingToolsScrapList) {
        this.cuttingToolsScrapList = cuttingToolsScrapList;
    }
    public List<InsideFactory> getInsideFactoryList() {
        return insideFactoryList;
    }

    public void setInsideFactoryList(List<InsideFactory> insideFactoryList) {
        this.insideFactoryList = insideFactoryList;
    }
    public List<MaterialInventory> getMaterialInventoryList() {
        return materialInventoryList;
    }

    public void setMaterialInventoryList(List<MaterialInventory> materialInventoryList) {
        this.materialInventoryList = materialInventoryList;
    }
    public List<OutPrepareLibrary> getOutPrepareLibraryList() {
        return outPrepareLibraryList;
    }

    public void setOutPrepareLibraryList(List<OutPrepareLibrary> outPrepareLibraryList) {
        this.outPrepareLibraryList = outPrepareLibraryList;
    }
    public List<OutsideFactory> getOutsideFactoryList() {
        return outsideFactoryList;
    }

    public void setOutsideFactoryList(List<OutsideFactory> outsideFactoryList) {
        this.outsideFactoryList = outsideFactoryList;
    }
    public List<Outsidefactoryhistory> getOutsidefactoryhistoryList() {
        return outsidefactoryhistoryList;
    }

    public void setOutsidefactoryhistoryList(List<Outsidefactoryhistory> outsidefactoryhistoryList) {
        this.outsidefactoryhistoryList = outsidefactoryhistoryList;
    }
    public List<SynthesisCuttingToolLocation> getSynthesisCuttingToolLocationList() {
        return synthesisCuttingToolLocationList;
    }

    public void setSynthesisCuttingToolLocationList(List<SynthesisCuttingToolLocation> synthesisCuttingToolLocationList) {
        this.synthesisCuttingToolLocationList = synthesisCuttingToolLocationList;
    }

    public List<SynthesisCuttingToolLocationConfig> getSynthesisCuttingToolLocationConfigList() {
        return synthesisCuttingToolLocationConfigList;
    }

    public void setSynthesisCuttingToolLocationConfigList(List<SynthesisCuttingToolLocationConfig> synthesisCuttingToolLocationConfigList) {
        this.synthesisCuttingToolLocationConfigList = synthesisCuttingToolLocationConfigList;
    }
}
