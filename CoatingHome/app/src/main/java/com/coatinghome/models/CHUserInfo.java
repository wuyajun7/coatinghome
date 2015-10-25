package com.coatinghome.models;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by wuyajun on 15/10/24.
 * Detail:用户信息
 */
public class CHUserInfo extends BmobUser {

    public int userId;               // 用户ID
    public int userAge;              // 用户年龄
    public int userSex;              // 用户性别
    public String userAdd;           // 用户地址
    public String userIcon;          // 用户头像
    public int userLevel;            // 用户等级
    public String userCompanyName;   // 用户公司名称
    public String userCompanyAdd;    // 用户公司地址
    public String userCompanyIntro;  // 用户公司介绍

    public List<String> searchTip;   // 首页搜索框提示
    public int userUnRead;           // 用户未读消息

}
