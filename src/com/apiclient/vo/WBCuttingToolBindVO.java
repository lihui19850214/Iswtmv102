package com.apiclient.vo;

import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolBind;

/**
 * Created by logan on 2018/5/11.
 */
public class WBCuttingToolBindVO extends WriteBaseVO{

    private CuttingToolBind cuttingToolBind;

    private AuthCustomer authCustomer;

    public AuthCustomer getAuthCustomer() {
        return authCustomer;
    }

    public void setAuthCustomer(AuthCustomer authCustomer) {
        this.authCustomer = authCustomer;
    }

    public CuttingToolBind getCuttingToolBind() {
        return cuttingToolBind;
    }

    public void setCuttingToolBind(CuttingToolBind cuttingToolBind) {
        this.cuttingToolBind = cuttingToolBind;
    }
}
