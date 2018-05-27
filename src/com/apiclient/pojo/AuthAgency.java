package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthAgency 
*/
public class AuthAgency implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  机构id
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
    * @Description  机构名称
     */
    private String name;
    /**
     * @fieldName tel
    * @fieldType  String
    * @Description  机构电话
     */
    private String tel;
    /**
     * @fieldName createDate
    * @fieldType  Timestamp
    * @Description  创建时间
     */
    private Timestamp createDate;
    /**
     * @fieldName corporate
    * @fieldType  String
    * @Description  法人
     */
    private String corporate;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;


    private List<AuthCustomer> authCustomerList;
    private List<AuthDepartment> authDepartmentList;


    /* 机构id */
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
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }


    public List<AuthCustomer> getAuthCustomerList() {
        return authCustomerList;
    }

    public void setAuthCustomerList(List<AuthCustomer> authCustomerList) {
        this.authCustomerList = authCustomerList;
    }
    public List<AuthDepartment> getAuthDepartmentList() {
        return authDepartmentList;
    }

    public void setAuthDepartmentList(List<AuthDepartment> authDepartmentList) {
        this.authDepartmentList = authDepartmentList;
    }

}
