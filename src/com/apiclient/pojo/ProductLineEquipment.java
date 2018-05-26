package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 ProductLineEquipment 
*/
public class ProductLineEquipment implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  设备id
     */
    private Integer id;

    /**
     * @fieldName rfidContainerCode
    * @fieldType  String
    * @Description  RFID载体
     */
    private String rfidContainerCode;
    /**
     * @fieldName statues
    * @fieldType  Integer
    * @Description  当前是否使用(0是,1否)
     */
    private Integer statues;
    /**
     * @fieldName code
    * @fieldType  String
    * @Description  编码
     */
    private String code;
    /**
     * @fieldName name
    * @fieldType  String
    * @Description  名称
     */
    private String name;
    /**
     * @fieldName type
    * @fieldType  Integer
    * @Description  设备类型(0:加工设备1:对刀设备2:专机)
     */
    private Integer type;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;

    /**
     * @fieldName rfidContainerCode
    * @fieldType  
    * @Description  RFID载体
     */
    private RfidContainer rfidContainer;

    private List<ProductLine> productLineList;


    /* 设备id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRfidContainerCode() {
        return rfidContainerCode;
    }

    public void setRfidContainerCode(String rfidContainerCode) {
        this.rfidContainerCode = rfidContainerCode;
    }
    public Integer getStatues() {
        return statues;
    }

    public void setStatues(Integer statues) {
        this.statues = statues;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public RfidContainer getRfidContainer() {
        return rfidContainer;
    }

    public void setRfidContainer(RfidContainer rfidContainer) {
        this.rfidContainer = rfidContainer;
    }

    public List<ProductLine> getProductLineList() {
        return productLineList;
    }

    public void setProductLineList(List<ProductLine> productLineList) {
        this.productLineList = productLineList;
    }

}
