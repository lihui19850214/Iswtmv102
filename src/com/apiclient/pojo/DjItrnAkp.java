package com.apiclient.pojo;

import java.io.Serializable;
import java.util.Date;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 DjItrnAkp 
*/
public class DjItrnAkp implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  序列
     */
    private String id;

    /**
     * @fieldName batchno
    * @fieldType  Integer
    * @Description  批次
     */
    private Integer batchno;
    /**
     * @fieldName whCode
    * @fieldType  String
    * @Description  仓库 ‘FSDJ’
     */
    private String whCode;
    /**
     * @fieldName ownerCode
    * @fieldType  String
    * @Description  供应商号
     */
    private String ownerCode;
    /**
     * @fieldName mtlCode
    * @fieldType  String
    * @Description  物料号，加外围类型
     */
    private String mtlCode;
    /**
     * @fieldName ggxh
    * @fieldType  String
    * @Description  规格型号
     */
    private String ggxh;
    /**
     * @fieldName gongw
    * @fieldType  String
    * @Description  工位
     */
    private String gongw;
    /**
     * @fieldName shengcx
    * @fieldType  String
    * @Description  生产线
     */
    private String shengcx;
    /**
     * @fieldName daojCase
    * @fieldType  String
    * @Description  刀具号
     */
    private String daojCase;
    /**
     * @fieldName jgsm
    * @fieldType  String
    * @Description  加工寿命
     */
    private String jgsm;
    /**
     * @fieldName price
    * @fieldType  String
    * @Description  价格
     */
    private String price;
    /**
     * @fieldName type
    * @fieldType  String
    * @Description  类型：N生成 U领用 R归还 S1车间刃磨 S2外委刃磨 S3直接外委 SR刃磨归还 SD刃磨报废 D报废
     */
    private String type;
    /**
     * @fieldName wwcode
    * @fieldType  String
    * @Description  外委单号
     */
    private String wwcode;
    /**
     * @fieldName jgcs
    * @fieldType  String
    * @Description  实际加工寿命，加工次数
     */
    private String jgcs;
    /**
     * @fieldName lhcs
    * @fieldType  String
    * @Description  刀具轮回次数，值为 palabel_cms 中remark03(车间刃磨次数)+remark04(外委刃磨次数)+1
     */
    private String lhcs;
    /**
     * @fieldName returnType
    * @fieldType  String
    * @Description  归还类型 1外委归还 2刃磨归还 3报废
     */
    private String returnType;
    /**
     * @fieldName createDate
    * @fieldType  Date
    * @Description  创建时间
     */
    private Date createDate;
    /**
     * @fieldName createBy
    * @fieldType  Integer
    * @Description  创建人
     */
    private Integer createBy;
    /**
     * @fieldName processTimes
    * @fieldType  String
    * @Description  加工次数
     */
    private Integer processTimes;
    /**
     * @fieldName sharpenTimes
    * @fieldType  String
    * @Description  刃磨次数
     */
    private Integer sharpenTimes;
    /**
     * @fieldName bfCause
    * @fieldType  String
    * @Description  报废原因
     */
    private String bfCause;
    /**
     * @fieldName bfStatus
    * @fieldType  String
    * @Description  报废状态（0.断刀1.丢刀2.到寿3.出库报废4.其他）
     */
    private String bfStatus;
    /**
     * @fieldName casecode
    * @fieldType  String
    * @Description  刀签号
     */
    private String casecode;
    /**
     * @fieldName cKanbanCode
    * @fieldType  String
    * @Description  领料条码,'CK'
     */
    private String cKanbanCode;
    /**
     * @fieldName not
    * @fieldType  String
    * @Description  备注长文本
     */
    private String note;
    /**
     * @fieldName kanbanCode
    * @fieldType  String
    * @Description  出库看板号“DJLY”
     */
    private String kanbanCode;
    /**
     * @fieldName wwOwner
    * @fieldType  String
    * @Description  外委供应商
     */
    private String wwOwner;
    /**
     * @fieldName maxDjh
    * @fieldType  String
    * @Description  当前最大刀头号
     */
    private String maxDjh;
    /**
     * @fieldName rfid
    * @fieldType  String
    * @Description  当前RFID
     */
    private String rfid;
    /**
     * @fieldName zcCode
    * @fieldType  String
    * @Description  外委资材单号
     */
    private String zcCode;




    /* 序列 */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBatchno() {
        return batchno;
    }

    public void setBatchno(Integer batchno) {
        this.batchno = batchno;
    }
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }
    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }
    public String getMtlCode() {
        return mtlCode;
    }

    public void setMtlCode(String mtlCode) {
        this.mtlCode = mtlCode;
    }
    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }
    public String getGongw() {
        return gongw;
    }

    public void setGongw(String gongw) {
        this.gongw = gongw;
    }
    public String getShengcx() {
        return shengcx;
    }

    public void setShengcx(String shengcx) {
        this.shengcx = shengcx;
    }
    public String getDaojCase() {
        return daojCase;
    }

    public void setDaojCase(String daojCase) {
        this.daojCase = daojCase;
    }
    public String getJgsm() {
        return jgsm;
    }

    public void setJgsm(String jgsm) {
        this.jgsm = jgsm;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getWwcode() {
        return wwcode;
    }

    public void setWwcode(String wwcode) {
        this.wwcode = wwcode;
    }
    public String getJgcs() {
        return jgcs;
    }

    public void setJgcs(String jgcs) {
        this.jgcs = jgcs;
    }
    public String getLhcs() {
        return lhcs;
    }

    public void setLhcs(String lhcs) {
        this.lhcs = lhcs;
    }
    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getProcessTimes() {
        return processTimes;
    }

    public void setProcessTimes(Integer processTimes) {
        this.processTimes = processTimes;
    }

    public Integer getSharpenTimes() {
        return sharpenTimes;
    }

    public void setSharpenTimes(Integer sharpenTimes) {
        this.sharpenTimes = sharpenTimes;
    }

    public String getBfCause() {
        return bfCause;
    }

    public void setBfCause(String bfCause) {
        this.bfCause = bfCause;
    }
    public String getBfStatus() {
        return bfStatus;
    }

    public void setBfStatus(String bfStatus) {
        this.bfStatus = bfStatus;
    }
    public String getCasecode() {
        return casecode;
    }

    public void setCasecode(String casecode) {
        this.casecode = casecode;
    }
    public String getCKanbanCode() {
        return cKanbanCode;
    }

    public void setCKanbanCode(String cKanbanCode) {
        this.cKanbanCode = cKanbanCode;
    }

    public String getcKanbanCode() {
        return cKanbanCode;
    }

    public void setcKanbanCode(String cKanbanCode) {
        this.cKanbanCode = cKanbanCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getKanbanCode() {
        return kanbanCode;
    }

    public void setKanbanCode(String kanbanCode) {
        this.kanbanCode = kanbanCode;
    }
    public String getWwOwner() {
        return wwOwner;
    }

    public void setWwOwner(String wwOwner) {
        this.wwOwner = wwOwner;
    }
    public String getMaxDjh() {
        return maxDjh;
    }

    public void setMaxDjh(String maxDjh) {
        this.maxDjh = maxDjh;
    }
    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }
    public String getZcCode() {
        return zcCode;
    }

    public void setZcCode(String zcCode) {
        this.zcCode = zcCode;
    }



}
