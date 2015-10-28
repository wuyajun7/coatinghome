package com.coatinghome.models;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by wuyajun on 15/10/28.
 * Detail: 首页搜索框提示
 */
public class CHSearchTip extends BmobObject{

    private List<String> searchTip;

    public List<String> getSearchTip() {
        return searchTip;
    }

    public void setSearchTip(List<String> searchTip) {
        this.searchTip = searchTip;
    }
}
