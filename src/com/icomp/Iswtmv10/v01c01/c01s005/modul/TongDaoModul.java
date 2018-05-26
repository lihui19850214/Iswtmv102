package com.icomp.Iswtmv10.v01c01.c01s005.modul;

/**
 * Created by ${Nice} on 2017/6/16.
 */

public class TongDaoModul {
    private boolean isCheck = false;
    private String caiLiao;
    private String groupNum;

    private String synthesisParametersCode;
    private String rFID;

    public String getSynthesisParametersCode() {
        return synthesisParametersCode;
    }

    public void setSynthesisParametersCode(String synthesisParametersCode) {
        this.synthesisParametersCode = synthesisParametersCode;
    }

    public String getrFID() {
        return rFID;
    }

    public void setrFID(String rFID) {
        this.rFID = rFID;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getCaiLiao() {
        return caiLiao;
    }

    public void setCaiLiao(String caiLiao) {
        this.caiLiao = caiLiao;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }
}
