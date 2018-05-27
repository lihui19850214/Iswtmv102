package com.apiclient.constants;

/**
 * Created by logan on 2018/5/7.
 */
public enum  OperationEnum {

    Out_Library(1,"出库","Out_Library"),
    Cutting_tool_Add_Code(2,"打码","Cutting_tool_Add_Code"),
    Cutting_tool_Bind(3,"绑定","Cutting_tool_Bind"),
    SynthesisCuttingTool_Exchange(4,"合成刀换装","SynthesisCuttingTool_Exchange"),
    SynthesisCuttingTool_Init(5,"合成刀初始化","SynthesisCuttingTool_Init"),
    SynthesisCuttingTool_Config(6,"合成刀组装","SynthesisCuttingTool_Config"),
    SynthesisCuttingTool_UnConfig(7,"合成刀拆分","SynthesisCuttingTool_UnConfig"),
    SynthesisCuttingTool_Install(8,"合成刀按上设备","SynthesisCuttingTool_Install"),
    SynthesisCuttingTool_UnInstall(9,"合成刀卸下设备","SynthesisCuttingTool_UnInstall"),
    Cutting_tool_Inside(10,"厂内刃磨","Cutting_tool_Inside"),
    Cutting_tool_OutSide(11,"外委刃磨","Cutting_tool_OutSide"),
    Cutting_tool_Inside_Coating(12,"厂外涂层","Cutting_tool_Inside_Coating"),
    Employee_code_Init(13,"员工卡初始化","Employee_code_Init"),
    Equipment_Init(14,"设备初始化","Equipment_Init"),
    Cutting_tool_scap(15,"刀具报废","Cutting_tool_scap"),
    RFID_Change(16,"标签置换","RFID_Change"),
    RFID_Clear(17,"标签清空","RFID_Clear"),
    Fast_Query(18,"快速查询","Fast_Query"),
    Setting_RFID(19,"射频设置","Setting_RFID"),
    Picking(20,"领料","Picking"),
    Audit(21,"审核","Audit");

    private Integer key;
    private String name;
    private String identify;

    OperationEnum(Integer key, String name, String identify) {
        this.key = key;
        this.name = name;
        this.identify = identify;
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

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }
}
