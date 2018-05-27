package com.apiclient.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 AuthAuthorizationVO 
*/
public class AuthAuthorizationVO implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  
     */
    private Integer id;


    /**
     * @fieldName reason
    * @fieldType  Integer
    * @Description  授权原因（0断刀,1丢刀,2补领,3到寿,4卸下后安上,5重复换装,6重复复磨,7重复初始化设备,8库存盘点,9出库报废，10其它）
     */
    private Integer reason;

    /**
     * @fieldName authorizedTime
    * @fieldType  Timestamp
    * @Description  授权时间 开始时间
     */
    private Timestamp authorizedTimeBegin;
    /**
     * @fieldName authorizedTime
    * @fieldType  Timestamp
    * @Description  授权时间 结束时间
     */
    private Timestamp authorizedTimeEnd;
    /**
     * @fieldName authorizedTime
    * @fieldType  Timestamp
    * @Description  授权时间
     */
    private Timestamp authorizedTime;

    /**
     * @fieldName note
    * @fieldType  String
    * @Description  备注（情况简述）
     */
    private String note;

    /**
     * @fieldName customerCode
    * @fieldType  String
    * @Description  用户
     */
    private String customerCode;

    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  删除区分(0有效1删除)
     */
    private Integer isDel;

    /**
     * @fieldName customerCode
    * @fieldType  
    * @Description  用户
     */
    private AuthCustomerVO authCustomerVO;


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

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }
    public Timestamp getAuthorizedTimeBegin() {
        return authorizedTimeBegin;
    }

    public void setAuthorizedTimeBegin(Timestamp authorizedTimeBegin) {
        this.authorizedTimeBegin = authorizedTimeBegin;
    }

    public Timestamp getAuthorizedTimeEnd() {
        return authorizedTimeEnd;
    }

    public void setAuthorizedTimeEnd(Timestamp authorizedTimeEnd) {
        this.authorizedTimeEnd = authorizedTimeEnd;
    }
    public Timestamp getAuthorizedTime() {
        return authorizedTime;
    }

    public void setAuthorizedTime(Timestamp authorizedTime) {
        this.authorizedTime = authorizedTime;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public AuthCustomerVO getAuthCustomerVO() {
        return authCustomerVO;
    }

    public void setAuthCustomerVO(AuthCustomerVO authCustomerVO) {
        this.authCustomerVO = authCustomerVO;
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
