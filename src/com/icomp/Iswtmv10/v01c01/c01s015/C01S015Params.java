package com.icomp.Iswtmv10.v01c01.c01s015;

import java.io.Serializable;

/**
 * 快速查询参数类
 * Created by FanLL on 2017/6/19.
 */

public class C01S015Params implements Serializable {

    //材料号
    public String toolCode;
    //库存数量
    public String libraryCount;
    //刀具ID
    public String toolID;
    //实际在库数量
    public String realLibraryCount;
    //刀具类型（0--非单品刀具，1--单品刀具）
    public String toolConsumetype;

}
