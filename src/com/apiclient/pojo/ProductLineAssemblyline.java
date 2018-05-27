package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 ProductLineAssemblyline 
*/
public class ProductLineAssemblyline implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  流水线ID
     */
    private Integer id;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  流水线名称
     */
    private String name;
    /**
     * @fieldName code
    * @fieldType  String
    * @Description  流水线编码
     */
    private String code;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  删除区分(0有效1删除)
     */
    private Integer isDel;


    private List<ProductLine> productLineList;
    private List<ProductLineProcess> productLineProcessList;


    /* 流水线ID */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    public List<ProductLineProcess> getProductLineProcessList() {
        return productLineProcessList;
    }

    public void setProductLineProcessList(List<ProductLineProcess> productLineProcessList) {
        this.productLineProcessList = productLineProcessList;
    }

}
