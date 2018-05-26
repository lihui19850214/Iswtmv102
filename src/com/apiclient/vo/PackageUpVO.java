package com.apiclient.vo;

import com.apiclient.pojo.SynthesisCuttingToolBind;

import java.util.List;

/**
 * Created by logan on 2018/4/30.
 */
public class PackageUpVO {

    private SynthesisCuttingToolBind synthesisCuttingToolBind;

    private List<UpCuttingToolVO> upCuttingToolVOS;

    public SynthesisCuttingToolBind getSynthesisCuttingToolBind() {
        return synthesisCuttingToolBind;
    }

    public void setSynthesisCuttingToolBind(SynthesisCuttingToolBind synthesisCuttingToolBind) {
        this.synthesisCuttingToolBind = synthesisCuttingToolBind;
    }

    public List<UpCuttingToolVO> getUpCuttingToolVOS() {
        return upCuttingToolVOS;
    }

    public void setUpCuttingToolVOS(List<UpCuttingToolVO> upCuttingToolVOS) {
        this.upCuttingToolVOS = upCuttingToolVOS;
    }
}
