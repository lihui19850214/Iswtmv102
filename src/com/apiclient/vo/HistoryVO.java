package com.apiclient.vo;

import com.apiclient.pojo.AuthCustomer;
import com.apiclient.pojo.CuttingToolBind;

/**
 * Created by logan on 2018/5/12.
 */
public class HistoryVO {

    private CuttingToolBind cuttingToolBind;

    private Integer count;

    private Integer avgProcessCount;

    private AuthCustomer authCustomer;

    public Integer getAvgProcessCount() {
        return avgProcessCount;
    }

    public void setAvgProcessCount(Integer avgProcessCount) {
        this.avgProcessCount = avgProcessCount;
    }

    public CuttingToolBind getCuttingToolBind() {
        return cuttingToolBind;
    }

    public void setCuttingToolBind(CuttingToolBind cuttingToolBind) {
        this.cuttingToolBind = cuttingToolBind;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public AuthCustomer getAuthCustomer() {
        return authCustomer;
    }

    public void setAuthCustomer(AuthCustomer authCustomer) {
        this.authCustomer = authCustomer;
    }
}
