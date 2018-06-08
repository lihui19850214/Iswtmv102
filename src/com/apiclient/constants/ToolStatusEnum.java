package com.apiclient.constants;


/**
 * Created by logan on 2018/5/27.
 */
public enum ToolStatusEnum {
    SynthesisCuttingTool_Init("1","合成刀初始化","SynthesisCuttingTool_Init",OperationEnum.SynthesisCuttingTool_Init.getKey()+""),
    SynthesisCuttingTool_ToExchnage("2","待换装","SynthesisCuttingTool_ToExchnage",OperationEnum.SynthesisCuttingTool_UnInstall.getKey()+""),
    SynthesisCuttingTool_ToConfig("3","待组装","SynthesisCuttingTool_ToConfig",OperationEnum.SynthesisCuttingTool_UnConfig.getKey()+""),
    SynthesisCuttingTool_ToInstall("4","待安上","SynthesisCuttingTool_ToInstall",OperationEnum.SynthesisCuttingTool_Exchange.getKey()+","+OperationEnum.SynthesisCuttingTool_Config.getKey()),
    SynthesisCuttingTool_ToUnInstall("5","待卸下","SynthesisCuttingTool_ToUnInstall",OperationEnum.SynthesisCuttingTool_Install.getKey()+""),
    CuttingTool_ToConfig("6","待安上","CuttingTool_ToConfig",OperationEnum.SynthesisCuttingTool_Exchange.getKey()+","+OperationEnum.SynthesisCuttingTool_Config.getKey()),
    CuttingTool_ToUnConfig("7","待卸下","CuttingTool_ToUnConfig",OperationEnum.SynthesisCuttingTool_Install.getKey()+""),
    CuttingTool_ToExchange("8","待换装","CuttingTool_ToExchange",OperationEnum.SynthesisCuttingTool_UnInstall.getKey()+""),
    CuttingTool_ToInsideGrinding("9","待厂内刃磨","CuttingTool_ToInsideGrinding",OperationEnum.SynthesisCuttingTool_Exchange.getKey()+","+OperationEnum.SynthesisCuttingTool_UnConfig.getKey()),
    CuttingTool_ToOutsideGrinding("10","待厂外刃磨","CuttingTool_ToOutsideGrinding",OperationEnum.SynthesisCuttingTool_Exchange.getKey()+","+OperationEnum.SynthesisCuttingTool_UnConfig.getKey()),
    CuttingTool_ToBack("11","待回厂","CuttingTool_ToBack",OperationEnum.Cutting_tool_Inside.getKey()+","+OperationEnum.Cutting_tool_OutSide.getKey()+","+OperationEnum.Cutting_tool_Inside_Coating.getKey());

    ToolStatusEnum(String key, String name, String identify, String operationKey) {
        this.key = key;
        this.name = name;
        this.identify = identify;
        this.operationKey = operationKey;
    }

    public static ToolStatusEnum getToolStatusEnumByOperationKey(String operationKey){
        for (ToolStatusEnum toolStatusEnum : values()) {
            if (toolStatusEnum.getOperationKey().indexOf(operationKey)>=0){
                return toolStatusEnum;
            }
        }
        return null;
    }

    private String key;
    private String name;
    private String identify;
    private String operationKey;

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

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getOperationKey() {
        return operationKey;
    }

    public void setOperationKey(String operationKey) {
        this.operationKey = operationKey;
    }
}
