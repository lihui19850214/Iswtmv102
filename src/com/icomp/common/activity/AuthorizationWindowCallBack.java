package com.icomp.common.activity;

import com.apiclient.pojo.AuthCustomer;

import java.util.List;

public interface AuthorizationWindowCallBack {

    public void success(AuthCustomer authCustomer);

    public void fail();
}
