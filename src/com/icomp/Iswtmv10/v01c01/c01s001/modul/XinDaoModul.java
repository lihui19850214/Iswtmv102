package com.icomp.Iswtmv10.v01c01.c01s001.modul;

import java.util.List;

/**
 * Created by ${Nice} on 2017/6/29.
 */

public class XinDaoModul {

    /**
     * success : true
     * message :
     * data : {"toolID":"9e0021a2-7467-49fa-8906-c15b41528791","materialNum":"B3001","libraryCodeID":"12-01-04","unitnumber":"100","procuredBatchCount":[{"toolsOrdeNO":"8001774296","procuredBatch":"0050072171","procuredCount":"300"},{"toolsOrdeNO":"8001774178","procuredBatch":"0050072522","procuredCount":"50"},{"toolsOrdeNO":"8001774178","procuredBatch":"0050072522","procuredCount":"100"}]}
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
         * toolID : 9e0021a2-7467-49fa-8906-c15b41528791
         * materialNum : B3001
         * libraryCodeID : 12-01-04
         * unitnumber : 100
         * procuredBatchCount : [{"toolsOrdeNO":"8001774296","procuredBatch":"0050072171","procuredCount":"300"},{"toolsOrdeNO":"8001774178","procuredBatch":"0050072522","procuredCount":"50"},{"toolsOrdeNO":"8001774178","procuredBatch":"0050072522","procuredCount":"100"}]
         */

        private String toolID;
        private String materialNum;
        private String libraryCodeID;
        private String unitnumber;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        private String code;
        private List<ProcuredBatchCountBean> procuredBatchCount;

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

        public String getLibraryCodeID() {
            return libraryCodeID;
        }

        public void setLibraryCodeID(String libraryCodeID) {
            this.libraryCodeID = libraryCodeID;
        }

        public String getUnitnumber() {
            return unitnumber;
        }

        public void setUnitnumber(String unitnumber) {
            this.unitnumber = unitnumber;
        }

        public List<ProcuredBatchCountBean> getProcuredBatchCount() {
            return procuredBatchCount;
        }

        public void setProcuredBatchCount(List<ProcuredBatchCountBean> procuredBatchCount) {
            this.procuredBatchCount = procuredBatchCount;
        }

        public static class ProcuredBatchCountBean {
            /**
             * toolsOrdeNO : 8001774296
             * procuredBatch : 0050072171
             * procuredCount : 300
             */

            private String toolsOrdeNO;
            private String procuredBatch;
            private String procuredCount;

            public String getToolsOrdeNO() {
                return toolsOrdeNO;
            }

            public void setToolsOrdeNO(String toolsOrdeNO) {
                this.toolsOrdeNO = toolsOrdeNO;
            }

            public String getProcuredBatch() {
                return procuredBatch;
            }

            public void setProcuredBatch(String procuredBatch) {
                this.procuredBatch = procuredBatch;
            }

            public String getProcuredCount() {
                return procuredCount;
            }

            public void setProcuredCount(String procuredCount) {
                this.procuredCount = procuredCount;
            }
        }
    }
}
