package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 PowerGroup 
*/
public class PowerGroup implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  标识
     */
    private Integer id;

    /**
     * @fieldName name
    * @fieldType  String
    * @Description  权限组名称
     */
    private String name;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;
    /**
     * @fieldName isRole
    * @fieldType  String
    * @Description  是否为角色分组 1是 0 否
     */
    private String isRole;


    private List<PowerHasGroup> powerHasGroupList;
    private List<PowerPositionHasGroup> powerPositionHasGroupList;
    private List<PowerUserHasGroup> powerUserHasGroupList;


    /* 标识 */
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
    public String getIsRole() {
        return isRole;
    }

    public void setIsRole(String isRole) {
        this.isRole = isRole;
    }


    public List<PowerHasGroup> getPowerHasGroupList() {
        return powerHasGroupList;
    }

    public void setPowerHasGroupList(List<PowerHasGroup> powerHasGroupList) {
        this.powerHasGroupList = powerHasGroupList;
    }
    public List<PowerPositionHasGroup> getPowerPositionHasGroupList() {
        return powerPositionHasGroupList;
    }

    public void setPowerPositionHasGroupList(List<PowerPositionHasGroup> powerPositionHasGroupList) {
        this.powerPositionHasGroupList = powerPositionHasGroupList;
    }
    public List<PowerUserHasGroup> getPowerUserHasGroupList() {
        return powerUserHasGroupList;
    }

    public void setPowerUserHasGroupList(List<PowerUserHasGroup> powerUserHasGroupList) {
        this.powerUserHasGroupList = powerUserHasGroupList;
    }

}
