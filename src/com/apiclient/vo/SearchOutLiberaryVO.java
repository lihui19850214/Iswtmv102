package com.apiclient.vo;

import com.apiclient.pojo.DjOutapplyAkp;

/**
 * Created by logan on 2018/5/9.
 */
public class SearchOutLiberaryVO {

    //订单号
    private String applyno;
    //物料号
    private String mtlno;
    //材料号
    private String cuttingtollBusinessCode;
    //型号规格
    private String specifications;
    //物料名称
    private String name;
    //生产线
    private String productline;
    //工位
    private String location;
    //要货数量
    private String unitqty;
    //刀具类型
    private String cuttingToolType;
    //修模方式
    private String grinding;

    private DjOutapplyAkp djOutapplyAkp;

    private String cuttingToolConsumeType;


    public DjOutapplyAkp getDjOutapplyAkp() {
        return djOutapplyAkp;
    }

    public void setDjOutapplyAkp(DjOutapplyAkp djOutapplyAkp) {
        this.djOutapplyAkp = djOutapplyAkp;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getApplyno() {
        return applyno;
    }

    public void setApplyno(String applyno) {
        this.applyno = applyno;
    }

    public String getMtlno() {
        return mtlno;
    }

    public void setMtlno(String mtlno) {
        this.mtlno = mtlno;
    }

    public String getCuttingtollBusinessCode() {
        return cuttingtollBusinessCode;
    }

    public void setCuttingtollBusinessCode(String cuttingtollBusinessCode) {
        this.cuttingtollBusinessCode = cuttingtollBusinessCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductline() {
        return productline;
    }

    public void setProductline(String productline) {
        this.productline = productline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUnitqty() {
        return unitqty;
    }

    public void setUnitqty(String unitqty) {
        this.unitqty = unitqty;
    }

    public String getCuttingToolType() {
        return cuttingToolType;
    }

    public void setCuttingToolType(String cuttingToolType) {
        this.cuttingToolType = cuttingToolType;
    }

    public String getGrinding() {
        return grinding;
    }

    public void setGrinding(String grinding) {
        this.grinding = grinding;
    }

    public String getCuttingToolConsumeType() {
        return cuttingToolConsumeType;
    }

    public void setCuttingToolConsumeType(String cuttingToolConsumeType) {
        this.cuttingToolConsumeType = cuttingToolConsumeType;
    }
}
