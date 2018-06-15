package com.apiclient.vo;


import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.DjCircleKanbanAkp;
import com.apiclient.pojo.DjMtlAkp;
import com.apiclient.pojo.DjOutapplyAkp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by logan on 2018/5/7.
 */
public class OutApplyVO extends  WriteBaseVO{

    private String applyno;

    private DjOutapplyAkp djOutapplyAkp;

    private DjCircleKanbanAkp djCircleKanbanAkp;

    private DjMtlAkp djMtlAkp;

    private Integer batchNo;
    //当前登陆
    private String kuguanOperatorCode;

    private AuthCustomerVO kzAuthCustomerVO;

    private AuthCustomerVO llAuthCustomerVO;

    private AuthCustomer loginUser;

    private AuthCustomer llAuthCustomer;

    private AuthCustomer kzAuthCustomer;

    private String mtlCode;

    public String getApplyno() {
        return applyno;
    }

    public void setApplyno(String applyno) {
        this.applyno = applyno;
    }

    public AuthCustomer getLlAuthCustomer() {
        return llAuthCustomer;
    }

    public void setLlAuthCustomer(AuthCustomer llAuthCustomer) {
        this.llAuthCustomer = llAuthCustomer;
    }

    public AuthCustomer getKzAuthCustomer() {
        return kzAuthCustomer;
    }

    public void setKzAuthCustomer(AuthCustomer kzAuthCustomer) {
        this.kzAuthCustomer = kzAuthCustomer;
    }

    @Override
    public Integer getBatchNo() {
        return batchNo;
    }

    @Override
    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    public AuthCustomerVO getKzAuthCustomerVO() {
        return kzAuthCustomerVO;
    }

    public void setKzAuthCustomerVO(AuthCustomerVO kzAuthCustomerVO) {
        this.kzAuthCustomerVO = kzAuthCustomerVO;
    }

    public AuthCustomerVO getLlAuthCustomerVO() {
        return llAuthCustomerVO;
    }

    public void setLlAuthCustomerVO(AuthCustomerVO llAuthCustomerVO) {
        this.llAuthCustomerVO = llAuthCustomerVO;
    }


    public AuthCustomer getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(AuthCustomer loginUser) {
        this.loginUser = loginUser;
    }

    public String getKuguanOperatorCode() {
        return kuguanOperatorCode;
    }

    public void setKuguanOperatorCode(String kuguanOperatorCode) {
        this.kuguanOperatorCode = kuguanOperatorCode;
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

    public String getMtlCode() {
        return mtlCode;
    }

    public void setMtlCode(String mtlCode) {
        this.mtlCode = mtlCode;
    }

}
