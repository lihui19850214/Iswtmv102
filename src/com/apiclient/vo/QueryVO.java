package com.apiclient.vo;


import com.apiclient.pojo.*;

import java.util.List;

/**
 * Created by logan on 2018/6/16.
 */
public class QueryVO {

    private SynthesisCuttingToolBind synthesisCuttingToolBind;

    private ProductLineEquipment equipment;

    private ProductLineProcess process;

    private ProductLineAssemblyline assemblyline;

    private List<ProductLine> partsList;

    private SynthesisCuttingToolConfig config;

    private List<ProductLineEquipment> equipmentList;

    public List<ProductLineEquipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<ProductLineEquipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public SynthesisCuttingToolConfig getConfig() {
        return config;
    }

    public void setConfig(SynthesisCuttingToolConfig config) {
        this.config = config;
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

    public ProductLineProcess getProcess() {
        return process;
    }

    public void setProcess(ProductLineProcess process) {
        this.process = process;
    }

    public ProductLineAssemblyline getAssemblyline() {
        return assemblyline;
    }

    public void setAssemblyline(ProductLineAssemblyline assemblyline) {
        this.assemblyline = assemblyline;
    }

    public List<ProductLine> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<ProductLine> partsList) {
        this.partsList = partsList;
    }
}
