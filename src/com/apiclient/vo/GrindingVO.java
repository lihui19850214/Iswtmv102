package com.apiclient.vo;


import com.apiclient.pojo.CuttingTool;
import com.apiclient.pojo.CuttingToolBind;

/**
 * Created by logan on 2018/6/14.
 */
public class GrindingVO {

    private CuttingTool cuttingTool;

    private CuttingToolBind cuttingToolBind;

    private Integer grindingCount;

    public CuttingTool getCuttingTool() {
        return cuttingTool;
    }

    public void setCuttingTool(CuttingTool cuttingTool) {
        this.cuttingTool = cuttingTool;
    }

    public CuttingToolBind getCuttingToolBind() {
        return cuttingToolBind;
    }

    public void setCuttingToolBind(CuttingToolBind cuttingToolBind) {
        this.cuttingToolBind = cuttingToolBind;
    }

    public Integer getGrindingCount() {
        return grindingCount;
    }

    public void setGrindingCount(Integer grindingCount) {
        this.grindingCount = grindingCount;
    }
}
