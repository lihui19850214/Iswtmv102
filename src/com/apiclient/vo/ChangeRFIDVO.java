package com.apiclient.vo;

/**
 * Created by logan on 2018/5/6.
 */
public class ChangeRFIDVO {


    private String rfidCode;

    private String toolCode;

    private String laserCode;

    private String newLaserCode;

    private Integer type;

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getLaserCode() {
        return laserCode;
    }

    public void setLaserCode(String laserCode) {
        this.laserCode = laserCode;
    }

    public String getNewLaserCode() {
        return newLaserCode;
    }

    public void setNewLaserCode(String newLaserCode) {
        this.newLaserCode = newLaserCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
