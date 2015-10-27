package com.coatinghome.services.online;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.coatinghome.interfaces.online.IOnlineDataInfoProvider;
import com.coatinghome.models.CHUserInfo;
import com.coatinghome.openapi.CHHttpClient;
import com.coatinghome.openapi.CHPARAMS;
import com.coatinghome.providers.CHContrat;
import com.google.inject.Inject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.AsyncCustomEndpoints;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CloudCodeListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created with IntelliJ IDEA.
 * User: wuyajun
 * Date: 15-2-2
 * Time: 下午8:40
 * Details:在线数据拉取模块
 */
public class OnlineDataInfoProvider implements IOnlineDataInfoProvider {

    @Inject
    private OnlineDataManager onlineDataManager; //在线数据缓存管理类

    /**
     * test1
     *
     * @param context
     * @param backHandler
     */
    @Override
    public void getTest1(Context context, final Handler backHandler) {
        final Message message = Message.obtain();

        //post方式

        JSONObject params = new JSONObject();
        //name是上传到云端的参数名称，值是bmob，云端代码可以通过调用request.body.name获取这个值
        try {
            params.put(CHPARAMS.USER_NAME, "test2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //创建云端代码对象
        AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
        //异步调用云端代码
        cloudCode.callEndpoint(context, CHHttpClient.GET_USERINFO, params, new CloudCodeListener() {

            //执行成功时调用，返回result对象
            @Override
            public void onSuccess(Object result) {
                message.obj = result.toString();
                message.setTarget(backHandler);
                message.sendToTarget();
            }

            //执行失败时调用
            @Override
            public void onFailure(int i, String s) {
                message.obj = s;
                message.setTarget(backHandler);
                message.sendToTarget();
            }
        });

    }

    @Override
    public void registerAccount(Context context, final Handler backHandler) {
//        final Message message = Message.obtain();
//
//        JSONObject params = new JSONObject();
//        //name是上传到云端的参数名称，值是bmob，云端代码可以通过调用request.body.name获取这个值
//        try {
//            params.put(CHPARAMS.USER_MOBILE, "13122781686");
//            params.put(CHPARAMS.USER_PWD, "808080");
//            params.put(CHPARAMS.USER_NAME, "13122781686");
//            params.put(CHPARAMS.USER_COMPANY_NAME, "上海&&网络科技有限公司");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //创建云端代码对象
//        AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
//        //异步调用云端代码
//        cloudCode.callEndpoint(context, CHHttpClient.REGISTER_ACCOUNT, params, new CloudCodeListener() {
//
//            //执行成功时调用，返回result对象
//            @Override
//            public void onSuccess(Object result) {
//                message.obj = result.toString();
//                message.setTarget(backHandler);
//                message.sendToTarget();
//            }
//
//            //执行失败时调用
//            @Override
//            public void onFailure(int i, String s) {
//                message.obj = s;
//                message.setTarget(backHandler);
//                message.sendToTarget();
//            }
//        });
    }

    @Override
    public void apiLogin(Context context, final Handler backHandler, String userName, String userPwd) {
        final Message message = Message.obtain();

        BmobUser bu2 = new BmobUser();
        bu2.setUsername(userName);
        bu2.setPassword(userPwd);
        bu2.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                message.obj = "";
                message.what = CHContrat.ONSRCCESS;
                message.setTarget(backHandler);
                message.sendToTarget();
            }

            @Override
            public void onFailure(int code, String msg) {
                message.obj = code;
                message.what = CHContrat.ONFAILURE;
                message.setTarget(backHandler);
                message.sendToTarget();
            }
        });
    }

    /**
     * 发送验证码到用户手机
     *
     * @param context
     * @param backHandler
     * @param mobile
     */
    @Override
    public void apiSendSmsCode(Context context, final Handler backHandler, String mobile, final int backCode) {
        final Message message = Message.obtain();

        BmobSMS.requestSMSCode(context, mobile, CHPARAMS.SMSVALI, new RequestSMSCodeListener() {


            @Override
            public void done(Integer smsId, BmobException ex) {
                if (ex == null) {//验证码发送成功
                    message.arg1 = backCode;
                    message.arg2 = smsId;
                    message.what = CHContrat.ONSRCCESS;
                    message.setTarget(backHandler);
                    message.sendToTarget();
                } else {
                    message.arg1 = backCode;
                    message.arg2 = -1;
                    message.what = CHContrat.ONFAILURE;
                    message.setTarget(backHandler);
                    message.sendToTarget();
                }
            }
        });
    }

    /**
     * 检查账户是否注册过
     *
     * @param context
     * @param backHandler
     * @param mobile
     */
    @Override
    public void apiCheckAccount(Context context, final Handler backHandler, String mobile, final int backCode) {
        final Message message = Message.obtain();

        BmobQuery<CHUserInfo> query = new BmobQuery<CHUserInfo>();
        query.addWhereEqualTo(CHPARAMS.MOBILE_PHONE_NUMBER, mobile);
        query.findObjects(context, new FindListener<CHUserInfo>() {
            @Override
            public void onSuccess(List<CHUserInfo> object) {
                if (object.size() > 0) {//已注册
                    message.arg1 = backCode;
                    message.what = CHContrat.ONREGISTERFAILURE;
                    message.setTarget(backHandler);
                    message.sendToTarget();
                } else {//未注册
                    message.arg1 = backCode;
                    message.what = CHContrat.ONREGISTERSRCCESS;
                    message.setTarget(backHandler);
                    message.sendToTarget();
                }
            }

            @Override
            public void onError(int code, String msg) {
                message.arg1 = backCode;
                message.what = CHContrat.ONFAILURE;
                message.setTarget(backHandler);
                message.sendToTarget();
            }
        });
    }

    /**
     * 检查验证码
     *
     * @param context
     * @param backHandler
     * @param mobile
     * @param smsCode
     * @param backCode
     */
    @Override
    public void apiCheckSmsCode(Context context, final Handler backHandler, String mobile, String smsCode, final int backCode) {
        final Message message = Message.obtain();

        BmobSMS.verifySmsCode(context, mobile, smsCode, new VerifySMSCodeListener() {

            @Override
            public void done(BmobException ex) {
                if (ex == null) {//短信验证码已验证成功
                    message.arg1 = backCode;
                    message.what = CHContrat.ONSRCCESS;
                    message.setTarget(backHandler);
                    message.sendToTarget();
                } else {
                    message.arg1 = backCode;
                    message.what = CHContrat.ONFAILURE;
                    message.setTarget(backHandler);
                    message.sendToTarget();
                }
            }
        });
    }

    /**
     * 注册账户
     *
     * @param context
     * @param backHandler
     * @param chUserInfo
     * @param backCode
     */
    @Override
    public void apiRegisterAccount(Context context, final Handler backHandler, CHUserInfo chUserInfo, final int backCode) {
        final Message message = Message.obtain();

        JSONObject params = new JSONObject();
        //name是上传到云端的参数名称，值是bmob，云端代码可以通过调用request.body.name获取这个值
        try {
            params.put(CHPARAMS.MOBILE_PHONE_NUMBER, chUserInfo.getMobilePhoneNumber());
            params.put(CHPARAMS.USER_PWD, chUserInfo.getUserPwd());
            params.put(CHPARAMS.USER_NAME, chUserInfo.getUsername());
            params.put(CHPARAMS.USER_COMPANY_NAME, chUserInfo.getUserCompanyName());

            //创建云端代码对象
            AsyncCustomEndpoints cloudCode = new AsyncCustomEndpoints();
            //异步调用云端代码
            cloudCode.callEndpoint(context, CHHttpClient.API_REGISTER_ACCOUNT, params, new CloudCodeListener() {

                //执行成功时调用，返回result对象
                @Override
                public void onSuccess(Object result) {
                    message.arg1 = backCode;
                    message.what = CHContrat.ONSRCCESS;
                    message.obj = result.toString();
                    message.setTarget(backHandler);
                    message.sendToTarget();
                }

                //执行失败时调用
                @Override
                public void onFailure(int i, String s) {
                    message.arg1 = backCode;
                    message.what = CHContrat.ONFAILURE;
                    message.obj = s;
                    message.setTarget(backHandler);
                    message.sendToTarget();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();

            message.arg1 = backCode;
            message.what = CHContrat.ONFAILURE;
            message.obj = e.toString();
            message.setTarget(backHandler);
            message.sendToTarget();
        }

    }
}
