package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 DjCircleKanbanAkp 
*/
public class DjCircleKanbanAkp implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName whCode
    * @fieldType  String
    * @Description  仓库
     */
    private String whCode;

    /**
     * @fieldName cKanbanCode
    * @fieldType  String
    * @Description  刀具条码，看板号 'CK'
     */
    private String cKanbanCode;
    /**
     * @fieldName ownerCode
    * @fieldType  String
    * @Description  供应商
     */
    private String ownerCode;
    /**
     * @fieldName mtlCode
    * @fieldType  String
    * @Description  物料号
     */
    private String mtlCode;
    /**
     * @fieldName mtlName
    * @fieldType  String
    * @Description  物料名称
     */
    private String mtlName;
    /**
     * @fieldName qty
    * @fieldType  Integer
    * @Description  单次加工寿命
     */
    private Integer qty;
    /**
     * @fieldName loc
    * @fieldType  String
    * @Description  刀具号
     */
    private String loc;
    /**
     * @fieldName prdLine
    * @fieldType  String
    * @Description  生产线
     */
    private String prdLine;
    /**
     * @fieldName station
    * @fieldType  String
    * @Description  工位
     */
    private String station;
    /**
     * @fieldName boxType
    * @fieldType  String
    * @Description  工厂
     */
    private String boxType;
    /**
     * @fieldName prdLineType
    * @fieldType  String
    * @Description  项目
     */
    private String prdLineType;
    /**
     * @fieldName status
    * @fieldType  String
    * @Description  0-新建 1-已打印
     */
    private String status;
    /**
     * @fieldName susr1
    * @fieldType  String
    * @Description  安装数
     */
    private String susr1;
    /**
     * @fieldName susr2
    * @fieldType  String
    * @Description  规则型号
     */
    private String susr2;
    /**
     * @fieldName susr3
    * @fieldType  String
    * @Description  采购价格
     */
    private String susr3;
    /**
     * @fieldName susr4
    * @fieldType  String
    * @Description  同时加工件数
     */
    private String susr4;
    /**
     * @fieldName brand
    * @fieldType  String
    * @Description  刀具品牌
     */
    private String brand;
    /**
     * @fieldName orderCode
    * @fieldType  String
    * @Description  订货号
     */
    private String orderCode;
    /**
     * @fieldName application
    * @fieldType  String
    * @Description  用途
     */
    private String application;
    /**
     * @fieldName stationNum
    * @fieldType  Integer
    * @Description  复列设备数/复列工位数
     */
    private Integer stationNum;
    /**
     * @fieldName productFlag
    * @fieldType  String
    * @Description  产品标识
     */
    private String productFlag;
    /**
     * @fieldName outsourcingFlag
    * @fieldType  String
    * @Description  外委标识
     */
    private String outsourcingFlag;
    /**
     * @fieldName lifeMax
    * @fieldType  String
    * @Description  总寿命
     */
    private String lifeMax;




    /* 仓库 */
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getCKanbanCode() {
        return cKanbanCode;
    }

    public void setCKanbanCode(String cKanbanCode) {
        this.cKanbanCode = cKanbanCode;
    }
    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }
    public String getMtlCode() {
        return mtlCode;
    }

    public void setMtlCode(String mtlCode) {
        this.mtlCode = mtlCode;
    }
    public String getMtlName() {
        return mtlName;
    }

    public void setMtlName(String mtlName) {
        this.mtlName = mtlName;
    }
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }
    public String getPrdLine() {
        return prdLine;
    }

    public void setPrdLine(String prdLine) {
        this.prdLine = prdLine;
    }
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }
    public String getPrdLineType() {
        return prdLineType;
    }

    public void setPrdLineType(String prdLineType) {
        this.prdLineType = prdLineType;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getSusr1() {
        return susr1;
    }

    public void setSusr1(String susr1) {
        this.susr1 = susr1;
    }
    public String getSusr2() {
        return susr2;
    }

    public void setSusr2(String susr2) {
        this.susr2 = susr2;
    }
    public String getSusr3() {
        return susr3;
    }

    public void setSusr3(String susr3) {
        this.susr3 = susr3;
    }
    public String getSusr4() {
        return susr4;
    }

    public void setSusr4(String susr4) {
        this.susr4 = susr4;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
    public Integer getStationNum() {
        return stationNum;
    }

    public void setStationNum(Integer stationNum) {
        this.stationNum = stationNum;
    }
    public String getProductFlag() {
        return productFlag;
    }

    public void setProductFlag(String productFlag) {
        this.productFlag = productFlag;
    }
    public String getOutsourcingFlag() {
        return outsourcingFlag;
    }

    public void setOutsourcingFlag(String outsourcingFlag) {
        this.outsourcingFlag = outsourcingFlag;
    }
    public String getLifeMax() {
        return lifeMax;
    }

    public void setLifeMax(String lifeMax) {
        this.lifeMax = lifeMax;
    }



}
