package com.apiclient.pojo;

import java.io.Serializable;

/**
* Created by jiangchenkeji
* Automated Build
* 实体 DjUserAkp 
*/
public class DjUserAkp implements Serializable{

    // 序列化接口属性
    private static final long serialVersionUID = 1L;
    /**
     * @fieldName userId
    * @fieldType  Integer
    * @Description  用户ID
     */
    private Integer userId;

    /**
     * @fieldName userName
    * @fieldType  String
    * @Description  用户名
     */
    private String userName;
    /**
     * @fieldName password
    * @fieldType  String
    * @Description  密码
     */
    private String password;
    /**
     * @fieldName name
    * @fieldType  String
    * @Description  姓名
     */
    private String name;
    /**
     * @fieldName title
    * @fieldType  String
    * @Description  性别
     */
    private String title;




    /* 用户ID */
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
