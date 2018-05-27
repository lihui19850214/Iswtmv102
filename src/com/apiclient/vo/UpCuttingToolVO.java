package com.apiclient.vo;

/**
 * Created by logan on 2018/4/30.
 */
public class UpCuttingToolVO {

    private String upCode;

    private Integer upCount;

    private String rfidCode;

    private String bladeCode;

    public String getBladeCode() {
        return bladeCode;
    }

    public void setBladeCode(String bladeCode) {
        this.bladeCode = bladeCode;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public String getUpCode() {
        return upCode;
    }

    public void setUpCode(String upCode) {
        this.upCode = upCode;
    }

    public Integer getUpCount() {
        return upCount;
    }

    public void setUpCount(Integer upCount) {
        this.upCount = upCount;
    }
}
