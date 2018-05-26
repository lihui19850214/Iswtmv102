package com.icomp.common.activity;

import com.apiclient.pojo.AuthCustomer;

import java.util.List;

public interface AuthorizationWindowCallBack {

    public void success(List<AuthCustomer> authorizationList);

    public void fail();
}
