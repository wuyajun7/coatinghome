package com.coatinghome.interfaces.online;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

/**
 * Created by wuyajun on 15/10/22.
 * Detail: 在线数据接口类
 */
public interface IOnlineDataInfoProvider {

    /**
     * 获取启动页广告
     *
     * @param context
     */
    public void getStartAdmobData(Context context, Handler bckHandler);

}
