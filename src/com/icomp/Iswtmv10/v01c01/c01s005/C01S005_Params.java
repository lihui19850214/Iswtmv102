package com.icomp.Iswtmv10.v01c01.c01s005;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * C01s027Params-参数
 *
 * @author FanLL
 * @2016年5月4日17:58:35
 */
public class C01S005_Params implements Serializable {

    //Spinner中选择第几条
    int containerCarrierType;

    //刀具类型
    String toolType;

    //材料号
    String material;

    //刀具ID
    String toolID;

    //报废原因
    String scrapCause;

    //RFIDCode
    String rfidString;

    String scanNumber;
    ArrayList<String> rfidLists;
}