package com.apiclient.vo;

/**
 * Created by logan on 2018/5/26.
 */
public class PowerSearchVO {

    private String userCode;

    private String powerType;

    private Integer positionId;

    private String isRoot;

    private Integer[] powerIds;

    public Integer[] getPowerIds() {
        return powerIds;
    }

    public void setPowerIds(Integer[] powerIds) {
        this.powerIds = powerIds;
    }

    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }
}
