package com.apiclient.vo;

/**
 * Created by logan on 2018/5/12.
 */
public class SharpenVO {

    private String cuttingToolCode;

    private String cuttingToolBusinessCode;

    private String cuttingToolBladeCode;

    private Integer count;

    private String status;

    public String getCuttingToolCode() {
        return cuttingToolCode;
    }

    public void setCuttingToolCode(String cuttingToolCode) {
        this.cuttingToolCode = cuttingToolCode;
    }

    public String getCuttingToolBladeCode() {
        return cuttingToolBladeCode;
    }

    public void setCuttingToolBladeCode(String cuttingToolBladeCode) {
        this.cuttingToolBladeCode = cuttingToolBladeCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCuttingToolBusinessCode() {
        return cuttingToolBusinessCode;
    }

    public void setCuttingToolBusinessCode(String cuttingToolBusinessCode) {
        this.cuttingToolBusinessCode = cuttingToolBusinessCode;
    }
}
