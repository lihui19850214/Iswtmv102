package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SharpenProvider 
*/
public class SharpenProvider implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  供应商id
     */
    private Integer id;

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
    * @Description  类型
     */
    private Integer type;
    /**
     * @fieldName address
    * @fieldType  String
    * @Description  地址
     */
    private String address;
    /**
     * @fieldName contact
    * @fieldType  String
    * @Description  
     */
    private String contact;
    /**
     * @fieldName tell
    * @fieldType  String
    * @Description  联系人电话
     */
    private String tell;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;


    private List<OutsideFactory> outsideFactoryList;
    private List<Outsidefactoryhistory> outsidefactoryhistoryList;


    /* 供应商id */
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
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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

}
