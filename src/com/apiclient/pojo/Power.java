package com.apiclient.pojo;

import java.io.Serializable;
import java.util.List;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 Power 
*/
public class Power implements Serializable {

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
    * @Description  名称
     */
    private String name;
    /**
     * @fieldName url
    * @fieldType  String
    * @Description  操作地址
     */
    private String url;
    /**
     * @fieldName enumKey
    * @fieldType  String
    * @Description  操作枚举key
     */
    private String enumKey;
    /**
     * @fieldName enumValue
    * @fieldType  String
    * @Description  操作枚举名称
     */
    private String enumValue;
    /**
     * @fieldName identify
    * @fieldType  String
    * @Description  唯一标识
     */
    private String identify;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;
    /**
     * @fieldName parentId
    * @fieldType  Integer
    * @Description  父权限id
     */
    private Integer parentId;
    /**
     * @fieldName isRoot
    * @fieldType  String
    * @Description  根节点
     */
    private String isRoot;
    /**
     * @fieldName powerType
    * @fieldType  String
    * @Description  权限类型 1 可执行操作 2 可查看数据 3流程通过 4 菜单
     */
    private String powerType;

    private List<Power> childPowers;

    public List<Power> getChildPowers() {
        return childPowers;
    }

    public void setChildPowers(List<Power> childPowers) {
        this.childPowers = childPowers;
    }

    private List<PowerHasGroup> powerHasGroupList;


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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getEnumKey() {
        return enumKey;
    }

    public void setEnumKey(String enumKey) {
        this.enumKey = enumKey;
    }
    public String getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }
    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot;
    }
    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }


    public List<PowerHasGroup> getPowerHasGroupList() {
        return powerHasGroupList;
    }

    public void setPowerHasGroupList(List<PowerHasGroup> powerHasGroupList) {
        this.powerHasGroupList = powerHasGroupList;
    }

}
