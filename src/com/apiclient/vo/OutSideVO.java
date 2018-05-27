package com.apiclient.vo;

import com.apiclient.pojo.AuthCustomer;

import java.util.List;

/**
 * Created by logan on 2018/5/12.
 */
public class OutSideVO {

    private AuthCustomer authCustomer;

    private String wwcode;

    private List<SharpenVO> sharpenVOS;

    /**
      * @fieldName orderNum
     * @fieldType  String
     * @Description  外委单号
     */
    private String orderNum;

    /**
      * @fieldName sharpenProviderCode
     * @fieldType  String
     * @Description  外委商
     */
    private String sharpenProviderCode;

    /**
      * @fieldName zcCode
     * @fieldType  String
     * @Description  资材单号
     */
    private String zcCode;

    /**
      * @fieldName handlers
     * @fieldType  String
     * @Description  经手人
     */
    private String handlers;

    /**
      * @fieldName sender
     * @fieldType  String
     * @Description  邮寄人
     */
    private String sender;

    /**
      * @fieldName outWay
     * @fieldType  String
     * @Description  外委方式
     */
    private String outWay;

    /**
      * @fieldName qmSharpenProviderCode
     * @fieldType  String
     * @Description  启明外委供应商编码
     */
    private String qmSharpenProviderCode;

    /**
      * @fieldName qmSharpenProviderName
     * @fieldType  String
     * @Description  启明外委供应商名称
     */
    private String qmSharpenProviderName;

    /**
      * @fieldName approverName
     * @fieldType  String
     * @Description  审批人姓名
     */
    private String approverName;

    public String getWwcode() {
        return wwcode;
    }

    public void setWwcode(String wwcode) {
        this.wwcode = wwcode;
    }

    public AuthCustomer getAuthCustomer() {
        return authCustomer;
    }

    public void setAuthCustomer(AuthCustomer authCustomer) {
        this.authCustomer = authCustomer;
    }

    public List<SharpenVO> getSharpenVOS() {
        return sharpenVOS;
    }

    public void setSharpenVOS(List<SharpenVO> sharpenVOS) {
        this.sharpenVOS = sharpenVOS;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSharpenProviderCode() {
        return sharpenProviderCode;
    }

    public void setSharpenProviderCode(String sharpenProviderCode) {
        this.sharpenProviderCode = sharpenProviderCode;
    }

    public String getZcCode() {
        return zcCode;
    }

    public void setZcCode(String zcCode) {
        this.zcCode = zcCode;
    }

    public String getHandlers() {
        return handlers;
    }

    public void setHandlers(String handlers) {
        this.handlers = handlers;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getOutWay() {
        return outWay;
    }

    public void setOutWay(String outWay) {
        this.outWay = outWay;
    }

    public String getQmSharpenProviderCode() {
        return qmSharpenProviderCode;
    }

    public void setQmSharpenProviderCode(String qmSharpenProviderCode) {
        this.qmSharpenProviderCode = qmSharpenProviderCode;
    }

    public String getQmSharpenProviderName() {
        return qmSharpenProviderName;
    }

    public void setQmSharpenProviderName(String qmSharpenProviderName) {
        this.qmSharpenProviderName = qmSharpenProviderName;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }
}
