package com.apiclient.vo;

/**
 * Created by logan on 2018/4/30.
 */
public class DownCuttingToolVO {

    private String downCode;

    private Integer downCount;

    private Integer downLostCount;

    private String downRfidLaserCode;

    private String bladeCode;

    private Integer needBind;

    public String getBladeCode() {
        return bladeCode;
    }

    public void setBladeCode(String bladeCode) {
        this.bladeCode = bladeCode;
    }

    public Integer getNeedBind() {
        return needBind;
    }

    public void setNeedBind(Integer needBind) {
        this.needBind = needBind;
    }

    public String getDownRfidLaserCode() {
        return downRfidLaserCode;
    }

    public void setDownRfidLaserCode(String downRfidLaserCode) {
        this.downRfidLaserCode = downRfidLaserCode;
    }

    public Integer getDownLostCount() {
        return downLostCount;
    }

    public void setDownLostCount(Integer downLostCount) {
        this.downLostCount = downLostCount;
    }

    public String getDownCode() {
        return downCode;
    }

    public void setDownCode(String downCode) {
        this.downCode = downCode;
    }

    public Integer getDownCount() {
        return downCount;
    }

    public void setDownCount(Integer downCount) {
        this.downCount = downCount;
    }
}
