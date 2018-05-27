package com.apiclient.vo;

import com.apiclient.pojo.AuthCustomer;

import java.util.List;

/**
 * Created by logan on 2018/5/12.
 */
public class ScrapVO {

    private List<SharpenVO> sharpenVOS;

    private AuthCustomer authCustomer;

    public List<SharpenVO> getSharpenVOS() {
        return sharpenVOS;
    }

    public void setSharpenVOS(List<SharpenVO> sharpenVOS) {
        this.sharpenVOS = sharpenVOS;
    }

    public AuthCustomer getAuthCustomer() {
        return authCustomer;
    }

    public void setAuthCustomer(AuthCustomer authCustomer) {
        this.authCustomer = authCustomer;
    }
}
