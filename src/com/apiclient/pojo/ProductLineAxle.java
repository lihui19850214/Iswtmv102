package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 ProductLineAxle 
*/
public class ProductLineAxle implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  轴ID
     */
    private Integer id;

    /**
     * @fieldName code
    * @fieldType  String
    * @Description  轴编码
     */
    private String code;
    /**
     * @fieldName name
    * @fieldType  String
    * @Description  轴名称
     */
    private String name;
    /**
     * @fieldName isDel
    * @fieldType  String
    * @Description  
     */
    private Integer isDel;


    private List<ProductLine> productLineList;


    /* 轴ID */
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public List<ProductLine> getProductLineList() {
        return productLineList;
    }

    public void setProductLineList(List<ProductLine> productLineList) {
        this.productLineList = productLineList;
    }

}
