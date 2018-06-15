package com.apiclient.dto;


import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.SynthesisCuttingToolLocation;

/**
 * Created by logan on 2018/6/15.
 */
public class BindBladeDTO {

    private CuttingToolBind cuttingToolBind;

    private SynthesisCuttingToolLocation location;

    public CuttingToolBind getCuttingToolBind() {
        return cuttingToolBind;
    }

    public void setCuttingToolBind(CuttingToolBind cuttingToolBind) {
        this.cuttingToolBind = cuttingToolBind;
    }

    public SynthesisCuttingToolLocation getLocation() {
        return location;
    }

    public void setLocation(SynthesisCuttingToolLocation location) {
        this.location = location;
    }
}
