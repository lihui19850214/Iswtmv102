package com.apiclient.vo;

import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolBind;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by logan on 2018/5/11.
 */
public class WriteBaseVO {

    private AuthCustomer authCustomer;
    //批次
    private Integer batchNo;

    private String opratorName;

    private Timestamp opratorTime;

    List<CuttingToolBind> cuttingToolBinds;

    public List<CuttingToolBind> getCuttingToolBinds() {
        return cuttingToolBinds;
    }

    public void setCuttingToolBinds(List<CuttingToolBind> cuttingToolBinds) {
        this.cuttingToolBinds = cuttingToolBinds;
    }

    public Integer getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    public String getOpratorName() {
        return opratorName;
    }

    public void setOpratorName(String opratorName) {
        this.opratorName = opratorName;
    }

    public Timestamp getOpratorTime() {
        return opratorTime;
    }

    public void setOpratorTime(Timestamp opratorTime) {
        this.opratorTime = opratorTime;
    }

    public AuthCustomer getAuthCustomer() {
        return authCustomer;
    }

    public void setAuthCustomer(AuthCustomer authCustomer) {
        this.authCustomer = authCustomer;
    }
}
