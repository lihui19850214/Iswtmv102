package com.apiclient.vo;

/**
 * Created by logan on 2018/4/30.
 */
public class DownCuttingToolVO {

    private String downCode;

    private Integer downCount;

    private Integer downLostCount;

    private String downRfidCode;

    private String bladeCode;

    private Integer lost;

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }

    public String getDownRfidCode() {
        return downRfidCode;
    }

    public void setDownRfidCode(String downRfidCode) {
        this.downRfidCode = downRfidCode;
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

    public String getBladeCode() {
        return bladeCode;
    }

    public void setBladeCode(String bladeCode) {
        this.bladeCode = bladeCode;
    }
}
