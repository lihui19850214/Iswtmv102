package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthDepartment 
*/
public class AuthDepartment implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  部门id
     */
    private Integer id;

    /**
     * @fieldName code
    * @fieldType  String
    * @Description  部门编码
     */
    private String code;
    /**
     * @fieldName name
    * @fieldType  String
    * @Description  部门名称
     */
    private String name;
    /**
     * @fieldName phone
    * @fieldType  String
    * @Description  部门电话
     */
    private String phone;
    /**
     * @fieldName leaderName
    * @fieldType  String
    * @Description  部门负责人
     */
    private String leaderName;
    /**
     * @fieldName leaderMobile
    * @fieldType  String
    * @Description  负责人手机
     */
    private String leaderMobile;
    /**
     * @fieldName description
    * @fieldType  String
    * @Description  描述
     */
    private String description;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName authAgencyCode
    * @fieldType  Integer
    * @Description  
     */
    private Integer authAgencyCode;

    /**
     * @fieldName authAgencyCode
    * @fieldType  
    * @Description  
     */
    private AuthAgency authAgency;

    private List<AuthCustomer> authCustomerList;
    private List<AuthPosition> authPositionList;


    /* 部门id */
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
    public String getLeaderMobile() {
        return leaderMobile;
    }

    public void setLeaderMobile(String leaderMobile) {
        this.leaderMobile = leaderMobile;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Integer getAuthAgencyCode() {
        return authAgencyCode;
    }

    public void setAuthAgencyCode(Integer authAgencyCode) {
        this.authAgencyCode = authAgencyCode;
    }

    public AuthAgency getAuthAgency() {
        return authAgency;
    }

    public void setAuthAgency(AuthAgency authAgency) {
        this.authAgency = authAgency;
    }

    public List<AuthCustomer> getAuthCustomerList() {
        return authCustomerList;
    }

    public void setAuthCustomerList(List<AuthCustomer> authCustomerList) {
        this.authCustomerList = authCustomerList;
    }
    public List<AuthPosition> getAuthPositionList() {
        return authPositionList;
    }

    public void setAuthPositionList(List<AuthPosition> authPositionList) {
        this.authPositionList = authPositionList;
    }

}
