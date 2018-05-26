package com.apiclient.vo;

import com.apiclient.pojo.DjCircleKanbanAkp;
import com.apiclient.pojo.DjMtlAkp;
import com.apiclient.pojo.DjOutapplyAkp;

import java.util.Date;
import java.util.List;

/**
 * Created by logan on 2018/5/7.
 */
public class WriteBackVO {

    private String daoshen;

    private String note;

    private Integer batchno;

    private String susr20;

    private DjOutapplyAkp djOutapplyAkp;

    private DjCircleKanbanAkp djCircleKanbanAkp;

    private DjMtlAkp djMtlAkp;

    private String orderNum;

    private Integer jgcs;

    private Integer lhcs;

    private Integer returnType;

    private Date createTime;

    private Integer createBy;

    private String sharpenTimes;

    private String bfCause;

    private String bfStatus;

    private String rfid;

    public String getDaoshen() {
        return daoshen;
    }

    public void setDaoshen(String daoshen) {
        this.daoshen = daoshen;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private List<String> daoshenhao;

    public List<String> getDaoshenhao() {
        return daoshenhao;
    }

    public void setDaoshenhao(List<String> daoshenhao) {
        this.daoshenhao = daoshenhao;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getSusr20() {
        return susr20;
    }

    public void setSusr20(String susr20) {
        this.susr20 = susr20;
    }

    public String getBfStatus() {
        return bfStatus;
    }

    public void setBfStatus(String bfStatus) {
        this.bfStatus = bfStatus;
    }

    public String getBfCause() {
        return bfCause;
    }

    public void setBfCause(String bfCause) {
        this.bfCause = bfCause;
    }

    public String getSharpenTimes() {
        return sharpenTimes;
    }

    public void setSharpenTimes(String sharpenTimes) {
        this.sharpenTimes = sharpenTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public Integer getLhcs() {
        return lhcs;
    }

    public void setLhcs(Integer lhcs) {
        this.lhcs = lhcs;
    }

    public Integer getJgcs() {
        return jgcs;
    }

    public void setJgcs(Integer jgcs) {
        this.jgcs = jgcs;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getBatchno() {
        return batchno;
    }

    public void setBatchno(Integer batchno) {
        this.batchno = batchno;
    }

    public DjOutapplyAkp getDjOutapplyAkp() {
        return djOutapplyAkp;
    }

    public void setDjOutapplyAkp(DjOutapplyAkp djOutapplyAkp) {
        this.djOutapplyAkp = djOutapplyAkp;
    }

    public DjCircleKanbanAkp getDjCircleKanbanAkp() {
        return djCircleKanbanAkp;
    }

    public void setDjCircleKanbanAkp(DjCircleKanbanAkp djCircleKanbanAkp) {
        this.djCircleKanbanAkp = djCircleKanbanAkp;
    }

    public DjMtlAkp getDjMtlAkp() {
        return djMtlAkp;
    }

    public void setDjMtlAkp(DjMtlAkp djMtlAkp) {
        this.djMtlAkp = djMtlAkp;
    }
}
