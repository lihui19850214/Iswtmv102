package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthPosition 
*/
public class AuthPosition implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  
     */
    private Integer id;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName code
    * @fieldType  String
    * @Description  职务编码
     */
    private String code;
    /**
     * @fieldName name
    * @fieldType  String
    * @Description  职务名称
     */
    private String name;
    /**
     * @fieldName authDepartmentCode1
    * @fieldType  Integer
    * @Description  
     */
    private Integer authDepartmentCode1;

    /**
     * @fieldName authDepartmentCode1
    * @fieldType  
    * @Description  
     */
    private AuthDepartment authDepartment;

    private List<AuthCustomer> authCustomerList;


    /*  */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
    public Integer getAuthDepartmentCode1() {
        return authDepartmentCode1;
    }

    public void setAuthDepartmentCode1(Integer authDepartmentCode1) {
        this.authDepartmentCode1 = authDepartmentCode1;
    }

    public AuthDepartment getAuthDepartment() {
        return authDepartment;
    }

    public void setAuthDepartment(AuthDepartment authDepartment) {
        this.authDepartment = authDepartment;
    }

    public List<AuthCustomer> getAuthCustomerList() {
        return authCustomerList;
    }

    public void setAuthCustomerList(List<AuthCustomer> authCustomerList) {
        this.authCustomerList = authCustomerList;
    }

}
