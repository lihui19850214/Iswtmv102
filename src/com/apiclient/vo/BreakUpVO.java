package com.apiclient.vo;

import com.apiclient.pojo.SynthesisCuttingToolBind;

import java.util.List;

/**
 * Created by logan on 2018/4/30.
 */
public class BreakUpVO {

    private SynthesisCuttingToolBind synthesisCuttingToolBind;

    private List<DownCuttingToolVO> downCuttingToolVOS;


    public SynthesisCuttingToolBind getSynthesisCuttingToolBind() {
        return synthesisCuttingToolBind;
    }

    public void setSynthesisCuttingToolBind(SynthesisCuttingToolBind synthesisCuttingToolBind) {
        this.synthesisCuttingToolBind = synthesisCuttingToolBind;
    }

    public List<DownCuttingToolVO> getDownCuttingToolVOS() {
        return downCuttingToolVOS;
    }

    public void setDownCuttingToolVOS(List<DownCuttingToolVO> downCuttingToolVOS) {
        this.downCuttingToolVOS = downCuttingToolVOS;
    }

}
