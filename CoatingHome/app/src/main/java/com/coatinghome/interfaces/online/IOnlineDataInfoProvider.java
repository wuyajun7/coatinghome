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
     * test1
     *
     * @param context
     * @param backHandler
     */
    void getTest1(Context context, Handler backHandler);

    void registerAccount(Context context, Handler backHandler);

    /**
     * 用户登录
     *
     * @param context
     * @param backHandler
     * @param userName
     * @param userPwd
     */
    void apiLogin(Context context, Handler backHandler, String userName, String userPwd);

    /**
     * 发送验证码到用户手机
     *
     * @param context
     * @param backHandler
     * @param mobile
     */
    void apiSendSmsCode(Context context, Handler backHandler, String mobile, int backCode);

    /**
     * 检查账户是否注册过
     *
     * @param context
     * @param backHandler
     * @param mobile
     */
    void apiCheckAccount(Context context, Handler backHandler, String mobile, int backCode);

    /**
     * 检查验证码
     *
     * @param context
     * @param backHandler
     * @param mobile
     * @param smsCode
     * @param backCode
     */
    void apiCheckSmsCode(Context context, Handler backHandler, String mobile, int smsCode, int backCode);

}
