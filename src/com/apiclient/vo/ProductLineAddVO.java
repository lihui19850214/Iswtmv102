package com.apiclient.vo;

/**
 * Created by logan on 2018/5/6.
 */
public class ProductLineAddVO {

    private String assemblyLineID;

    private String processID;

    private String equipmentID;

    private String axleID;

    private String parts;

    private String str;

    public String getAssemblyLineID() {
        return assemblyLineID;
    }

    public void setAssemblyLineID(String assemblyLineID) {
        this.assemblyLineID = assemblyLineID;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getAxleID() {
        return axleID;
    }

    public void setAxleID(String axleID) {
        this.axleID = axleID;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
