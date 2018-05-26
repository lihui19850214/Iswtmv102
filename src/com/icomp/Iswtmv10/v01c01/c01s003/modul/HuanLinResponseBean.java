package com.icomp.Iswtmv10.v01c01.c01s003.modul;

/**
 * Created by ${Nice} on 2017/7/3.
 */

public class HuanLinResponseBean {

    /**
     * success : true
     * message :
     * data : {"toolID":"9e0021a2-7467-49fa-8906-c15b41528790","materialNum":"B3000","requestNum":10,"inventory":"250","libraryCodeID":"","toolType":"2"}
     */

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
        /**
         * toolID : 9e0021a2-7467-49fa-8906-c15b41528790
         * materialNum : B3000
         * requestNum : 10
         * inventory : 250
         * libraryCodeID :
         * toolType : 2
         */

        private String toolID;
        private String materialNum;
        private int requestNum;
        private String inventory;
        private String libraryCodeID;
        private String toolType;
        private String rfidCode;

        public String getRfidCode() {
            return rfidCode;
        }

        public void setRfidCode(String rfidCode) {
            this.rfidCode = rfidCode;
        }

        public String getToolID() {
            return toolID;
        }

        public void setToolID(String toolID) {
            this.toolID = toolID;
        }

        public String getMaterialNum() {
            return materialNum;
        }

        public void setMaterialNum(String materialNum) {
            this.materialNum = materialNum;
        }

        public int getRequestNum() {
            return requestNum;
        }

        public void setRequestNum(int requestNum) {
            this.requestNum = requestNum;
        }

        public String getInventory() {
            return inventory;
        }

        public void setInventory(String inventory) {
            this.inventory = inventory;
        }

        public String getLibraryCodeID() {
            return libraryCodeID;
        }

        public void setLibraryCodeID(String libraryCodeID) {
            this.libraryCodeID = libraryCodeID;
        }

        public String getToolType() {
            return toolType;
        }

        public void setToolType(String toolType) {
            this.toolType = toolType;
        }
    }
}
