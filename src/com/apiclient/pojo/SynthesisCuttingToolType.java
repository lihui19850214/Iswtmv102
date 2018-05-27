package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 SynthesisCuttingToolType 
*/
public class SynthesisCuttingToolType implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  名称
     */
    private String name;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;


    private List<SynthesisCuttingTool> synthesisCuttingToolList;
    private List<SynthesisCuttingToolConfig> synthesisCuttingToolConfigList;


    /* id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }


    public List<SynthesisCuttingTool> getSynthesisCuttingToolList() {
        return synthesisCuttingToolList;
    }

    public void setSynthesisCuttingToolList(List<SynthesisCuttingTool> synthesisCuttingToolList) {
        this.synthesisCuttingToolList = synthesisCuttingToolList;
    }
    public List<SynthesisCuttingToolConfig> getSynthesisCuttingToolConfigList() {
        return synthesisCuttingToolConfigList;
    }

    public void setSynthesisCuttingToolConfigList(List<SynthesisCuttingToolConfig> synthesisCuttingToolConfigList) {
        this.synthesisCuttingToolConfigList = synthesisCuttingToolConfigList;
    }

}
