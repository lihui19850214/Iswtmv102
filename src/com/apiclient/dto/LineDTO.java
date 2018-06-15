package com.apiclient.dto;


import com.apiclient.vo.ProductLineEquipmentVO;
import com.apiclient.vo.ProductLinePartsVO;
import com.apiclient.vo.SynthesisCuttingToolBindVO;

/**
 * Created by logan on 2018/6/13.
 */
public class LineDTO {

    //合成刀
    private SynthesisCuttingToolBindVO synthesisCuttingToolBindVO;

    //设备
    private ProductLineEquipmentVO equipmentVO;

    //零件
    private ProductLinePartsVO partsVO;

    //卸下原因
    private String unBindReason;

    //加工数量
    private Integer processingCount;

    public ProductLinePartsVO getPartsVO() {
        return partsVO;
    }

    public void setPartsVO(ProductLinePartsVO partsVO) {
        this.partsVO = partsVO;
    }

    public String getUnBindReason() {
        return unBindReason;
    }

    public void setUnBindReason(String unBindReason) {
        this.unBindReason = unBindReason;
    }

    public Integer getProcessingCount() {
        return processingCount;
    }

    public void setProcessingCount(Integer processingCount) {
        this.processingCount = processingCount;
    }

    public SynthesisCuttingToolBindVO getSynthesisCuttingToolBindVO() {
        return synthesisCuttingToolBindVO;
    }

    public void setSynthesisCuttingToolBindVO(SynthesisCuttingToolBindVO synthesisCuttingToolBindVO) {
        this.synthesisCuttingToolBindVO = synthesisCuttingToolBindVO;
    }

    public ProductLineEquipmentVO getEquipmentVO() {
        return equipmentVO;
    }

    public void setEquipmentVO(ProductLineEquipmentVO equipmentVO) {
        this.equipmentVO = equipmentVO;
    }
}
