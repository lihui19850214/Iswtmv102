package com.apiclient.pojo;


import com.apiclient.vo.ScrapVO;

import java.util.List;

/**
 * Created by logan on 2018/6/27.
 */
public class ScrapBO extends BaseBO{

    private List<ScrapVO> scrapVOS;

    private String reason;

    private String operatKey;

    public String getOperatKey() {
        return operatKey;
    }

    public void setOperatKey(String operatKey) {
        this.operatKey = operatKey;
    }

    private AuthCustomer authCustomer;

    public AuthCustomer getAuthCustomer() {
        return authCustomer;
    }

    public void setAuthCustomer(AuthCustomer authCustomer) {
        this.authCustomer = authCustomer;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ScrapVO> getScrapVOS() {
        return scrapVOS;
    }

    public void setScrapVOS(List<ScrapVO> scrapVOS) {
        this.scrapVOS = scrapVOS;
    }
}
