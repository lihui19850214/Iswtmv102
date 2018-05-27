package com.apiclient.vo;

import com.apiclient.pojo.RfidContainer;
import com.apiclient.pojo.SynthesisCuttingToolBind;

/**
 * Created by logan on 2018/5/11.
 */
public class WBUnInstallVO extends WriteBaseVO {

    private UnBindEquipmentVO unBindEquipmentVO;

    private SynthesisCuttingToolBind synthesisCuttingToolBind;

    private RfidContainer rfidContainer;

    public UnBindEquipmentVO getUnBindEquipmentVO() {
        return unBindEquipmentVO;
    }

    public void setUnBindEquipmentVO(UnBindEquipmentVO unBindEquipmentVO) {
        this.unBindEquipmentVO = unBindEquipmentVO;
    }

    public SynthesisCuttingToolBind getSynthesisCuttingToolBind() {
        return synthesisCuttingToolBind;
    }

    public void setSynthesisCuttingToolBind(SynthesisCuttingToolBind synthesisCuttingToolBind) {
        this.synthesisCuttingToolBind = synthesisCuttingToolBind;
    }

    public RfidContainer getRfidContainer() {
        return rfidContainer;
    }

    public void setRfidContainer(RfidContainer rfidContainer) {
        this.rfidContainer = rfidContainer;
    }
}
