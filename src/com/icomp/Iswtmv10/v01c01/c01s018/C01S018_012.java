package com.icomp.Iswtmv10.v01c01.c01s018;

/**
 * 厂内修磨一体刀的参数类
 * Created by FanLL on 2017/7/7.
 */

public class C01S018_012 {

    private boolean success;
    private String message;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String synthesisParametersCode;
        private String rfidContainerID;
        private String code;
        private String authorizationFlgs;
        private String laserCode;

        public String getSynthesisParametersCode() {
            return synthesisParametersCode;
        }

        public void setSynthesisParametersCode(String synthesisParametersCode) {
            this.synthesisParametersCode = synthesisParametersCode;
        }

        public String getRfidContainerID() {
            return rfidContainerID;
        }

        public void setRfidContainerID(String rfidContainerID) {
            this.rfidContainerID = rfidContainerID;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getAuthorizationFlgs() {
            return authorizationFlgs;
        }

        public void setAuthorizationFlgs(String authorizationFlgs) {
            this.authorizationFlgs = authorizationFlgs;
        }

        public String getLaserCode() {
            return laserCode;
        }

        public void setLaserCode(String laserCode) {
            this.laserCode = laserCode;
        }

    }

}
