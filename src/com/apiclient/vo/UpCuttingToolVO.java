package com.apiclient.vo;


import com.apiclient.pojo.CuttingTool;
import com.apiclient.pojo.CuttingToolBind;

/**
 * Created by logan on 2018/4/30.
 */
public class UpCuttingToolVO {

    private CuttingTool cuttingTool;

    private CuttingToolBind cuttingToolBind;

    private String upCode;

    private Integer upCount;

    private String upRfidLaserCode;

    private String bladeCode;

    private String businessCode;

    public CuttingTool getCuttingTool() {
        return cuttingTool;
    }

    public void setCuttingTool(CuttingTool cuttingTool) {
        this.cuttingTool = cuttingTool;
    }

    public CuttingToolBind getCuttingToolBind() {
        return cuttingToolBind;
    }

    public void setCuttingToolBind(CuttingToolBind cuttingToolBind) {
        this.cuttingToolBind = cuttingToolBind;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBladeCode() {
        return bladeCode;
    }

    public void setBladeCode(String bladeCode) {
        this.bladeCode = bladeCode;
    }

    public String getUpRfidLaserCode() {
        return upRfidLaserCode;
    }

    public void setUpRfidLaserCode(String upRfidLaserCode) {
        this.upRfidLaserCode = upRfidLaserCode;
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
