package com.apiclient.constants;

/**
 * Created by logan on 2018/5/11.
 */
public enum  QiMingOptionEnum {

    NewOutPreLibrary("N","生成","新刀出库"),
    OutPreLibrary("U","领用","外委出库"),
    tosharpening("R","归还","待刃磨"),
    inside_sharpening("S1","厂内修磨","车间刃磨"),
    outside_coating("S2","外委刃磨","厂外涂层"),
    outside_sharpening("S3","直接外委","厂外刃磨"),
    toPreLibrary("SR","刃磨归还","备用刀"),
    sharpening_scap("SD","刃磨报废","待刃磨"),
    scap("D","报废","报废"),
    Cutting_tool_Add_Code("A","打码","打码"),
    Cutting_tool_Bind("B","绑定","绑定"),
    installed("C","设备上","设备上"),
    to_outFactory("D","待出厂","待出厂"),
    to_install("E","待安上","待安上"),
    to_exchange("F","待换装（设备下）","待换装（设备下）");


    private String key;
    private String name;
    private String icompName;

    QiMingOptionEnum(String key, String name, String icompName) {
        this.key = key;
        this.name = name;
        this.icompName = icompName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcompName() {
        return icompName;
    }

    public void setIcompName(String icompName) {
        this.icompName = icompName;
    }
}
