package com.apiclient.dto;


import com.apiclient.pojo.ProductLineEquipment;
import com.apiclient.vo.GrindingVO;

import java.util.List;

/**
 * Created by logan on 2018/6/14.
 */
public class InFactoryDTO {

    private ProductLineEquipment grindingEquipment;

    private List<GrindingVO> grindingVOS;

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

    public ProductLineEquipment getGrindingEquipment() {
        return grindingEquipment;
    }

    public void setGrindingEquipment(ProductLineEquipment grindingEquipment) {
        this.grindingEquipment = grindingEquipment;
    }

    public List<GrindingVO> getGrindingVOS() {
        return grindingVOS;
    }

    public void setGrindingVOS(List<GrindingVO> grindingVOS) {
        this.grindingVOS = grindingVOS;
    }
}
