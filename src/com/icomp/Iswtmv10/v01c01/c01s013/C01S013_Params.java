package com.icomp.Iswtmv10.v01c01.c01s013;


import com.icomp.entity.base.Vgetwheelinfo;
import com.icomp.wsdl.v01c01.c01s013.endpoint.PartsEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 卸下设备-参数
 *
 * @author WHY
 * @ClassName: C01S013_Params
 * @date 2016-3-2 上午10:39:10
 */
public class C01S013_Params implements Serializable {

    //对应工序ID
    String processID;
    //对应工序编码
    String processCode;
    //流水线名称
    String assemblyLineName;
    //流水线ID
    String assemblyLineID;
    //设备名称
    String equipmentName;
    //设备ID
    String equipmentID;
    //载体ID
    String rfidContainerId;
    //合成刀具编码列表
    List<Vgetwheelinfo> synthesisParametersCodeList;
    //轴号名称
    String axleName;
    //轴号ID
    String axleID;
    //合成刀编码
    String synthesisParametersCode;
    //零部件名称
    int partsName;

    public String getPartsID() {
        return partsID;
    }

    public void setPartsID(String partsID) {
        this.partsID = partsID;
    }

    String partsID;
    //Rfid编码
    String rfidCode;
    //零部件列表
    List<PartsEntity> partsEntity;
    //卸下原因
    int removeReason;
    //加工量
    String processCount;
    //合成刀具ID
    String synthesisParametersID;

    public String getSynthesisParametersID() {
        return synthesisParametersID;
    }

    public void setSynthesisParametersID(String synthesisParametersID) {
        this.synthesisParametersID = synthesisParametersID;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getAxleName() {
        return axleName;
    }

    public void setAxleName(String axleName) {
        this.axleName = axleName;
    }

    public String getAxleID() {
        return axleID;
    }

    public void setAxleID(String axleID) {
        this.axleID = axleID;
    }

    public String getSynthesisParametersCode() {
        return synthesisParametersCode;
    }

    public void setSynthesisParametersCode(String synthesisParametersCode) {
        this.synthesisParametersCode = synthesisParametersCode;
    }

    public int getPartsName() {
        return partsName;
    }

    public void setPartsName(int partsName) {
        this.partsName = partsName;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public List<PartsEntity> getPartsEntity() {
        return partsEntity;
    }

    public void setPartsEntity(List<PartsEntity> partsEntity) {
        this.partsEntity = partsEntity;
    }

    public int getRemoveReason() {
        return removeReason;
    }

    public void setRemoveReason(int removeReason) {
        this.removeReason = removeReason;
    }

    public String getProcessCount() {
        return processCount;
    }

    public void setProcessCount(String processCount) {
        this.processCount = processCount;
    }

    public String getAssemblyLineID() {
        return assemblyLineID;
    }

    public void setAssemblyLineID(String assemblyLineID) {
        this.assemblyLineID = assemblyLineID;
    }

    public String getRfidContainerId() {
        return rfidContainerId;
    }

    public void setRfidContainerId(String rfidContainerId) {
        this.rfidContainerId = rfidContainerId;
    }

    public String getAssemblyLineName() {
        return assemblyLineName;
    }

    public void setAssemblyLineName(String assemblyLineName) {
        this.assemblyLineName = assemblyLineName;
    }

    public List<Vgetwheelinfo> getSynthesisParametersCodeList() {
        return synthesisParametersCodeList;
    }

    public void setSynthesisParametersCodeList(List<Vgetwheelinfo> synthesisParametersCodeList) {
        this.synthesisParametersCodeList = synthesisParametersCodeList;
    }
}
