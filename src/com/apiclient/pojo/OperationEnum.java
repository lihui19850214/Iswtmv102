package com.apiclient.pojo;

/**
 * Created by logan on 2018/5/7.
 */
public enum  OperationEnum {

    Out_Library(1,"出库"),
    Cutting_tool_Add_Code(2,"打码"),
    Cutting_tool_Bind(3,"绑定"),
    SynthesisCuttingTool_Exchange(4,"合成刀换装"),
    SynthesisCuttingTool_Init(5,"合成刀初始化"),
    SynthesisCuttingTool_Config(6,"合成刀组装"),
    SynthesisCuttingTool_UnConfig(7,"合成刀拆分"),
    SynthesisCuttingTool_Install(8,"合成刀按上设备"),
    SynthesisCuttingTool_UnInstall(9,"合成刀卸下设备"),
    Cutting_tool_Inside(10,"厂内刃磨"),
    Cutting_tool_OutSide(11,"外委刃磨"),
    Cutting_tool_Inside_Coating(12,"厂外涂层"),
    Employee_code_Init(13,"员工卡初始化"),
    Equipment_Init(14,"设备初始化");

    private String name;
    private Integer key;
    private String QiMingname;

    OperationEnum(int key, String name) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

}
