package com.apiclient.vo;

import com.apiclient.pojo.SynthesisCuttingToolBind;
import com.apiclient.pojo.SynthesisCuttingToolExchange;

import java.util.List;

/**
 * Created by logan on 2018/4/30.
 */
public class ExChangeVO {

    private SynthesisCuttingToolBind synthesisCuttingToolBind;

    private SynthesisCuttingToolExchange synthesisCuttingToolExchange;

    private List<DownCuttingToolVO> downCuttingToolVOS;

    private List<UpCuttingToolVO> upCuttingToolVOS;

    public List<UpCuttingToolVO> getUpCuttingToolVOS() {
        return upCuttingToolVOS;
    }

    public void setUpCuttingToolVOS(List<UpCuttingToolVO> upCuttingToolVOS) {
        this.upCuttingToolVOS = upCuttingToolVOS;
    }

    public SynthesisCuttingToolBind getSynthesisCuttingToolBind() {
        return synthesisCuttingToolBind;
    }

    public void setSynthesisCuttingToolBind(SynthesisCuttingToolBind synthesisCuttingToolBind) {
        this.synthesisCuttingToolBind = synthesisCuttingToolBind;
    }

    public SynthesisCuttingToolExchange getSynthesisCuttingToolExchange() {
        return synthesisCuttingToolExchange;
    }

    public void setSynthesisCuttingToolExchange(SynthesisCuttingToolExchange synthesisCuttingToolExchange) {
        this.synthesisCuttingToolExchange = synthesisCuttingToolExchange;
    }

    public List<DownCuttingToolVO> getDownCuttingToolVOS() {
        return downCuttingToolVOS;
    }

    public void setDownCuttingToolVOS(List<DownCuttingToolVO> downCuttingToolVOS) {
        this.downCuttingToolVOS = downCuttingToolVOS;
    }
}
