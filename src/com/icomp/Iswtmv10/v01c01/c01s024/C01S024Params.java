package com.icomp.Iswtmv10.v01c01.c01s024;

import com.icomp.wsdl.v01c01.c01s024.endpoint.TypeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 快速查询参数类
 * Created by FanLL on 2017/6/19.
 */

public class C01S024Params implements Serializable {

    //查询结果列表
    public List<TypeEntity> list;

    public List<TypeEntity> getList() {
        return list;
    }

    public void setList(List<TypeEntity> list) {
        this.list = list;
    }

}
