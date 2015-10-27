package com.coatinghome.models;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by wuyajun on 15/10/24.
 * Detail:用户信息
 */
public class CHUserInfo extends BmobUser {

    private int userId;               // 用户ID
    private int userAge;              // 用户年龄
    private int userSex;              // 用户性别
    private String userPwd;           // 用户密码
    private String userAdd;           // 用户地址
    private String userIcon;          // 用户头像
    private int userLevel;            // 用户等级
    private String userCompanyName;   // 用户公司名称
    private String userCompanyAdd;    // 用户公司地址
    private String userCompanyIntro;  // 用户公司介绍

//    public List<String> searchTip;   // 首页搜索框提示
//    public int userUnRead;           // 用户未读消息


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserAdd() {
        return userAdd;
    }

    public void setUserAdd(String userAdd) {
        this.userAdd = userAdd;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserCompanyAdd() {
        return userCompanyAdd;
    }

    public void setUserCompanyAdd(String userCompanyAdd) {
        this.userCompanyAdd = userCompanyAdd;
    }

    public String getUserCompanyIntro() {
        return userCompanyIntro;
    }

    public void setUserCompanyIntro(String userCompanyIntro) {
        this.userCompanyIntro = userCompanyIntro;
    }
}
