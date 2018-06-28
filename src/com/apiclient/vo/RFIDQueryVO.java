package com.apiclient.vo;


import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolBind;
import com.apiclient.pojo.ProductLineEquipment;
import com.apiclient.pojo.SynthesisCuttingToolBind;

import java.util.List;

/**
 * Created by logan on 2018/5/7.
 */
public class RFIDQueryVO {

    private AuthCustomer loginUser;

    private AuthCustomer authCustomer;

    private List<RfidContainerVO> rfidContainerVOList;

    private String rfidCode;

    private CuttingToolBind cuttingToolBind;

    private ProductLineEquipment equipment;

    private SynthesisCuttingToolBind synthesisCuttingToolBind;

    public AuthCustomer getAuthCustomer() {
        return authCustomer;
    }

    public void setAuthCustomer(AuthCustomer authCustomer) {
        this.authCustomer = authCustomer;
    }

    public AuthCustomer getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(AuthCustomer loginUser) {
        this.loginUser = loginUser;
    }

    public List<RfidContainerVO> getRfidContainerVOList() {
        return rfidContainerVOList;
    }

    public void setRfidContainerVOList(List<RfidContainerVO> rfidContainerVOList) {
        this.rfidContainerVOList = rfidContainerVOList;
    }

    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public CuttingToolBind getCuttingToolBind() {
        return cuttingToolBind;
    }

    public void setCuttingToolBind(CuttingToolBind cuttingToolBind) {
        this.cuttingToolBind = cuttingToolBind;
    }

    public ProductLineEquipment getEquipment() {
        return equipment;
    }

    public void setEquipment(ProductLineEquipment equipment) {
        this.equipment = equipment;
    }

    public SynthesisCuttingToolBind getSynthesisCuttingToolBind() {
        return synthesisCuttingToolBind;
    }

    public void setSynthesisCuttingToolBind(SynthesisCuttingToolBind synthesisCuttingToolBind) {
        this.synthesisCuttingToolBind = synthesisCuttingToolBind;
    }
}
