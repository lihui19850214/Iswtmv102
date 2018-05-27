package com.apiclient.vo;

import com.apiclient.pojo.*;

import java.util.List;

/**
 * Created by logan on 2018/5/12.
 */
public class QueryEquipmentByRfidVO {

    private String rfidCode;

    private String synthesisCuttingToolCode;

    private ProductLineEquipment productLineEquipment;

    private List<ProductLine> productLines;

    private List<ProductLineAssemblyline> assemblylines;

    public List<ProductLine> getProductLines() {
        return productLines;
    }

    public void setProductLines(List<ProductLine> productLines) {
        this.productLines = productLines;
    }

    public String getSynthesisCuttingToolCode() {
        return synthesisCuttingToolCode;
    }

    public void setSynthesisCuttingToolCode(String synthesisCuttingToolCode) {
        this.synthesisCuttingToolCode = synthesisCuttingToolCode;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public ProductLineEquipment getProductLineEquipment() {
        return productLineEquipment;
    }

    public void setProductLineEquipment(ProductLineEquipment productLineEquipment) {
        this.productLineEquipment = productLineEquipment;
    }

    public List<ProductLineAssemblyline> getAssemblylines() {
        return assemblylines;
    }

    public void setAssemblylines(List<ProductLineAssemblyline> assemblylines) {
        this.assemblylines = assemblylines;
    }
}
