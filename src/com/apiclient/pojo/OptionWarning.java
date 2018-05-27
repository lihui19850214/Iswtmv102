package com.apiclient.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 OptionWarning 
*/
public class OptionWarning implements Serializable {

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName id
    * @fieldType  Integer
    * @Description  id
     */
    private Integer id;

    /**
     * @fieldName time
    * @fieldType  Timestamp
    * @Description  预警时间
     */
    private Timestamp time;
    /**
     * @fieldName optionUserCode
    * @fieldType  String
    * @Description  操作人编码
     */
    private String optionUserCode;
    /**
     * @fieldName optionUserName
    * @fieldType  String
    * @Description  操作人姓名
     */
    private String optionUserName;
    /**
     * @fieldName option
    * @fieldType  String
    * @Description  操作
     */
    private String option;
    /**
     * @fieldName authorizedUserCode
    * @fieldType  String
    * @Description  授权人编码
     */
    private String authorizedUserCode;
    /**
     * @fieldName authorizedUserName
    * @fieldType  String
    * @Description  授权人姓名
     */
    private String authorizedUserName;
    /**
     * @fieldName isDel
    * @fieldType  Integer
    * @Description  逻辑删除
     */
    private Integer isDel;




    /* id */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
    public String getOptionUserCode() {
        return optionUserCode;
    }

    public void setOptionUserCode(String optionUserCode) {
        this.optionUserCode = optionUserCode;
    }
    public String getOptionUserName() {
        return optionUserName;
    }

    public void setOptionUserName(String optionUserName) {
        this.optionUserName = optionUserName;
    }
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
    public String getAuthorizedUserCode() {
        return authorizedUserCode;
    }

    public void setAuthorizedUserCode(String authorizedUserCode) {
        this.authorizedUserCode = authorizedUserCode;
    }
    public String getAuthorizedUserName() {
        return authorizedUserName;
    }

    public void setAuthorizedUserName(String authorizedUserName) {
        this.authorizedUserName = authorizedUserName;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }



}
