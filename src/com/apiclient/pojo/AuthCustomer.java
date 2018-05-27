package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthCustomer 
*/
public class AuthCustomer implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  
     */
    private Integer id;

    /**
     * @fieldName account
    * @fieldType  String
    * @Description  用户名[16位数字字母组合]
     */
    private String account;
    /**
     * @fieldName password
    * @fieldType  String
    * @Description  用户密码[16位数字字母组合]
     */
    private String password;
    /**
     * @fieldName employeeCode
    * @fieldType  String
    * @Description  员工卡号[20位员工卡]
     */
    private String employeeCode;
    /**
     * @fieldName type
    * @fieldType  Integer
    * @Description  用户类型(0系统用户1普通用户)
     */
    private Integer type;
    /**
     * @fieldName lockFlag
    * @fieldType  Integer
    * @Description  用户锁定区分(0正常1锁定)
     */
    private Integer lockFlag;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  
     */
    private Integer isDel;
    /**
     * @fieldName code
    * @fieldType  String
    * @Description  用户编码
     */
    private String code;
    /**
     * @fieldName rfidContainerCode
    * @fieldType  String
    * @Description  RFID标签
     */
    private String rfidContainerCode;
    /**
     * @fieldName name
    * @fieldType  String
    * @Description  真实姓名
     */
    private String name;
    /**
     * @fieldName rfidCode
    * @fieldType  String
    * @Description  
     */
    private String rfidCode;
    /**
     * @fieldName authPositionCode
    * @fieldType  Integer
    * @Description  
     */
    private Integer authPositionCode;
    /**
     * @fieldName authDepartmentCode
    * @fieldType  Integer
    * @Description  
     */
    private Integer authDepartmentCode;
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
    /**
     * @fieldName authDepartmentCode
    * @fieldType  
    * @Description  
     */
    private AuthDepartment authDepartment;
    /**
     * @fieldName authPositionCode
    * @fieldType  
    * @Description  
     */
    private AuthPosition authPosition;
    /**
     * @fieldName rfidContainerCode
    * @fieldType  
    * @Description  RFID标签
     */
    private RfidContainer rfidContainer;

    private List<AuthAuthorization> authAuthorizationList;


    /*  */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(Integer lockFlag) {
        this.lockFlag = lockFlag;
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
    public String getRfidContainerCode() {
        return rfidContainerCode;
    }

    public void setRfidContainerCode(String rfidContainerCode) {
        this.rfidContainerCode = rfidContainerCode;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }
    public Integer getAuthPositionCode() {
        return authPositionCode;
    }

    public void setAuthPositionCode(Integer authPositionCode) {
        this.authPositionCode = authPositionCode;
    }
    public Integer getAuthDepartmentCode() {
        return authDepartmentCode;
    }

    public void setAuthDepartmentCode(Integer authDepartmentCode) {
        this.authDepartmentCode = authDepartmentCode;
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
    public AuthDepartment getAuthDepartment() {
        return authDepartment;
    }

    public void setAuthDepartment(AuthDepartment authDepartment) {
        this.authDepartment = authDepartment;
    }
    public AuthPosition getAuthPosition() {
        return authPosition;
    }

    public void setAuthPosition(AuthPosition authPosition) {
        this.authPosition = authPosition;
    }
    public RfidContainer getRfidContainer() {
        return rfidContainer;
    }

    public void setRfidContainer(RfidContainer rfidContainer) {
        this.rfidContainer = rfidContainer;
    }

    public List<AuthAuthorization> getAuthAuthorizationList() {
        return authAuthorizationList;
    }

    public void setAuthAuthorizationList(List<AuthAuthorization> authAuthorizationList) {
        this.authAuthorizationList = authAuthorizationList;
    }

}
