package com.coatinghome.providers;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.coatinghome.R;

/**
 * Created by wuyajun on 15/10/22.
 * Detail:公共常量 - Action
 */
public class CHContrat {

    /* net code */
    public static final int ONSRCCESS = 200;
    public static final int ONFAILURE = 100;
    public static final int ON404 = 404;
    public static final int ON500 = 500;

    /* 注册返回码 */
    public static final int ONREGISTERSRCCESS = 999;
    public static final int ONREGISTERFAILURE = 111;

    public static final int R_CODE_LOGIN_TO_REGISTER = 0x01;

    /* 界面间传递手机号 标签 */
    public static final String RESULT_MOBILE = "RESULT_MOBILE";

    /* 界面抬头 */
    public static final String ACTIVITY_TITLE_TEXT = "ACTIVITY_TITLE_TEXT";
    /* 网页路径 */
    public static final String WEB_URL_PATH = "WEB_URL_PATH";
    /* 彩色数组 */
    public static final int colours[] = {
            R.color.colours1,
            R.color.colours2,
            R.color.colours3,
            R.color.colours4,
            R.color.colours5,
            R.color.colours6,
            R.color.colours7
    };

    /**
     * 显示Views
     *
     * @param views
     */
    public static void showView(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 隐藏Views
     *
     * @param views
     */
    public static void hideView(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 验证手机格式
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 验证验证码格式
     *
     * @param smsCode
     * @return
     */
    public static boolean isSmsCode(String smsCode) {
        if (TextUtils.isEmpty(smsCode)) return false;
        else return smsCode.length() == 6;
    }

    /**
     * 验证密码格式
     * 由数字和字母组成，且长度要在6-15位之间
     *
     * @param pwd
     * @return
     */
    public static boolean isPwd(String pwd) {
        String pwdRegex = "^[0-9a-zA-Z]{6,15}+$";
        if (TextUtils.isEmpty(pwd)) return false;
        else return pwd.matches(pwdRegex);
    }

    /**
     * 关闭输入法
     */
    public static void closeIme(Context context) {
        Activity activity = (Activity) context;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity != null) {
            if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken()
                        , InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    //---------------------------------- Test Datas
    public static final String userIcon = "http://diy.qqjay.com/u2/2013/0403/a778f5b0538769ae5e0046a83a4c243c.jpg";

    public static final String[] testTitle = {
            "大宝多乐",
            "罗马大宝",
            "维克罗马多",
            "格林维克堡",
            "皮思格林考",
            "九强皮思油漆",
            "特卖九强八宝涂漆",
            "多乐特卖士",
            "强森多乐",
            "罗马强森",
            "维克罗马多",
            "格林维克堡",
            "皮思格林考",
            "九强皮思油漆",
            "多乐士"
    };

    public static final String[] testContent = {
            "多乐士",
            "大宝",
            "罗马",
            "维克多",
            "格林堡",
            "皮思考",
            "九强油漆",
            "特卖八宝涂漆",
            "多乐士",
            "强森",
            "罗马",
            "维克多",
            "格林堡",
            "皮思考",
            "九强油漆"
    };
    public static final String[] testImageUrl = {
            "http://www.youpuqi.com/Images/20120917/16425_m.jpg",
            "http://i05.yizimg.com/uploads/160615/200668142951489_5117.jpg",
            "http://image.tupian114.com/20140329/11501121.jpg.thumb.jpg",
            "http://img.xiaba.cvimage.cn/4e167c87e5052619108a0000.jpg",
            "http://img.xiaba.cvimage.cn/51889d94b89bd3890b000008.jpg",
            "http://img2.ooopic.com/uploadfilepic/shiliang/2009-02-08/OOOPIC_hy602295222_2009020855358b06e9a2276a_202.jpg",
            "http://pic12.nipic.com/20110117/2457331_090129367000_2.jpg",
            "http://www.youpuqi.com/Images/20120917/16425_m.jpg",
            "http://i05.yizimg.com/uploads/160615/200668142951489_5117.jpg",
            "http://image.tupian114.com/20140329/11501121.jpg.thumb.jpg",
            "http://img.xiaba.cvimage.cn/4e167c87e5052619108a0000.jpg",
            "http://img.xiaba.cvimage.cn/51889d94b89bd3890b000008.jpg",
            "http://img2.ooopic.com/uploadfilepic/shiliang/2009-02-08/OOOPIC_hy602295222_2009020855358b06e9a2276a_202.jpg",
            "http://pic12.nipic.com/20110117/2457331_090129367000_2.jpg",
            "http://www.youpuqi.com/Images/20120917/16425_m.jpg",
    };

}
