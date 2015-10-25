package com.coatinghome.services.online;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.coatinghome.interfaces.online.IOnlineDataInfoProvider;
import com.google.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: wuyajun
 * Date: 15-2-2
 * Time: 下午8:40
 * Details:在线数据拉取模块
 */
public class OnlineDataInfoProvider implements IOnlineDataInfoProvider {

    @Inject
    private OnlineDataManager maiChangOnlineManager; //在线数据缓存管理类

    @Override
    public void getStartAdmobData(final Context context, final Handler bckHandler) {
        final Message message = Message.obtain();
        message.obj = "9090";
        message.setTarget(bckHandler);
        message.sendToTarget();
    }
}
