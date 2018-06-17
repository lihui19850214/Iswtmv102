package com.apiclient.dto;

import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.vo.RfidContainerVO;

/**
 * Created by logan on 2018/6/14.
 */
public class RunningDTO {

    private CuttingToolBind cuttingToolBind;

    private String status;

    private RfidContainerVO rfidContainerVO;

    public RfidContainerVO getRfidContainerVO() {
        return rfidContainerVO;
    }

    public void setRfidContainerVO(RfidContainerVO rfidContainerVO) {
        this.rfidContainerVO = rfidContainerVO;
    }

    public CuttingToolBind getCuttingToolBind() {
        return cuttingToolBind;
    }

    public void setCuttingToolBind(CuttingToolBind cuttingToolBind) {
        this.cuttingToolBind = cuttingToolBind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
