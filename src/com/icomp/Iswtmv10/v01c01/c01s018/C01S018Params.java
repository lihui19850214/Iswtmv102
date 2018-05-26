package com.icomp.Iswtmv10.v01c01.c01s018;

import java.io.Serializable;

/**
 * 厂内修磨参数类
 * Created by FanLL on 2017/7/2.
 */

public class C01S018Params implements Serializable {

    //材料号
    public String materialNumber;
    //修磨数量
    public String grindingQuantity;

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getGrindingQuantity() {
        return grindingQuantity;
    }

    public void setGrindingQuantity(String grindingQuantity) {
        this.grindingQuantity = grindingQuantity;
    }

}
