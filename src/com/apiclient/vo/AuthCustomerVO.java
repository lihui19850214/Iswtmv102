package com.apiclient.vo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthCustomerVO 
*/
public class AuthCustomerVO implements Serializable {

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
    private AuthAgencyVO authAgencyVO;
    /**
     * @fieldName authDepartmentCode
    * @fieldType  
    * @Description  
     */
    private AuthDepartmentVO authDepartmentVO;
    /**
     * @fieldName authPositionCode
    * @fieldType  
    * @Description  
     */
    private AuthPositionVO authPositionVO;
    /**
     * @fieldName rfidContainerCode
    * @fieldType  
    * @Description  RFID标签
     */
    private RfidContainerVO rfidContainerVO;

    private List<AuthAuthorizationVO> authAuthorizationVOList;

    /**
     * @fieldName currentPage
    * @fieldType  Integer
    * @Description  当前页码
     */
    private Integer currentPage = 1;

    /**
     * @fieldName totalPage
    * @fieldType  Integer
    * @Description  总页数
     */
    private Integer totalPage;

    /**
     * @fieldName pageSize
    * @fieldType  Integer
    * @Description  每页记录条数
     */
    private Integer pageSize = 10;

    /**
     * @fieldName maxPage
    * @fieldType  Integer
    * @Description  总页数
     */
    private Integer maxPage;

    /**
     * @fieldName startRecord
    * @fieldType  Integer
    * @Description  开始查询记录
     */
    private Integer startRecord;


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

    public AuthAgencyVO getAuthAgencyVO() {
        return authAgencyVO;
    }

    public void setAuthAgencyVO(AuthAgencyVO authAgencyVO) {
        this.authAgencyVO = authAgencyVO;
    }
    public AuthDepartmentVO getAuthDepartmentVO() {
        return authDepartmentVO;
    }

    public void setAuthDepartmentVO(AuthDepartmentVO authDepartmentVO) {
        this.authDepartmentVO = authDepartmentVO;
    }
    public AuthPositionVO getAuthPositionVO() {
        return authPositionVO;
    }

    public void setAuthPositionVO(AuthPositionVO authPositionVO) {
        this.authPositionVO = authPositionVO;
    }
    public RfidContainerVO getRfidContainerVO() {
        return rfidContainerVO;
    }

    public void setRfidContainerVO(RfidContainerVO rfidContainerVO) {
        this.rfidContainerVO = rfidContainerVO;
    }

    public List<AuthAuthorizationVO> getAuthAuthorizationVOList() {
        return authAuthorizationVOList;
    }

    public void setAuthAuthorizationVOList(List<AuthAuthorizationVO> authAuthorizationVOList) {
        this.authAuthorizationVOList = authAuthorizationVOList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        this.startRecord = (this.currentPage-1)*pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        this.maxPage = this.totalPage/this.pageSize+(this.totalPage%this.pageSize)>0?1:0;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRecord() {
        return startRecord;
    }

    public void setStartRecord(Integer startRecord) {
        this.startRecord = startRecord;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }



}
