package com.apiclient.vo;

import com.apiclient.pojo.ProductLineAxle;
import com.apiclient.pojo.ProductLineEquipment;
import com.apiclient.pojo.SynthesisCuttingToolBind;

/**
 * Created by logan on 2018/4/30.
 */
public class BindEquipmentVO {

    private SynthesisCuttingToolBind synthesisCuttingToolBind;

    private ProductLineEquipment equipment;

    private ProductLineAxle axle;

    public ProductLineAxle getAxle() {
        return axle;
    }

    public void setAxle(ProductLineAxle axle) {
        this.axle = axle;
    }

    public SynthesisCuttingToolBind getSynthesisCuttingToolBind() {
        return synthesisCuttingToolBind;
    }

    public void setSynthesisCuttingToolBind(SynthesisCuttingToolBind synthesisCuttingToolBind) {
        this.synthesisCuttingToolBind = synthesisCuttingToolBind;
    }

    public ProductLineEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(ProductLineEquipment equipment) {
        this.equipment = equipment;
    }
}
